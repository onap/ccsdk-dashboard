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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.model.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.model.RestResponseSuccess;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class ConsulControllerTest extends MockitoTestSuite {

    @Mock
    ConsulClient consulClient;

    @InjectMocks
    ConsulController subject = new ConsulController();

    protected final ObjectMapper objectMapper = new ObjectMapper();

    MockUser mockUser = new MockUser();
    HttpClientErrorException httpException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        mockedRequest = getMockedRequest();
        mockedResponse = getMockedResponse();
        httpException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "statusText");
    }

    @After
    public void tearDown() throws Exception {
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetServiceHealthDetails() throws Exception {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", 190199, 199395);

        List<ConsulServiceHealth> expectedCnslSrvcHlth = new ArrayList<ConsulServiceHealth>();
        expectedCnslSrvcHlth.add(consulSrvcHlth);

        when(consulClient.getServiceHealth(Matchers.anyString(), Matchers.anyString()))
            .thenReturn(expectedCnslSrvcHlth).thenThrow(Exception.class);

        String actualResult = subject.getServiceHealthDetails(mockedRequest, "dc1", "srvc1");
        assertTrue(actualResult.contains("pgaasServer1"));

        actualResult = subject.getServiceHealthDetails(mockedRequest, "dc1", "srvc1");
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetServicesHealth() throws Exception {

        List<String> srvcIps = new ArrayList<String>();
        srvcIps.add("135.91.224.136");
        srvcIps.add("135.91.224.138");
        ConsulServiceInfo consulSrvcInfo = new ConsulServiceInfo("pgaasServer1", srvcIps);

        List<ConsulServiceInfo> expectedCnslSrvcs = new ArrayList<ConsulServiceInfo>();
        expectedCnslSrvcs.add(consulSrvcInfo);

        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", 190199, 199395);

        List<ConsulServiceHealth> expectedCnslSrvcHlth = new ArrayList<ConsulServiceHealth>();
        expectedCnslSrvcHlth.add(consulSrvcHlth);

        when(consulClient.getServices(Matchers.anyString())).thenReturn(expectedCnslSrvcs);
        when(consulClient.getServiceHealth(Matchers.anyString(), Matchers.anyString()))
            .thenReturn(expectedCnslSrvcHlth).thenThrow(Exception.class);

        String actualResult = subject.getServicesHealth(mockedRequest, "dc1");
        assertTrue(actualResult.contains("pgaasServer1"));

        actualResult = subject.getServicesHealth(mockedRequest, "dc1");
        assertTrue(actualResult.contains("error"));
    }

    @Test
    public final void testGetNodesInfo() {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        user.setId(1000L);
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);

        ConsulNodeInfo cnslNode = new ConsulNodeInfo("a2788806-6e2e-423e-8ee7-6cad6f3d3de6",
            "cjlvmcnsl00", "10.170.8.13", null, null, 6, 17980);

        List<ConsulNodeInfo> cnslNodeList = new ArrayList<ConsulNodeInfo>();
        cnslNodeList.add(cnslNode);

        when(consulClient.getNodes(Matchers.anyString())).thenReturn(cnslNodeList);

        String actualResult = subject.getNodesInfo(mockedRequest, "dc1");
        assertTrue(actualResult.contains("cjlvmcnsl00"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetNodeServicesHealth() throws Exception {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", 190199, 199395);

        List<ConsulServiceHealth> expectedCnslSrvcHlth = new ArrayList<ConsulServiceHealth>();
        expectedCnslSrvcHlth.add(consulSrvcHlth);

        when(consulClient.getNodeServicesHealth(Matchers.anyString(), Matchers.anyString()))
            .thenReturn(expectedCnslSrvcHlth).thenThrow(Exception.class);

        String actualResult = subject.getNodeServicesHealth(mockedRequest, "dc1", "node1");
        assertTrue(actualResult.contains("pgaasServer1"));

        actualResult = subject.getNodeServicesHealth(mockedRequest, "dc1", "node1");
        assertTrue(actualResult.contains("error"));
    }

    @Test
    public final void testGetDatacentersHealth() {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        user.setId(1000L);
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);

        ConsulDatacenter cnslDc = new ConsulDatacenter("dc1");

        List<ConsulDatacenter> cnslDcList = new ArrayList<ConsulDatacenter>();
        cnslDcList.add(cnslDc);

        when(consulClient.getDatacenters()).thenReturn(cnslDcList);

        String actualResult = subject.getDatacentersHealth(mockedRequest);
        assertTrue(actualResult.contains("dc1"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRegisterService() throws Exception {
        ConsulHealthServiceRegistration.EndpointCheck endPoint =
            new ConsulHealthServiceRegistration.EndpointCheck("endpoint1", "interval1",
                "description1", "name1");
        List<ConsulHealthServiceRegistration.EndpointCheck> endPointList =
            new ArrayList<ConsulHealthServiceRegistration.EndpointCheck>();
        endPointList.add(endPoint);

        List<String> tagList = new ArrayList<String>();
        tagList.add("tag1");
        tagList.add("tag2");

        ConsulHealthServiceRegistration.ConsulServiceRegistration servcReg =
            new ConsulHealthServiceRegistration.ConsulServiceRegistration("id1", "name1",
                "address1", "port1", tagList, endPointList);
        List<ConsulHealthServiceRegistration.ConsulServiceRegistration> servcRegList =
            new ArrayList<ConsulHealthServiceRegistration.ConsulServiceRegistration>();
        servcRegList.add(servcReg);

        ConsulHealthServiceRegistration chsrObj = new ConsulHealthServiceRegistration(servcRegList);

        RestResponseSuccess expectedResp = new RestResponseSuccess("Registration yielded code 0");
        String expectedResult = objectMapper.writeValueAsString(expectedResp);

        String expectedStr = "Registration yielded code 0";
        when(consulClient.registerService(Matchers.<ConsulHealthServiceRegistration>any()))
            .thenReturn(expectedStr).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.registerService(mockedRequest, chsrObj);
        assertTrue(actualResult.equals(expectedResult));

        actualResult = subject.registerService(mockedRequest, chsrObj);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.registerService(mockedRequest, chsrObj);
        assertTrue(actualResult.contains("error"));
    }

    @Test
    public final void testRegisterService_SrvcError() throws Exception {
        ConsulHealthServiceRegistration chsrObj = new ConsulHealthServiceRegistration(null);

        String actualResult = subject.registerService(mockedRequest, chsrObj);
        assertTrue(actualResult.contains("error"));
    }

    @Test
    @Ignore
    public final void testRegisterService_invalidSrvcError() throws Exception {
        ConsulHealthServiceRegistration.EndpointCheck endPoint =
            new ConsulHealthServiceRegistration.EndpointCheck("endpoint1", "interval1",
                "description1", "name1");
        List<ConsulHealthServiceRegistration.EndpointCheck> endPointList =
            new ArrayList<ConsulHealthServiceRegistration.EndpointCheck>();
        endPointList.add(endPoint);

        List<String> tagList = new ArrayList<String>();
        tagList.add("tag1");
        tagList.add("tag2");

        ConsulHealthServiceRegistration.ConsulServiceRegistration servcReg =
            new ConsulHealthServiceRegistration.ConsulServiceRegistration("id1", "name2",
                "address2", "port1", tagList, endPointList);
        List<ConsulHealthServiceRegistration.ConsulServiceRegistration> servcRegList =
            new ArrayList<ConsulHealthServiceRegistration.ConsulServiceRegistration>();
        servcRegList.add(servcReg);

        ConsulHealthServiceRegistration chsrObj = new ConsulHealthServiceRegistration(servcRegList);

        String actualResult = subject.registerService(mockedRequest, chsrObj);
        assertTrue(actualResult.contains("error"));
    }

    @Test
    public final void testRegisterService_invalidEndptError() throws Exception {
        ConsulHealthServiceRegistration.EndpointCheck endPoint =
            new ConsulHealthServiceRegistration.EndpointCheck("", "", "description1", "name1");
        List<ConsulHealthServiceRegistration.EndpointCheck> endPointList =
            new ArrayList<ConsulHealthServiceRegistration.EndpointCheck>();
        endPointList.add(endPoint);

        List<String> tagList = new ArrayList<String>();
        tagList.add("tag1");
        tagList.add("tag2");

        ConsulHealthServiceRegistration.ConsulServiceRegistration servcReg =
            new ConsulHealthServiceRegistration.ConsulServiceRegistration("id1", "", "", "port1",
                tagList, endPointList);
        List<ConsulHealthServiceRegistration.ConsulServiceRegistration> servcRegList =
            new ArrayList<ConsulHealthServiceRegistration.ConsulServiceRegistration>();
        servcRegList.add(servcReg);

        ConsulHealthServiceRegistration chsrObj = new ConsulHealthServiceRegistration(servcRegList);

        String actualResult = subject.registerService(mockedRequest, chsrObj);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testDeregisterService() throws Exception {
        RestResponseSuccess expectedResp = new RestResponseSuccess("Deregistration yielded code 0");
        String expectedResult = objectMapper.writeValueAsString(expectedResp);

        when(consulClient.deregisterService(Mockito.any())).thenReturn(0).thenThrow(Exception.class)
            .thenThrow(httpException);

        String actualResult = subject.deregisterService(mockedRequest, "srvc1");
        assertTrue(actualResult.equals(expectedResult));

        actualResult = subject.deregisterService(mockedRequest, "srvc1");
        assertTrue(actualResult.contains("error"));

        actualResult = subject.deregisterService(mockedRequest, "srvc1");
        assertTrue(actualResult.contains("error"));
    }

}
