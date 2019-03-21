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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprint;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateResponse;
import org.onap.ccsdk.dashboard.model.CloudifyEvent;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstance;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceId;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.CloudifyTenant;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class CloudifyControllerTest extends MockitoTestSuite {

    @Mock
    private CloudifyClient restClient;

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

    @SuppressWarnings("unchecked")
    @Test
    public void testGetTenantStatusForService() throws Exception {

        String[] deplIds = {"id1", "id2"};

        CloudifyDeployedTenant cldDeplTenant = new CloudifyDeployedTenant("id1", "bp1", "tenant1");

        List<CloudifyDeployedTenant> cldDeplTenantList = new ArrayList<CloudifyDeployedTenant>();

        cldDeplTenantList.add(cldDeplTenant);

        CloudifyDeployedTenantList cldDeployedTenantList =
            new CloudifyDeployedTenantList(cldDeplTenantList, null);

        CloudifyTenant cldTenant = new CloudifyTenant("tenant1", "tenant1", "tenant_id1");

        List<CloudifyTenant> cldfyTenantList = new ArrayList<CloudifyTenant>();
        cldfyTenantList.add(cldTenant);

        CloudifyTenantList cloudifyTenantList = new CloudifyTenantList(cldfyTenantList, null);

        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null, false, false);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        Map<String, Object> plan = new HashMap<String, Object>();
        HashMap<String, String> plugins_to_install = new HashMap<String, String>();
        plugins_to_install.put("name", "helm-plugin");
        ArrayList<HashMap<String, String>> deployment_plugins_to_install =
            new ArrayList<HashMap<String, String>>();

        deployment_plugins_to_install.add(plugins_to_install);
        plan.put("deployment_plugins_to_install", deployment_plugins_to_install);

        Map<String, String> workflows = new HashMap<String, String>();
        workflows.put("status", "workflowshelm");
        plan.put("workflows", workflows);

        CloudifyBlueprint cldBp =
            new CloudifyBlueprint("file1", "description1", "343242", "3423423", "id1", plan);

        List<CloudifyBlueprint> items = new ArrayList<CloudifyBlueprint>();
        items.add(cldBp);

        CloudifyBlueprintList.Metadata.Pagination pageObj =
            new CloudifyBlueprintList.Metadata.Pagination(1, 0, 1);

        CloudifyBlueprintList.Metadata metadata = new CloudifyBlueprintList.Metadata(pageObj);

        CloudifyBlueprintList cldBpList = new CloudifyBlueprintList(items, metadata);

        when(restClient.getTenants()).thenReturn(cloudifyTenantList);

        when(restClient.getTenantInfoFromDeploy(Mockito.any())).thenReturn(cldDeployedTenantList);

        when(restClient.getExecutionsSummary(Mockito.any(), Mockito.any()))
            .thenReturn(cloudifyExecutionList);

        when(restClient.getBlueprint(Mockito.any(), Mockito.any())).thenReturn(cldBpList)
            .thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.getTenantStatusForService(mockedRequest, deplIds);
        assertTrue(actualResult.contains("successful"));

        actualResult = subject.getTenantStatusForService(mockedRequest, deplIds);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getTenantStatusForService(mockedRequest, deplIds);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetExecutionsByPage() throws Exception {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null, false, false);

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

        when(restClient.getDeployments()).thenReturn(cldDeployList);
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

    @SuppressWarnings("unchecked")
    @Test
    public void testGetExecutionByIdAndDeploymentId() throws Exception {
        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null, false, false);

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
        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "install",
            false, "bp1", "id1", "tenant1", "error", "execution_id1", null, false, false);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key1", "value1");

        CloudifyExecutionRequest cfyExecReq = new CloudifyExecutionRequest("deployment_id",
            "upgrade", false, false, "tenant1", params);

        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");

        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();

        cfyNodeInstItems.add(cfyNodeInst);

        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);

        when(restClient.getNodeInstanceId(Mockito.any(), Mockito.any()))
            .thenReturn(cfyNodeInstList);

        when(restClient.startExecution(Matchers.<CloudifyExecutionRequest>any()))
            .thenReturn(cfyExecObj).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.startExecution(mockedRequest, cfyExecReq);
        assertTrue(actualResult.contains("execution_id1"));

        actualResult = subject.startExecution(mockedRequest, cfyExecReq);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.startExecution(mockedRequest, cfyExecReq);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateDeployment() throws Exception {

        CloudifyDeploymentUpdateRequest cfyDeployUpdateReq =
            new CloudifyDeploymentUpdateRequest("deployment_id", "update", false, false,
                "node_instance_id1", "4", "1000", "image", 2, "my_container");

        CloudifyDeploymentUpdateResponse cfyDeployUpdateResp = new CloudifyDeploymentUpdateResponse(
            "terminated", "created_at", "update", false, "blueprint_id", "deployment_id", "", "id1",
            null, "tenant1", "junit", false, "resource_availability");

        when(restClient.updateDeployment(Matchers.<CloudifyDeploymentUpdateRequest>any()))
            .thenReturn(cfyDeployUpdateResp).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.updateDeployment(mockedRequest, cfyDeployUpdateReq);
        assertTrue(actualResult.contains("terminated"));

        actualResult = subject.updateDeployment(mockedRequest, cfyDeployUpdateReq);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.updateDeployment(mockedRequest, cfyDeployUpdateReq);
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

        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "cancel",
            false, "bp1", "id1", "tenant1", "error", "execution_id1", null, false, false);

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
