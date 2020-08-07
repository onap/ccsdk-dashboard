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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite.MockHttpServletRequestWrapper;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprint;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEvent;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEventList.Metadata;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeId;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstance;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceId;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPlugin;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPluginList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenantList;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DashboardProperties.class})
public class CloudifyRestClientImplTest extends MockitoTestSuite {

    @Mock
    RestTemplate mockRest;

    @InjectMocks
    CloudifyRestClientImpl subject = new CloudifyRestClientImpl();
    
    HttpServletRequest mockedRequest;
    HttpServletResponse mockedResponse;
    protected final static ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        PowerMockito.mockStatic(DashboardProperties.class);
        when(DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_URL)).thenReturn("https://orcl.com");
        CacheManager testCache = new CacheManager();
        subject.setCacheManager(testCache); 
        this.subject.init();
    }

    @Test
    public final void getEventlogsTest()
        throws JsonParseException, JsonMappingException, IOException {
        String executionId = "123a123a";
        String tenant = "thisTenant";
        List<CloudifyEvent> items = new ArrayList<CloudifyEvent>();
        CloudifyEvent aMockEvent = new CloudifyEvent(
            "dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02",
            "dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02", null, "workflow_succeeded",
            "5f8a2e05-e187-4925-90de-ece9160aa517", "warning",
            "ctx.7a10e191-f12b-4142-aa5d-6e5766ebb1d4", "install workflow execution succeeded",
            "publish_l36bhr", "publish", "cloudify.interfaces.lifecycle.create",
            "2019-02-28T23:17:49.228Z", "2019-02-28T23:17:49.700Z", "cloudify_event", "install");
        items.add(aMockEvent);
        items.add(new CloudifyEvent("dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02",
            "dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02", null, "workflow_node_event",
            "5f8a2e05-e187-4925-90de-ece9160aa517", "warning",
            "ctx.7a10e191-f12b-4142-aa5d-6e5766ebb1d4", "Starting node", "publish_l36bhr",
            "publish", "cloudify.interfaces.lifecycle.create", "2019-02-28T23:17:48.391Z",
            "2019-02-28T23:17:48.516Z", "cloudify_event", "install"));

        String metaInfo =
            "metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}";
        Metadata metadata = null;

        CloudifyEventList expected = new CloudifyEventList(items, metadata);

        ResponseEntity<CloudifyEventList> response =
            new ResponseEntity<CloudifyEventList>(expected, HttpStatus.OK);
        Mockito
            .when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<?>>any(),
                Matchers.<ParameterizedTypeReference<CloudifyEventList>>any()))
            .thenReturn(response);
        CloudifyEventList actual = subject.getEventlogs(executionId, tenant);
        assertTrue(actual.items.size() == 2);

    }

    @Test
    public final void testGetTenants_GetData() {
        // define the entity you want the exchange to return
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"dName\": null, \"name\": \"default_tenant\"}, {\"id\": 2, \"dName\": null, \"name\": \"dyh1b1902\"}], \"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList sampleData = null;
        try {
            sampleData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }

        ResponseEntity<CloudifyTenantList> response =
            new ResponseEntity<CloudifyTenantList>(sampleData, HttpStatus.OK);
        Mockito
            .when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<?>>any(),
                Matchers.<ParameterizedTypeReference<CloudifyTenantList>>any()))
            .thenReturn(response);

        CloudifyTenantList res = subject.getTenants();
        assertNotNull(res);
        assertThat(res.items.get(1).name, is("dyh1b1902"));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = RestClientException.class)
    public final void testGetTenants_withException() {
        // define the entity you want the exchange to return
        Mockito
            .when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<?>>any(),
                Matchers.<ParameterizedTypeReference<CloudifyTenantList>>any()))
            .thenThrow(RestClientException.class);
        subject.getTenants();
    }

    @Test
    public final void testGetNodeInstanceId() {
        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");
        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();
        cfyNodeInstItems.add(cfyNodeInst);
        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);

        ResponseEntity<CloudifyNodeInstanceIdList> response =
            new ResponseEntity<CloudifyNodeInstanceIdList>(cfyNodeInstList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeInstanceIdList>>any()))
                .thenReturn(response);

        CloudifyNodeInstanceIdList actualResult =
            subject.getNodeInstanceId("deploymentId", "nodeId", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("node_instance_id1"));
    }

    @Test
    public final void testGetNodeInstanceVersion() {
        CloudifyNodeInstance cfyNodeInstance = new CloudifyNodeInstance("id1", null);

        List<CloudifyNodeInstance> cfyNodeInstanceItems = new ArrayList<CloudifyNodeInstance>();
        cfyNodeInstanceItems.add(cfyNodeInstance);

        CloudifyNodeInstanceList cfyNodeInstList =
            new CloudifyNodeInstanceList(cfyNodeInstanceItems, null);

        ResponseEntity<CloudifyNodeInstanceList> response =
            new ResponseEntity<CloudifyNodeInstanceList>(cfyNodeInstList, HttpStatus.OK);
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeInstanceList>>any()))
                .thenReturn(response);

        CloudifyNodeInstanceList actualResult =
            subject.getNodeInstanceVersion("deploymentId", "nodeId", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("id1"));
    }

    @Test
    public final void testGetNodeInstanceVersion_blueprint() {
        CloudifyNodeId cfyNodeId = new CloudifyNodeId("node_id");
        List<CloudifyNodeId> cfyNodeIdItems = new ArrayList<CloudifyNodeId>();
        cfyNodeIdItems.add(cfyNodeId);
        CloudifyNodeIdList cfyNodeIdList = new CloudifyNodeIdList(cfyNodeIdItems, null);

        ResponseEntity<CloudifyNodeIdList> response1 =
            new ResponseEntity<CloudifyNodeIdList>(cfyNodeIdList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("nodes"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeIdList>>any())).thenReturn(response1);

        CloudifyNodeInstance cfyNodeInstance = new CloudifyNodeInstance("id1", null);

        List<CloudifyNodeInstance> cfyNodeInstanceItems = new ArrayList<CloudifyNodeInstance>();
        cfyNodeInstanceItems.add(cfyNodeInstance);

        CloudifyNodeInstanceList cfyNodeInstList =
            new CloudifyNodeInstanceList(cfyNodeInstanceItems, null);

        ResponseEntity<CloudifyNodeInstanceList> response2 =
            new ResponseEntity<CloudifyNodeInstanceList>(cfyNodeInstList, HttpStatus.OK);
        when(mockRest.exchange(Matchers.contains("node-instances"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeInstanceList>>any()))
                .thenReturn(response2);

        CloudifyNodeInstanceList actualResult =
            subject.getNodeInstanceVersion("blueprintId", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("id1"));
    }

    @Test
    public final void testGetNodeInstanceId_blueprint() {
        CloudifyNodeId cfyNodeId = new CloudifyNodeId("node_id");
        List<CloudifyNodeId> cfyNodeIdItems = new ArrayList<CloudifyNodeId>();
        cfyNodeIdItems.add(cfyNodeId);
        CloudifyNodeIdList cfyNodeIdList = new CloudifyNodeIdList(cfyNodeIdItems, null);

        ResponseEntity<CloudifyNodeIdList> response1 =
            new ResponseEntity<CloudifyNodeIdList>(cfyNodeIdList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("nodes"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeIdList>>any())).thenReturn(response1);

        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");
        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();
        cfyNodeInstItems.add(cfyNodeInst);
        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);

        ResponseEntity<CloudifyNodeInstanceIdList> response =
            new ResponseEntity<CloudifyNodeInstanceIdList>(cfyNodeInstList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("node-instances"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeInstanceIdList>>any()))
                .thenReturn(response);

        CloudifyNodeInstanceIdList actualResult = subject.getNodeInstanceId("bpId", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("node_instance_id1"));
    }

    @Test
    public void testGetExecutions() {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, 
                "bp1", "id1","tenant1", "error", "execution_id1", null);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        ResponseEntity<CloudifyExecutionList> response =
            new ResponseEntity<CloudifyExecutionList>(cloudifyExecutionList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("deployment_id"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyExecutionList>>any()))
                .thenReturn(response);

        CloudifyExecutionList actualResult = subject.getExecutions("deploymentId1", "tenant1");
        assertTrue(actualResult.items.get(0).id.contains("id1"));
    }

    @Test
    public void testGetExecutionsSummary() {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        ResponseEntity<CloudifyExecutionList> response =
            new ResponseEntity<CloudifyExecutionList>(cloudifyExecutionList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("include"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyExecutionList>>any()))
                .thenReturn(response);

        CloudifyExecutionList actualResult =
            subject.getExecutionsSummary("deploymentId1", "tenant1");
        assertTrue(actualResult.items.get(0).id.contains("id1"));
    }

    @Test
    public void testStartExecution() {
        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "ended-at", "install",
            false, "bp1", "id1", "tenant1", "error", "execution_id1", null);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key1", "value1");

        CloudifyExecutionRequest cfyExecReq = new CloudifyExecutionRequest("deployment_id",
            "upgrade", false, false, "tenant1", params);

        when(mockRest.postForObject(Matchers.anyString(), Matchers.<HttpEntity<?>>any(),
            Matchers.<Class<CloudifyExecution>>any())).thenReturn(cfyExecObj);

        CloudifyExecution actualResult = subject.startExecution(cfyExecReq);
        assertTrue(actualResult.status.equals("successful"));
    }

    @Test
    @Ignore
    public void testCancelExecution() {
        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "end_at", "install",
            false, "bp1", "id1", "tenant1", "error", "execution_id1", null);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key1", "value1");

        ResponseEntity<CloudifyExecution> response =
            new ResponseEntity<CloudifyExecution>(cfyExecObj, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("executions"), Matchers.eq(HttpMethod.POST),
            Matchers.<HttpEntity<?>>any(), Matchers.<Class<CloudifyExecution>>any()))
                .thenReturn(response);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key1", "value1");

        CloudifyExecution actualResult =
            subject.cancelExecution("executionId", parameters, "tenant");
        assertTrue(actualResult.id.equals("execution_id1"));
    }

    @Test
    public void testGetBlueprint() {
        CloudifyBlueprint cldBp =
            new CloudifyBlueprint("file1", "description1", "343242", "3423423", "id1", null);

        List<CloudifyBlueprint> items = new ArrayList<CloudifyBlueprint>();
        items.add(cldBp);

        CloudifyBlueprintList.Metadata.Pagination pageObj =
            new CloudifyBlueprintList.Metadata.Pagination(1, 0, 1);

        CloudifyBlueprintList.Metadata metadata = new CloudifyBlueprintList.Metadata(pageObj);

        CloudifyBlueprintList cldBpList = new CloudifyBlueprintList(items, metadata);

        ResponseEntity<CloudifyBlueprintList> response =
            new ResponseEntity<CloudifyBlueprintList>(cldBpList, HttpStatus.OK);

        doReturn(response).when(mockRest).exchange(Matchers.anyString(),
            Matchers.eq(HttpMethod.GET), Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyBlueprintList>>any());

        CloudifyBlueprintList actualResult = subject.getBlueprint("id1", "tenant1");
        assertTrue(actualResult.items.get(0).id.equals("id1"));
    }

    @Test
    public void testGetDeployments() {
        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        ResponseEntity<CloudifyDeploymentList> response =
            new ResponseEntity<CloudifyDeploymentList>(cldDeployList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<CloudifyDeploymentList>>any()))
                .thenReturn(response);
        
        CloudifyDeploymentList actualResult = subject.getDeployments("tenant", 10, 0);
        assertTrue(actualResult.items.get(0).id.equals("id"));
    }
    
    @Test
    public void testGetDeployments_from_cache() {
        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        ResponseEntity<CloudifyDeploymentList> response =
            new ResponseEntity<CloudifyDeploymentList>(cldDeployList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<CloudifyDeploymentList>>any()))
                .thenReturn(response);
        
        List<CloudifyDeployment> actualResult = subject.getDeployments("tenant", 10, 0, true, true);
        assertTrue(actualResult.get(0).id.equals("id"));
    }
    
    @Test
    public void testGetDeploymentForBlueprint() {
        CloudifyDeployedTenant cfyDepTenant = 
            new CloudifyDeployedTenant("id", "tenant", "created_at", "updated_at");
        List<CloudifyDeployedTenant> cfyDepTenantList = new ArrayList<>();
        cfyDepTenantList.add(cfyDepTenant);
            
        CloudifyDeployedTenantList.Metadata.Pagination pageObj =
            new CloudifyDeployedTenantList.Metadata.Pagination(1, 0, 1);
        CloudifyDeployedTenantList.Metadata metadata = new CloudifyDeployedTenantList.Metadata(pageObj);

        CloudifyDeployedTenantList cldDeployList = new CloudifyDeployedTenantList(cfyDepTenantList, metadata);

        ResponseEntity<CloudifyDeployedTenantList> response =
            new ResponseEntity<CloudifyDeployedTenantList>(cldDeployList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyDeployedTenantList>>any()))
                .thenReturn(response);
        
        List<CloudifyDeployedTenant> actuals = subject.getDeploymentForBlueprint("bpId");
        assertTrue(actuals.get(0).id.equals("id"));
    }
    
    @Test
    public void testGetDeploymentResource() {        
        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        ResponseEntity<CloudifyDeploymentList> response =
            new ResponseEntity<CloudifyDeploymentList>(cldDeployList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<CloudifyDeploymentList>>any()))
                .thenReturn(response);

        CloudifyDeployment actualResult = subject.getDeploymentResource("id", "tenant");  
        assertTrue(actualResult.id.equals("id"));
    }
    
    @Test
    public void testGetNodeInstanceDetails() {
        CloudifyNodeInstance cfyNodeInstance = new CloudifyNodeInstance("id1", null);

        List<CloudifyNodeInstance> cfyNodeInstanceItems = new ArrayList<CloudifyNodeInstance>();
        cfyNodeInstanceItems.add(cfyNodeInstance);

        CloudifyNodeInstanceList cfyNodeInstList =
            new CloudifyNodeInstanceList(cfyNodeInstanceItems, null);

        ResponseEntity<CloudifyNodeInstanceList> response2 =
            new ResponseEntity<CloudifyNodeInstanceList>(cfyNodeInstList, HttpStatus.OK);
        when(mockRest.exchange(Matchers.contains("node-instances"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeInstanceList>>any()))
                .thenReturn(response2);       
        
        CloudifyNodeInstanceList actualResult = 
            subject.getNodeInstanceDetails("id", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("id1"));
    }
    
    @Test
    public void testGetNodeInstances() {
        CloudifyNodeInstanceId cfyNodeInstance = new CloudifyNodeInstanceId("id1");

        List<CloudifyNodeInstanceId> cfyNodeInstanceItems = new ArrayList<CloudifyNodeInstanceId>();
        cfyNodeInstanceItems.add(cfyNodeInstance);

        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstanceItems, null);

        ResponseEntity<CloudifyNodeInstanceIdList> response2 =
            new ResponseEntity<CloudifyNodeInstanceIdList>(cfyNodeInstList, HttpStatus.OK);
        when(mockRest.exchange(Matchers.contains("node-instances"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyNodeInstanceIdList>>any()))
                .thenReturn(response2);       
        
        CloudifyNodeInstanceIdList actualResult = 
            subject.getNodeInstances("id", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("id1"));
    }
    
    
    @Test    
    public void testGetExecutionsSummaryPerTenant() {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        ResponseEntity<CloudifyExecutionList> response =
            new ResponseEntity<CloudifyExecutionList>(cloudifyExecutionList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("include"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyExecutionList>>any()))
                .thenReturn(response);

        CloudifyExecutionList actualResult =
            subject.getExecutionsSummaryPerTenant("tenant1");
        assertTrue(actualResult.items.get(0).id.contains("id1"));
    }
    
    @Test
    public void testGetExecutionIdSummary() {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);
        ResponseEntity<CloudifyExecution> response =
            new ResponseEntity<CloudifyExecution>(cldExecution, HttpStatus.OK);
        when(mockRest.exchange(Matchers.contains("include"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyExecution>>any()))
                .thenReturn(response);
        CloudifyExecution actuals = subject.getExecutionIdSummary("execution_id1", "tenant");
        assertTrue(actuals.id.contains("execution_id1"));
    }
    
    @Test
    public void testGetInstallExecutionSummary() {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        ResponseEntity<CloudifyExecutionList> response =
            new ResponseEntity<CloudifyExecutionList>(cloudifyExecutionList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.contains("include"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyExecutionList>>any()))
                .thenReturn(response);

        CloudifyExecutionList actualResult =
            subject.getInstallExecutionSummary("id1", "tenant1");
        assertTrue(actualResult.items.get(0).id.contains("id1"));
    }
    
    @Test
    public void testViewBlueprint() {
        byte[] outArr =  "Any String you want".getBytes();
        ResponseEntity<byte[]> response = 
            new ResponseEntity<byte[]>(outArr, HttpStatus.OK);

        doReturn(response).when(mockRest).exchange(Matchers.anyString(),
            Matchers.eq(HttpMethod.GET), Matchers.<HttpEntity<?>>any(),
            Matchers.<Class<?>>any());
        
        subject.viewBlueprint("tenant1", "id1");
    }
    
    @Test
    public void testGetDeploymentNamesWithFilter() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        //mockedRequest.addParameter("filters", filterStr);
        //mockedRequest.addParameter("sort", "name");
        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("ECOMPC_DCAE_WRITE");
        userApps.add("dcae");
        
        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);
        
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"dName\": null, \"name\": \"default_tenant\"}, {\"id\": 2, \"dName\": null, \"name\": \"dyh1b1902\"}], \"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList sampleData = null;
        try {
            sampleData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }

        ResponseEntity<CloudifyTenantList> response1 =
            new ResponseEntity<CloudifyTenantList>(sampleData, HttpStatus.OK);

        
        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        ResponseEntity<CloudifyDeploymentList> response2 =
            new ResponseEntity<CloudifyDeploymentList>(cldDeployList, HttpStatus.OK);

        Mockito
        .when(mockRest.exchange(Matchers.contains("tenants"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyTenantList>>any()))
        .thenReturn(response1);

        Mockito.when(mockRest.exchange(Matchers.contains("deployments"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<CloudifyDeploymentList>>any()))
                .thenReturn(response2);        
        
        List<String> actual = subject.getDeploymentNamesWithFilter(mockedRequest);
        assertTrue(actual.size() > 0);
    }
    
    @Test
    public void testGetDeploymentsWithFilter() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        //mockedRequest.addParameter("filters", filterStr);
        //mockedRequest.addParameter("sort", "name");
        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("ECOMPC_DCAE_WRITE");
        userApps.add("dcae");
        
        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);
        
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"dName\": null, \"name\": \"default_tenant\"}, {\"id\": 2, \"dName\": null, \"name\": \"dyh1b1902\"}], \"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList sampleData = null;
        try {
            sampleData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }

        ResponseEntity<CloudifyTenantList> response1 =
            new ResponseEntity<CloudifyTenantList>(sampleData, HttpStatus.OK);

        
        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        ResponseEntity<CloudifyDeploymentList> response2 =
            new ResponseEntity<CloudifyDeploymentList>(cldDeployList, HttpStatus.OK);

        Mockito
        .when(mockRest.exchange(Matchers.contains("tenants"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyTenantList>>any()))
        .thenReturn(response1);

        Mockito.when(mockRest.exchange(Matchers.contains("deployments"), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<CloudifyDeploymentList>>any()))
                .thenReturn(response2);
        
        List<CloudifyDeployment> actuals = subject.getDeploymentsWithFilter(mockedRequest);
        assertTrue(actuals.size() > 0);
    }
    
    @Test
    public void testGetSecret() {
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
        
        CloudifySecret actuals = subject.getSecret("acl_key", "tenant");
        assertTrue(actuals.getKey().equals("acl_key"));
    }
    
    @Test
    public void testDeleteBlueprint() {
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyBlueprint>>any())).thenReturn(null);
        subject.deleteBlueprint("bp", "tenant");
    }
    
    @Test
    public void testGetPlugins() {
        CloudifyPlugin sampleData = 
            new CloudifyPlugin("plugin1", "202001", "linux", "linux_k8s_plugin", "20200801");

        List<CloudifyPlugin> cfyPlugins = new ArrayList<CloudifyPlugin>();
        cfyPlugins.add(sampleData);

        CloudifyPluginList.Metadata.Pagination pageObj =
            new CloudifyPluginList.Metadata.Pagination(1, 0, 1);
        CloudifyPluginList.Metadata metadata = new CloudifyPluginList.Metadata(pageObj);

        CloudifyPluginList cfyPluginList = new CloudifyPluginList(cfyPlugins, metadata);
        ResponseEntity<CloudifyPluginList> response =
            new ResponseEntity<CloudifyPluginList>(cfyPluginList, HttpStatus.OK);
        
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<CloudifyPluginList>>any()))
                .thenReturn(response);
        
        CloudifyPluginList actuals = subject.getPlugins();
        assertTrue(actuals.items.get(0).package_name.equals("plugin1"));
    }
    
    @Test
    public void testGetDeploymentInputs() {
        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        ResponseEntity<CloudifyDeploymentList> response =
            new ResponseEntity<CloudifyDeploymentList>(cldDeployList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<CloudifyDeploymentList>>any()))
                .thenReturn(response);
        
        CloudifyDeploymentList actualResult = subject.getDeploymentInputs("id", "tenant");
        assertTrue(actualResult.items.get(0).id.equals("id"));
    }
    

}

