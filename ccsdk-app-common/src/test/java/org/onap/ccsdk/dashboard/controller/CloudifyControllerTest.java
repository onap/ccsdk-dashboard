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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprint;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentExt;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentHelm;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEvent;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstance;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceId;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPlugin;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPluginList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenantList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.util.CacheManager;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@RunWith(PowerMockRunner.class)
public class CloudifyControllerTest extends MockitoTestSuite {

    @Mock
    private CloudifyClient restClient;
    
    @Mock
    private InventoryClient invClient;

    @InjectMocks
    private CloudifyController subject = new CloudifyController();

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    UserUtils userUtils = new UserUtils();

    @Mock
    User epuser;

    MockUser mockUser = new MockUser();

    HttpClientErrorException httpException;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        httpException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "statusText");
        CacheManager testCache = new CacheManager();
        subject.setCacheManager(testCache); 
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetTenants() throws Exception {
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"name\": \"default_tenant\", \"dName\": \"default_tenant\" }, "
                + "{\"id\": 2, \"name\": \"dyh1b1902\", \"dName\": \"dyh1b1902\"}], "
                + "\"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList sampleData = null;
        try {
            sampleData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }

        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();

        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        Mockito.when(restClient.getTenants()).thenReturn(sampleData).thenThrow(Exception.class);
        /*
         * RequestBuilder request = MockMvcRequestBuilders. get("/tenants").
         * accept(MediaType.APPLICATION_JSON);
         */
        String tenantStr = subject.getTenants(mockedRequest);
        assertTrue(tenantStr.contains("dyh1b"));

        tenantStr = subject.getTenants(mockedRequest);
        assertTrue(tenantStr.contains("error"));
    }

    @Test
    public final void testGetAllServiceNames() {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        //mockedRequest.addParameter("searchBy", "xyz");
        mockedRequest.addParameter("sortBy", "owner");
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        
        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);
        
        Mockito.when(restClient.getDeployments(
            Mockito.any(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean()))
            .thenReturn(items);
        
        String actual = subject.getAllServiceNames(mockedRequest);
        assertTrue(actual.contains("id1"));
    }
    @Test
    public final void testGetDeploymentsByPage() {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("searchBy", "tenant:default_tenant;cache:false;serviceRef:id1");
        mockedRequest.addParameter("sortBy", "owner");
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        
        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);
 
        Mockito.when(restClient.getDeployments(
            Mockito.any(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyBoolean()))
            .thenReturn(items);
        List<CloudifyDeploymentExt> cfyDepExList = 
            new ArrayList<CloudifyDeploymentExt>();
        List<CloudifyDeploymentHelm> cfyDeplHelmList = 
            new ArrayList<CloudifyDeploymentHelm>();
        
        Mockito.when(restClient.updateWorkflowStatus(Mockito.any())).
        thenReturn(cfyDepExList);
        Mockito.when(restClient.updateHelmInfo(Mockito.any())).
        thenReturn(cfyDeplHelmList);
        
        String actual = subject.getDeploymentsByPage(mockedRequest);
        assertTrue(actual.contains("id1"));
    }
    
    @Test
    public final void testViewBlueprintContentById() throws Exception {
        byte[] result = "sample blueprint yaml content".getBytes();
        
        when(restClient.viewBlueprint(Mockito.any(), Mockito.any())).thenReturn(result);
        
        byte[] actual = subject.viewBlueprintContentById("id", "tenant", mockedRequest);
        assertArrayEquals(result, actual);
    }
    
    @Test
    public final void testGetDepCount() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        //mockedRequest.addParameter("searchBy", "xyz");
        mockedRequest.addParameter("sortBy", "owner");
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        
        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);
        
        Mockito.when(restClient.getDeploymentsWithFilter(Mockito.any()))
            .thenReturn(items);
        
        String actual = subject.getDepCount(mockedRequest);
        assertTrue(actual.contains("items"));
    }
    
    @Test
    public final void testGetPluginCount() throws JsonProcessingException {
        CloudifyPlugin sampleData = 
            new CloudifyPlugin("plugin1", "202001", "linux", "linux_k8s_plugin", "20200801");

        List<CloudifyPlugin> cfyPlugins = new ArrayList<CloudifyPlugin>();
        cfyPlugins.add(sampleData);

        CloudifyPluginList.Metadata.Pagination pageObj =
            new CloudifyPluginList.Metadata.Pagination(1, 0, 1);
        CloudifyPluginList.Metadata metadata = new CloudifyPluginList.Metadata(pageObj);

        CloudifyPluginList cfyPluginList = new CloudifyPluginList(cfyPlugins, metadata);
    
        when(restClient.getPlugins()).thenReturn(cfyPluginList);
        String actual = subject.getPluginCount(mockedRequest);
        assertTrue(actual.contains("1"));
    }
    
    @Test
    public final void testDeleteBlueprint() throws Exception {
        String json = "{\"202\": \"OK\"}";       
             
        doNothing().doThrow(httpException).doThrow(Exception.class).when(restClient).deleteBlueprint(Mockito.any(), Mockito.any());
    
        String actual = subject.deleteBlueprint("id", "tenant", mockedRequest, mockedResponse);
        assertTrue(actual.equals(json));
        
        actual = subject.deleteBlueprint("id", "tenant", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
        
        actual = subject.deleteBlueprint("id", "tenant", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
    }
    
    @Test
    public final void testGetDeploymentsForType() throws Exception {              
        String[] testTypeIds = {"44234234"};
        ServiceRef expectedSrvc = new ServiceRef("dcae_dtiapi_1902", "432423", "433434");
        Collection<ServiceRef> expectedSrvcIds = new ArrayList<ServiceRef>();
        expectedSrvcIds.add(expectedSrvc);
        ServiceRefList expectedSrvcRefList = new ServiceRefList(expectedSrvcIds, 1);

        CloudifyDeployedTenant cfyDepTen = 
            new CloudifyDeployedTenant("id", "tenant_name", "created_at", "updated_at");
        
        List<CloudifyDeployedTenant> cfyDeplTenList = new ArrayList<>();
        cfyDeplTenList.add(cfyDepTen);
        
        when(restClient.getDeploymentForBlueprint(Mockito.any())).thenReturn(cfyDeplTenList);
        
        when(invClient.getServicesForType(Matchers.<ServiceQueryParams>any()))
            .thenReturn(expectedSrvcRefList);
        String actual = subject.getDeploymentsForType(mockedRequest, testTypeIds);
        assertTrue(actual.contains(testTypeIds[0]));
    }
    
    @Test
    @Ignore
    public final void testCacheOwnerDeployMap() {
        
    }
    
    @Test
    public final void testGetExecutionsPerTenant() throws Exception {
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"name\": \"default_tenant\", \"dName\": \"default_tenant\" }, "
                + "{\"id\": 2, \"name\": \"dyh1b1902\", \"dName\": \"dyh1b1902\"}], "
                + "\"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList tenantData = null;
        try {
            tenantData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(restClient.getTenants()).thenReturn(tenantData);
        
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);
        
        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);
        
        when(restClient.getExecutionsSummaryPerTenant(Mockito.any()))
            .thenReturn(cloudifyExecutionList).thenReturn(cloudifyExecutionList)
            .thenReturn(cloudifyExecutionList);
        
        String actual = subject.getExecutionsPerTenant(mockedRequest, "default_tenant", "failed");
        assertFalse(actual.contains("execution_id1"));
        actual = subject.getExecutionsPerTenant(mockedRequest, "default_tenant", "successful");
        assertTrue(actual.contains("execution_id1"));
        actual = subject.getExecutionsPerTenant(mockedRequest, null, "successful");
        assertTrue(actual.contains("execution_id1"));
    }
    
    @Test
    public final void testGetExecutionsPerTenant_error() throws Exception {
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"name\": \"default_tenant\", \"dName\": \"default_tenant\" }, "
                + "{\"id\": 2, \"name\": \"dyh1b1902\", \"dName\": \"dyh1b1902\"}], "
                + "\"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList tenantData = null;
        try {
            tenantData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(restClient.getTenants()).thenReturn(tenantData);
        
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);
        
        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);
        
        when(restClient.getExecutionsSummaryPerTenant(Mockito.any()))
            .thenThrow(Exception.class).thenThrow(Exception.class).thenThrow(Exception.class);
        
        String actual = subject.getExecutionsPerTenant(mockedRequest, "default_tenant", "failed");
        assertTrue(actual.contains("error"));
        
        actual = subject.getExecutionsPerTenant(mockedRequest, "default_tenant", "successful");
        assertTrue(actual.contains("error"));
        
        actual = subject.getExecutionsPerTenant(mockedRequest, null, "failed");
        assertTrue(actual.contains("error"));
    }
    
    @Test
    public final void testQueryExecution() throws Exception {      
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);      
        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();
        cldExecutionList.add(cldExecution);
        
        when(restClient.getExecutionIdSummary(
            Mockito.any(), Mockito.any())).thenReturn(cldExecution);
        
        String actual = subject.queryExecution("id", "tenant", mockedRequest);
        assertTrue(actual.contains("items"));
    }
     
    @SuppressWarnings("unchecked")
    @Test
    public final void testGetActiveExecutions() throws Exception {
        String tenantsList =
            "{\"items\": [{\"id\": 1, \"name\": \"default_tenant\", \"dName\": \"default_tenant\" }, "
                + "{\"id\": 2, \"name\": \"dyh1b1902\", \"dName\": \"dyh1b1902\"}], "
                + "\"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
        CloudifyTenantList tenantData = null;
        try {
            tenantData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
        } catch (Exception e) {
        }
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(restClient.getTenants()).thenReturn(tenantData).thenThrow(Exception.class);
        
        CloudifyExecution cldExecution =
            new CloudifyExecution("started", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);
        
        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);
        
        when(restClient.getExecutionsSummaryPerTenant(Mockito.any()))
            .thenReturn(cloudifyExecutionList).thenReturn(cloudifyExecutionList);
        
        String actual = subject.getActiveExecutions(mockedRequest);
        assertTrue(actual.contains("execution_id1"));
        actual = subject.getActiveExecutions(mockedRequest);
        assertTrue(actual.contains("error"));
        
    }
    
    @Test
    public final void testGetNodeInstanceDetails() throws Exception {
        
        Map<String, Object> runtime_properties = 
            new HashMap<>();
        runtime_properties.put("key1", "value1");
        
        CloudifyNodeInstance cfyNodeInst = 
            new CloudifyNodeInstance("node_instance_id1", runtime_properties);

        List<CloudifyNodeInstance> cfyNodeInstItems = new ArrayList<CloudifyNodeInstance>();

        cfyNodeInstItems.add(cfyNodeInst);

        CloudifyNodeInstanceList cfyNodeInstList =
            new CloudifyNodeInstanceList(cfyNodeInstItems, null);
        
        when(restClient.getNodeInstanceDetails(Mockito.any(), Mockito.any())).
        thenReturn(cfyNodeInstList).thenThrow(httpException).
        thenThrow(Exception.class);
        
        String actual = 
            subject.getNodeInstanceDetails("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("node_instance_id1"));
        
        actual = 
            subject.getNodeInstanceDetails("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("error"));
        
        actual = 
            subject.getNodeInstanceDetails("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("error"));  
    }
    
    @Test
    public final void testGetNodeInstances() throws Exception {
        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");

        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();

        cfyNodeInstItems.add(cfyNodeInst);

        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);
        
        when(restClient.getNodeInstances(Mockito.any(), Mockito.any())).
        thenReturn(cfyNodeInstList).thenThrow(httpException).
        thenThrow(Exception.class);
        
        String actual = 
            subject.getNodeInstances("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("node_instance_id1"));
        
        actual = 
            subject.getNodeInstances("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("error"));
        
        actual = 
            subject.getNodeInstances("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("error"));
    }
    
    @Test
    public final void testGetPlugins() throws Exception {
        CloudifyPlugin sampleData = 
            new CloudifyPlugin("plugin1", "202001", "linux", "linux_k8s_plugin", "20200801");

        List<CloudifyPlugin> cfyPlugins = new ArrayList<CloudifyPlugin>();
        cfyPlugins.add(sampleData);

        CloudifyPluginList.Metadata.Pagination pageObj =
            new CloudifyPluginList.Metadata.Pagination(1, 0, 1);
        CloudifyPluginList.Metadata metadata = new CloudifyPluginList.Metadata(pageObj);

        CloudifyPluginList cfyPluginList = new CloudifyPluginList(cfyPlugins, metadata);
    
        when(restClient.getPlugins()).thenReturn(cfyPluginList).thenThrow(Exception.class);
        String actual = subject.getPlugins(mockedRequest);
        assertTrue(actual.contains("plugin1"));
        
        actual = subject.getPlugins(mockedRequest);
        assertTrue(actual.contains("error"));
    }
    
    @Test
    @Ignore
    public final void testGetSecrets() {
        
    }
    
    @Test
    @Ignore
    public final void testGetSecret() {
        
    }
    
    @Test
    @Ignore
    public final void testDeleteSecret() {
        
    }
    
    @Test
    @Ignore
    public final void testCreateSecret() {
    }
    
    @SuppressWarnings({"unchecked", "unchecked"})
    @Test
    public final void testGetBlueprintById() throws Exception {

        CloudifyBlueprint cldBp =
            new CloudifyBlueprint("file1", "description1", "343242", "3423423", "id1", null);

        List<CloudifyBlueprint> items = new ArrayList<CloudifyBlueprint>();
        items.add(cldBp);

        CloudifyBlueprintList.Metadata.Pagination pageObj =
            new CloudifyBlueprintList.Metadata.Pagination(1, 0, 1);

        CloudifyBlueprintList.Metadata metadata = new CloudifyBlueprintList.Metadata(pageObj);

        CloudifyBlueprintList cldBpList = new CloudifyBlueprintList(items, metadata);

        when(restClient.getBlueprint(Mockito.any(), Mockito.any())).thenReturn(cldBpList)
            .thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.getBlueprintById("id1", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("id1"));

        actualResult = subject.getBlueprintById("id1", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getBlueprintById("id1", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetDeploymentById() throws Exception {

        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);

        when(restClient.getDeployment(Mockito.any(), Mockito.any())).thenReturn(cldDeplList)
            .thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.getDeploymentById("id1", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("id1"));

        actualResult = subject.getDeploymentById("id1", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getDeploymentById("id1", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("error"));

        when(restClient.getDeployment(Mockito.any())).thenReturn(cldDeplList)
            .thenThrow(Exception.class).thenThrow(httpException);

        actualResult = subject.getDeploymentById("id1", "", mockedRequest);
        assertTrue(actualResult.contains("id1"));

        actualResult = subject.getDeploymentById("id1", null, mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getDeploymentById("id1", null, mockedRequest);
        assertTrue(actualResult.contains("error"));
    }
    
    @Test
    public void testGetExecutionsByPage() throws Exception {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);
        
        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        CloudifyDeployment cldDeployment = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id", null, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);
               
        when(restClient.getDeployments(Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(cldDeployList);
        when(restClient.getExecutions(Mockito.any(), Mockito.any()))
            .thenReturn(cloudifyExecutionList).thenThrow(Exception.class)
            .thenReturn(cloudifyExecutionList);

        String actualResult =
            subject.getExecutionsByPage(mockedRequest, "id1", "successful", "tenant1");
        assertTrue(actualResult.contains("successful"));

        actualResult = subject.getExecutionsByPage(mockedRequest, "id1", "successful", "tenant1");
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getExecutionsByPage(mockedRequest, null, "successful", "tenant1");
        assertTrue(actualResult.contains("successful"));
    }
/*
    @SuppressWarnings("unchecked")
    @Test
    public void testGetExecutionByIdAndDeploymentId() throws Exception {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        when(restClient.getExecutions(Mockito.any(), Mockito.any()))
            .thenReturn(cloudifyExecutionList).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.getExecutionByIdAndDeploymentId("execution_id",
            "deployment_id", "tenant", mockedRequest);
        assertTrue(actualResult.contains("successful"));

        actualResult = subject.getExecutionByIdAndDeploymentId("execution_id", "deployment_id",
            "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getExecutionByIdAndDeploymentId("execution_id", "deployment_id",
            "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));
    }
*/
    @SuppressWarnings("unchecked")
    @Test
    public void testGetExecutionEventsById() throws Exception {

        CloudifyEvent cfyEvent1 = new CloudifyEvent("blueprint_id", "deployment_id", null, "log",
            "execution_id", "debug", "logger", "message", "node_instance_id", "node_name",
            "operation", "reported_timestamp", "timestamp", "cloudify_log", "workflow_id");
        CloudifyEvent cfyEvent2 = new CloudifyEvent("blueprint_id", "deployment_id", null, "log",
            "execution_id", "debug", "logger", "message", "node_instance_id", "node_name",
            "operation", "reported_timestamp", "timestamp", "type2", "workflow_id");

        List<CloudifyEvent> cfyEventItems = new ArrayList<CloudifyEvent>();
        cfyEventItems.add(cfyEvent1);
        cfyEventItems.add(cfyEvent2);

        CloudifyEventList cfyEventsList = new CloudifyEventList(cfyEventItems, null);

        when(restClient.getEventlogs(Mockito.any(), Mockito.any())).thenReturn(cfyEventsList)
            .thenThrow(Exception.class).thenThrow(httpException);

        String actualResult =
            subject.getExecutionEventsById("execution1", "false", "tenant", mockedRequest);
        assertTrue(actualResult.contains("execution_id"));

        actualResult =
            subject.getExecutionEventsById("execution1", "false", "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult =
            subject.getExecutionEventsById("execution1", "false", "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testStartExecution() throws Exception {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key1", "value1");

        CloudifyExecutionRequest cfyExecReq = new CloudifyExecutionRequest("deployment_id",
            "upgrade", false, false, "tenant1", params);

        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");

        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();

        cfyNodeInstItems.add(cfyNodeInst);

        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);
        
        String secretTokenStr =
            "{\"created_at\": \"created_ts\", \"key\": \"acl_key\", \"updated_at\": \"updated_ts\", \"value\": \"acl_token_val\", \"visibility\": \"global\", \"is_hidden_value\": \"false\", \"tenant_name\": \"tenant\", \"resource_availability\": \"rsrc\"}";          
        CloudifySecret secretData = null;
        try {
            secretData = objectMapper.readValue(secretTokenStr, CloudifySecret.class);
        } catch (Exception e) {
            
        }
        when(restClient.getSecret(Mockito.any(), Mockito.any())).thenReturn(secretData);
        
        when(restClient.getNodeInstanceId(Mockito.any(), Mockito.any()))
            .thenReturn(cfyNodeInstList);

        when(restClient.startExecution(Matchers.<CloudifyExecutionRequest>any()))
            .thenReturn(cldExecution).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.startExecution(mockedRequest, cfyExecReq);
        assertTrue(actualResult.contains("execution_id1"));

        actualResult = subject.startExecution(mockedRequest, cfyExecReq);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.startExecution(mockedRequest, cfyExecReq);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetNodeInstanceId() throws Exception {

        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");

        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();

        cfyNodeInstItems.add(cfyNodeInst);

        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);

        when(restClient.getNodeInstanceId(Mockito.any(), Mockito.any(), Mockito.any()))
            .thenReturn(cfyNodeInstList).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult =
            subject.getNodeInstanceId("deploymentId", "tenant", "nodeId", mockedRequest);
        assertTrue(actualResult.contains("node_instance_id1"));

        actualResult = subject.getNodeInstanceId("deploymentId", "tenant", "nodeId", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getNodeInstanceId("deploymentId", "tenant", "nodeId", mockedRequest);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetDeploymentRevisions() throws Exception {

        CloudifyNodeInstance cfyNodeInstance = new CloudifyNodeInstance("id1", null);

        List<CloudifyNodeInstance> cfyNodeInstanceItems = new ArrayList<CloudifyNodeInstance>();
        cfyNodeInstanceItems.add(cfyNodeInstance);

        CloudifyNodeInstanceList cfyNodeInstList =
            new CloudifyNodeInstanceList(cfyNodeInstanceItems, null);

        when(restClient.getNodeInstanceVersion(Mockito.any(), Mockito.any()))
            .thenReturn(cfyNodeInstList).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult =
            subject.getDeploymentRevisions("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("id1"));

        actualResult = subject.getDeploymentRevisions("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getDeploymentRevisions("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCancelExecution() throws Exception {
        List<String> tenants = new ArrayList<String>();
        tenants.add("tenant1");

        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.put("tenant", tenants);

        CloudifyExecution cfyExecObj =
            new CloudifyExecution("successful", "created_at", "ended_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null);

        when(restClient.cancelExecution(Mockito.any(), Mockito.any(), Mockito.any()))
            .thenReturn(cfyExecObj).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult =
            subject.cancelExecution(httpHeader, "id1", null, mockedRequest, mockedResponse);
        assertTrue(actualResult.contains("execution_id1"));

        actualResult =
            subject.cancelExecution(httpHeader, "id1", null, mockedRequest, mockedResponse);
        assertTrue(actualResult.contains("error"));

        actualResult =
            subject.cancelExecution(httpHeader, "id1", null, mockedRequest, mockedResponse);
        assertTrue(actualResult.contains("error"));
    }
}
