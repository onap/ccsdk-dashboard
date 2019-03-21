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
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.model.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DashboardProperties.class})
public class ConsulRestClientImplTest {

    @Mock
    RestTemplate mockRest;

    @InjectMocks
    ConsulRestClientImpl subject = new ConsulRestClientImpl();

    protected final ObjectMapper objectMapper = new ObjectMapper();
    HttpClientErrorException httpException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        httpException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "statusText");
        PowerMockito.mockStatic(DashboardProperties.class);
        when(DashboardProperties.getControllerProperty("dev",
            DashboardProperties.CONTROLLER_SUBKEY_CONSUL_URL)).thenReturn("https://invt.com");
        this.subject.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetServiceHealth() {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", 190199, 199395);

        List<ConsulServiceHealth> expectedCnslSrvcHlth = new ArrayList<ConsulServiceHealth>();
        expectedCnslSrvcHlth.add(consulSrvcHlth);

        ResponseEntity<List<ConsulServiceHealth>> response =
            new ResponseEntity<List<ConsulServiceHealth>>(expectedCnslSrvcHlth, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulServiceHealth>>>any()))
                .thenReturn(response);

        List<ConsulServiceHealth> actualCnslSrvcHlth = subject.getServiceHealth("dc1", "srvc1");
        assertTrue(actualCnslSrvcHlth.get(0).node.equals("cjlvmcnsl00"));
    }

    @Test
    @Ignore
    public final void testGetServices() {
        List<String> srvcIps = new ArrayList<String>();
        srvcIps.add("135.91.224.136");
        srvcIps.add("135.91.224.138");
        ConsulServiceInfo consulSrvcInfo = new ConsulServiceInfo("pgaasServer1", srvcIps);

        List<ConsulServiceInfo> expectedCnslSrvcs = new ArrayList<ConsulServiceInfo>();
        expectedCnslSrvcs.add(consulSrvcInfo);

        // ResponseEntity<Map<String, Object>> response = new ResponseEntity<Map<String,
        // Object>>(consulSrvcInfo);

        subject.getServices("dc1");
    }

    @Test
    public final void testGetNodes() {
        ConsulNodeInfo cnslNode = new ConsulNodeInfo("a2788806-6e2e-423e-8ee7-6cad6f3d3de6",
            "cjlvmcnsl00", "10.170.8.13", null, null, 6, 17980);

        List<ConsulNodeInfo> cnslNodeList = new ArrayList<ConsulNodeInfo>();
        cnslNodeList.add(cnslNode);

        ResponseEntity<List<ConsulNodeInfo>> response =
            new ResponseEntity<List<ConsulNodeInfo>>(cnslNodeList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulNodeInfo>>>any())).thenReturn(response);

        List<ConsulNodeInfo> actualNodeList = subject.getNodes("dc1");
        assertTrue(actualNodeList.get(0).node.equals("cjlvmcnsl00"));

    }

    @Test
    public final void testGetNodeServicesHealth() {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", 190199, 199395);

        List<ConsulServiceHealth> expectedCnslSrvcHlth = new ArrayList<ConsulServiceHealth>();
        expectedCnslSrvcHlth.add(consulSrvcHlth);

        ResponseEntity<List<ConsulServiceHealth>> response =
            new ResponseEntity<List<ConsulServiceHealth>>(expectedCnslSrvcHlth, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulServiceHealth>>>any()))
                .thenReturn(response);

        List<ConsulServiceHealth> actualNodeHlthList =
            subject.getNodeServicesHealth("dc1", "nodeId1");
        assertTrue(actualNodeHlthList.get(0).node.equals("cjlvmcnsl00"));
    }

    @Test
    public final void testGetDatacenters() {
        ConsulDatacenter cnslDc = new ConsulDatacenter("dc1");

        List<ConsulDatacenter> cnslDcList = new ArrayList<ConsulDatacenter>();
        cnslDcList.add(cnslDc);
        List<String> dcItems = new ArrayList<String>();
        dcItems.add("dc1");

        ResponseEntity<List<String>> response =
            new ResponseEntity<List<String>>(dcItems, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<String>>>any())).thenReturn(response);
        List<ConsulDatacenter> actualDcList = subject.getDatacenters();
        assertTrue(actualDcList.get(0).name.equals("dc1"));
    }

    @Test
    @Ignore
    public final void testRegisterService() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testDeregisterService() {

        JSONObject jsonResp = new JSONObject();

        ResponseEntity<JSONObject> result = new ResponseEntity<JSONObject>(jsonResp, HttpStatus.OK);
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<JSONObject>>any()))
                .thenReturn(result);

        int actualCode = subject.deregisterService("service1");
        assertTrue(actualCode == 200);
    }

}
