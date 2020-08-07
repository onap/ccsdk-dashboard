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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;
import org.onap.ccsdk.dashboard.model.consul.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.consul.ConsulDeploymentHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceInfo;
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
    ConsulRestClientImpl subject;

    String[] svcTags = {"cfytenantname=onap"}; 
    
    protected final static ObjectMapper objectMapper = new ObjectMapper();
    static HttpClientErrorException httpException;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        httpException = new HttpClientErrorException(HttpStatus.FORBIDDEN, "statusText");
        objectMapper.registerModule(new Jdk8Module());    
    }
   
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(DashboardProperties.class);
        when(DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CONSUL_URL)).thenReturn("https://cnsl-svc.com");
        when(DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_URL)).thenReturn("https://orcl-svc.com");          
        when(DashboardProperties.getControllerProperty("site.primary",
                DashboardProperties.SITE_SUBKEY_CLOUDIFY_USERNAME)).thenReturn("admin");
        when(DashboardProperties.getControllerProperty("site.primary",
                DashboardProperties.SITE_SUBKEY_CLOUDIFY_PASS)).thenReturn("admin");
        subject.setConsul_acl_token("consul_acl_token_for_dash");
        subject.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetConsulAcl() {
        String secretTokenStr =
            "{\"created_at\": \"created_ts\", \"key\": \"acl_key\", \"updated_at\": \"updated_ts\", \"value\": \"acl_token_val\", \"visibility\": \"global\", \"is_hidden_value\": \"false\", \"tenant_name\": \"tenant\", \"resource_availability\": \"rsrc\"}";          
        CloudifySecret sampleData = null;
        try {
            sampleData = objectMapper.readValue(secretTokenStr, CloudifySecret.class);
        } catch (Exception e) {
        }
        
        ResponseEntity<CloudifySecret> response =
            new ResponseEntity<CloudifySecret>(sampleData, HttpStatus.OK);
        
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifySecret>>any()))
                .thenReturn(response);
        String actualSecretStr = subject.getConsulAcl(mockRest);
        assertTrue(actualSecretStr.equals("acl_token_val"));
    }

    @Test(expected = Exception.class)
    public final void testCreateCfyRestTemplate() throws Exception {
        String webapiUrl = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_URL);         
        String user = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_USERNAME);
        String pass = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_PASS);
        URL url = null;
        try {
            url = new URL(webapiUrl);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String urlScheme = webapiUrl.split(":")[0];
        subject.createCfyRestTemplate(url, user, pass, urlScheme);
    }
    
    @Test
    public final void testGetServiceHealth() {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "OK Output:",
            "pgaas1_Service_ID", "pgaasServer1", svcTags, 34234, 4234);

       // new ConsulServiceHealth()
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

    @Test(expected = Exception.class)
    public final void get_svc_health_rest_client_exception() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulServiceHealth>>>any()))
                .thenThrow(httpException);
        
        subject.getServiceHealth("dc1", "srvc1");      
    }
    
    @Test(expected = Exception.class)
    public final void get_services_rest_client_exception() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<Map<String, Object>>>any()))
            .thenThrow(httpException);
        subject.getServices("dc1");
    }
    
    @Test
    public final void testGetServices() {
        List<String> srvcIps = new ArrayList<String>();
        srvcIps.add("135.91.224.136");
        srvcIps.add("135.91.224.138");
        ConsulServiceInfo consulSrvcInfo = new ConsulServiceInfo("pgaasServer1", srvcIps);

        Map<String, Object> respObj = new HashMap<String, Object>();
        respObj.put("pgaasServer1", srvcIps);
        
        List<ConsulServiceInfo> expectedCnslSrvcs = new ArrayList<ConsulServiceInfo>();
        expectedCnslSrvcs.add(consulSrvcInfo);

        ResponseEntity<Map<String, Object>> response = 
            new ResponseEntity<Map<String,Object>>(respObj, HttpStatus.OK);
        
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<Map<String, Object>>>any())).thenReturn(response);

        List<ConsulServiceInfo> actualSvcList = subject.getServices("dc1");
        assertTrue(actualSvcList.get(0).name.equals("pgaasServer1"));
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

    @Test(expected = Exception.class)
    public final void get_nodes_rest_client_exception() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulNodeInfo>>>any()))
            .thenThrow(httpException);
        subject.getNodes("dc1");
    }
     
    @Test
    public final void testGetNodeServicesHealth() {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", svcTags, 190199, 199395);

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

    @Test(expected = Exception.class)
    public final void get_node_svc_rest_client_exception() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulServiceHealth>>>any()))
            .thenThrow(httpException);
        subject.getNodeServicesHealth("dc1", "nodeId1");
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

    @Test(expected = Exception.class)
    public final void get_dc_rest_client_exception() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<String>>>any()))
            .thenThrow(httpException);
        subject.getDatacenters();
    }
    
    @Test
    public final void testGetServiceHealthByDeploymentId() {
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", svcTags, 190199, 199395);

        List<ConsulServiceHealth> expectedCnslSrvcHlth = new ArrayList<ConsulServiceHealth>();
        expectedCnslSrvcHlth.add(consulSrvcHlth);

        ResponseEntity<List<ConsulServiceHealth>> response =
            new ResponseEntity<List<ConsulServiceHealth>>(expectedCnslSrvcHlth, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulServiceHealth>>>any()))
                .thenReturn(response);

        ConsulDeploymentHealth cdh = subject.getServiceHealthByDeploymentId("deploymentId");
        assertTrue(cdh.getNode().equals("cjlvmcnsl00"));
    }
    
    @Test(expected = Exception.class)
    public final void get_svc_health_by_depId_rest_client_exception() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<List<ConsulServiceHealth>>>any()))
            .thenThrow(httpException);
        subject.getServiceHealthByDeploymentId("deploymentId");
    }
    
}
