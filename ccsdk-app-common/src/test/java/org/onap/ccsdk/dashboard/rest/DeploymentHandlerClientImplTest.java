/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/

package org.onap.ccsdk.dashboard.rest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentLink;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponseLinks;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentsListResponse;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.util.CacheManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DashboardProperties.class})
public class DeploymentHandlerClientImplTest {

    @Mock
    RestTemplate mockRest;

    @Mock
    CloudifyClient cfyClient;
    
    @InjectMocks
    DeploymentHandlerClientImpl subject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(DashboardProperties.class);
        when(DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_DHANDLER_URL)).thenReturn("https://dplh.com");
        CacheManager testCache = new CacheManager();
        subject.setCacheManager(testCache); 
        this.subject.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testCheckHealth() {
        String expectStr = "DH mS health check";
        ResponseEntity<String> response = 
            new ResponseEntity<String>(expectStr, HttpStatus.OK);
        
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<String>>any())).thenReturn(response)
                .thenReturn(response);
        
        String actualStr = subject.checkHealth();
        assertTrue(actualStr.equals(expectStr));
    }

    @Test
    public final void testGetDeployments() {
        Collection<DeploymentLink> expectedDeplRefs = new ArrayList<DeploymentLink>();
        expectedDeplRefs.add(new DeploymentLink("http://dplh.com/dpl1"));
        expectedDeplRefs.add(new DeploymentLink("http://dplh.com/dpl11"));

        DeploymentsListResponse expectedDeplList =
            new DeploymentsListResponse("req1", expectedDeplRefs);

        ResponseEntity<DeploymentsListResponse> response =
            new ResponseEntity<DeploymentsListResponse>(expectedDeplList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentsListResponse>>any()))
                .thenReturn(response);

        Stream<DeploymentLink> actualResult = subject.getDeployments();
        assertTrue(actualResult.findFirst().get().getHref().contains("dpl1"));
    }

    @Test
    public final void testGetDeploymentsString() {
        Collection<DeploymentLink> expectedDeplRefs = new ArrayList<DeploymentLink>();
        expectedDeplRefs.add(new DeploymentLink("http://dplh.com/dpl1"));
        expectedDeplRefs.add(new DeploymentLink("http://dplh.com/dpl11"));

        DeploymentsListResponse expectedDeplList =
            new DeploymentsListResponse("req1", expectedDeplRefs);

        ResponseEntity<DeploymentsListResponse> response =
            new ResponseEntity<DeploymentsListResponse>(expectedDeplList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentsListResponse>>any()))
                .thenReturn(response);

        Stream<DeploymentLink> actualResult = subject.getDeployments("serviceTypeId");
        assertTrue(actualResult.findFirst().get().getHref().contains("dpl1"));
    }

    @Test
    public final void testPutDeployment() throws Exception {
        DeploymentRequest deplReq = new DeploymentRequest("serviceTypeId", null);

        DeploymentResponseLinks newDeplItemLink =
            new DeploymentResponseLinks("selfUrl", "statusUrl");
        DeploymentResponse expectedDeplItem = new DeploymentResponse("req1", newDeplItemLink);

        ResponseEntity<DeploymentResponse> response =
            new ResponseEntity<DeploymentResponse>(expectedDeplItem, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any())).thenReturn(response);

        DeploymentResponse actualResponse = subject.putDeployment("dpl12", "tenant12", deplReq);
        assertTrue(actualResponse.getRequestId().equals("req1"));
    }

    @Test(expected = DownstreamException.class)
    public final void testPutDeployment_downstreamError() throws Exception {
        DeploymentRequest deplReq = new DeploymentRequest("serviceTypeId", null);

        HttpServerErrorException httpException =
            new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.putDeployment("dpl12", "tenant12", deplReq);
    }

    @Test(expected = BadRequestException.class)
    public final void testPutDeployment_badReqError() throws Exception {
        DeploymentRequest deplReq = new DeploymentRequest("serviceTypeId", null);

        HttpClientErrorException httpException =
            new HttpClientErrorException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.putDeployment("dpl12", "tenant12", deplReq);
    }

    @Test(expected = ServiceAlreadyExistsException.class)
    public final void testPutDeployment_srvcExistError() throws Exception {

        DeploymentRequest deplReq = new DeploymentRequest("serviceTypeId", null);

        HttpClientErrorException httpException =
            new HttpClientErrorException(HttpStatus.CONFLICT, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.putDeployment("dpl12", "tenant12", deplReq);
    }

    @Test(expected = ServerErrorException.class)
    public final void testPutDeployment_srvcError() throws Exception {

        DeploymentRequest deplReq = new DeploymentRequest("serviceTypeId", null);

        HttpServerErrorException httpException =
            new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.putDeployment("dpl12", "tenant12", deplReq);
    }

    @Test
    public final void testDeleteDeployment() throws Exception {
        DeploymentResponseLinks newDeplItemLink =
            new DeploymentResponseLinks("selfUrl", "statusUrl");
        DeploymentResponse expectedDeplItem = new DeploymentResponse("req1", newDeplItemLink);

        ResponseEntity<DeploymentResponse> response =
            new ResponseEntity<DeploymentResponse>(expectedDeplItem, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any())).thenReturn(response);

        CloudifyDeployment cfyDepl = 
            new CloudifyDeployment("description", "blueprint_id", "created_at", "updated_at", 
                "id", null, null, null, null, null, null, null, "tenant_name");

        when(cfyClient.getDeploymentResource(Matchers.anyString(), Matchers.anyString())).thenReturn(cfyDepl);

        subject.deleteDeployment("deploymentId", "tenant");
    }

    @Test(expected = ServerErrorException.class)
    public final void testDeleteDeployment_serverError() throws Exception {
        HttpServerErrorException httpException =
            new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.deleteDeployment("deploymentId", "tenant");
    }

    @Test(expected = DeploymentNotFoundException.class)
    public final void testDeleteDeployment_notFoundError() throws Exception {

        HttpServerErrorException httpException =
            new HttpServerErrorException(HttpStatus.NOT_FOUND, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.deleteDeployment("deploymentId", "tenant");
    }

    @Test(expected = BadRequestException.class)
    public final void testDeleteDeployment_badReqError() throws Exception {

        HttpClientErrorException httpException =
            new HttpClientErrorException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.deleteDeployment("deploymentId", "tenant");
    }

    @Test(expected = DownstreamException.class)
    public final void testDeleteDeployment_downstreamError() throws Exception {

        HttpServerErrorException httpException =
            new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "statusText");

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<DeploymentResponse>>any()))
                .thenThrow(httpException);

        subject.deleteDeployment("deploymentId", "tenant");
    }

}
