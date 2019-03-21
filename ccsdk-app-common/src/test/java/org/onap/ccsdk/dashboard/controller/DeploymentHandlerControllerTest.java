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

package org.onap.ccsdk.dashboard.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequestObject;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponseLinks;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class DeploymentHandlerControllerTest extends MockitoTestSuite {

    @Mock
    DeploymentHandlerClient restClient;

    @InjectMocks
    DeploymentHandlerController subject = new DeploymentHandlerController();

    protected final ObjectMapper objectMapper = new ObjectMapper();

    BadRequestException badReqError;
    ServiceAlreadyExistsException srvcExistError;
    ServerErrorException serverError;
    DownstreamException downStrmError;
    JsonProcessingException jsonError;
    DeploymentNotFoundException notFoundError;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        mockedRequest = getMockedRequest();
        mockedResponse = getMockedResponse();
        badReqError = new BadRequestException("bad request");
        srvcExistError = new ServiceAlreadyExistsException("service already exists");
        serverError = new ServerErrorException("Error occured in server");
        downStrmError = new DownstreamException("error occured in downstream");
        notFoundError = new DeploymentNotFoundException("item not found");
    }

    @After
    public void tearDown() throws Exception {
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPutDeployment_create() throws Exception {
        DeploymentRequestObject expectReq =
            new DeploymentRequestObject("dep1", "dep1", null, "tenant1", "create");

        DeploymentResponseLinks expectLink = new DeploymentResponseLinks("self", "status");
        DeploymentResponse expectResp = new DeploymentResponse("req1", expectLink);

        when(restClient.putDeployment(Matchers.anyString(), Matchers.anyString(),
            Matchers.<DeploymentRequest>any())).thenReturn(expectResp).thenThrow(badReqError)
                .thenThrow(srvcExistError).thenThrow(serverError).thenThrow(downStrmError)
                .thenThrow(Exception.class);

        String actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("req1"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPutDeployment_update() throws Exception {
        DeploymentRequestObject expectReq =
            new DeploymentRequestObject("dep1", "dep1", null, "tenant1", "update");

        DeploymentResponseLinks expectLink = new DeploymentResponseLinks("self", "status");
        DeploymentResponse expectResp = new DeploymentResponse("req1", expectLink);

        when(restClient.updateDeployment(Matchers.anyString(), Matchers.anyString(),
            Matchers.<DeploymentRequest>any())).thenReturn(expectResp).thenThrow(badReqError)
                .thenThrow(srvcExistError).thenThrow(serverError).thenThrow(downStrmError)
                .thenThrow(Exception.class);

        String actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("req1"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.putDeployment(mockedRequest, expectReq);
        assertTrue(actualResp.contains("error"));
    }

    @Test
    public final void testDeleteDeployment() throws Exception {

        doNothing().doThrow(badReqError).doThrow(serverError).doThrow(downStrmError)
            .doThrow(notFoundError).doThrow(Exception.class).when(restClient)
            .deleteDeployment(Matchers.anyString(), Matchers.anyString());

        StringBuffer expectedStrBuff = new StringBuffer();
        expectedStrBuff.append("http://oom.s2.com");
        when(mockedRequest.getRequestURL()).thenReturn(expectedStrBuff);

        String actual = subject.deleteDeployment("dep1", mockedRequest, "tenant1", mockedResponse);
        assertFalse(actual.contains("error"));

        actual = subject.deleteDeployment("dep1", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dep1", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dep1", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dep1", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dep1", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

    }

}
