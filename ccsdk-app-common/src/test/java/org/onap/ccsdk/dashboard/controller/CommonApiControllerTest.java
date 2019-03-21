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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.domain.EcdComponent;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstance;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceId;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.CloudifyTenant;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentInput;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponseLinks;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeUploadRequest;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class CommonApiControllerTest extends MockitoTestSuite {

    @Mock
    private CloudifyClient cfyClient;

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    DeploymentHandlerClient deploymentHandlerClient;

    @Mock
    ControllerEndpointService controllerEndpointService;

    @InjectMocks
    CommonApiController subject = new CommonApiController();

    private HttpStatusCodeException httpException =
        new HttpServerErrorException(HttpStatus.BAD_GATEWAY);

    protected final ObjectMapper objectMapper = new ObjectMapper();

    HttpServletRequest mockedRequest;
    HttpServletResponse mockedResponse;

    MockUser mockUser = new MockUser();
    ServiceList deplList = null;
    Service deplItem = null;

    ServiceType bpItem = null;
    ServiceType bpItem2 = null;
    ServiceTypeList bpList = null;
    ServiceTypeList bpList2 = null;

    ServiceTypeRequest bpUploadItem = null;

    BadRequestException badReqError;
    ServiceAlreadyExistsException srvcExistError;
    ServerErrorException serverError;
    DownstreamException downStrmError;
    JsonProcessingException jsonError;
    DeploymentNotFoundException notFoundError;
    private ServiceTypeNotFoundException serviceTypeException =
        new ServiceTypeNotFoundException("Invalid blueprint");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        getExpectedDeployments();
        getExpectedBueprints();
        createBpUploadItem();
        mockedRequest = getMockedRequest();
        mockedResponse = getMockedResponse();
        badReqError = new BadRequestException("bad request");
        srvcExistError = new ServiceAlreadyExistsException("service already exists");
        serverError = new ServerErrorException("Error occured in server");
        downStrmError = new DownstreamException("error occured in downstream");
        notFoundError = new DeploymentNotFoundException("item not found");
    }

    public void getExpectedDeployments()
        throws JsonParseException, JsonMappingException, IOException {

        deplItem = new Service("dcae_dtiapi_1902", null, "1552335532348", "1552335532348", null,
            "dummyVnfId", null, "dummyVnfType", "dummyLocation", "dcae_dtiapi_1902", null);
        Collection<Service> items = new ArrayList<Service>();
        items.add(deplItem);

        String pageLinks =
            "{\"previousLink\":null,\"nextLink\":{\"rel\":\"next\",\"href\":\"https://invt.com:30123/dcae-services/?offset=25\"}}";
        ServiceList.PaginationLinks paginationLinks =
            objectMapper.readValue(pageLinks, ServiceList.PaginationLinks.class);
        int totalCount = 1;
        deplList = new ServiceList(items, totalCount, paginationLinks);

    }

    public void getExpectedBueprints()
        throws JsonParseException, JsonMappingException, IOException {
        bpItem = new ServiceType.Builder("xyz1731", "xyz1731-helm-1906", 1906,
            "tosca_definitions_version: cloudify_dsl_1_3", "", "app1", "comp1").build();

        Collection<ServiceType> items = new ArrayList<ServiceType>();
        items.add(bpItem);

        String pageLinks2 =
            "{\"previousLink\":null,\"nextLink\":{\"rel\":\"next\",\"href\":\"https://invt.com:30123/dcae-services/?offset=25\"}}";
        ServiceTypeList.PaginationLinks paginationLinks =
            objectMapper.readValue(pageLinks2, ServiceTypeList.PaginationLinks.class);
        int totalCount = 1;
        bpList = new ServiceTypeList(items, totalCount, paginationLinks);

        bpItem2 = new ServiceType("xyz1731", "xyz1731-helm-1907", 1906,
            "tosca_definitions_version: cloudify_dsl_1_3\\r\\nimports:\\r\\n  - http://www.getcloudify.org/spec/cloudify/4.2/types.yaml\\r\\n",
            "app1", "comp1", null, null, null, null, null, null, "typeId", null, "created", null,
            true);

        Collection<ServiceType> items2 = new ArrayList<ServiceType>();
        items2.add(bpItem2);

        bpList2 = new ServiceTypeList(items2, totalCount, paginationLinks);
    }

    public void createBpUploadItem() {
        bpUploadItem = ServiceTypeRequest.from(bpItem);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testInsertComponent() throws Exception {
        EcdComponent component1 = new EcdComponent();

        component1.setCname("comp1");
        component1.setCompId(100L);
        component1.setDname("COMP1");

        doNothing().when(controllerEndpointService).insertComponent(component1);

        String actualResult = subject.insertComponent(mockedRequest, component1);
        assertTrue(actualResult.contains("Inserted"));
    }

    @Test
    public final void testGetComponents() throws Exception {
        EcdComponent component1 = new EcdComponent();
        EcdComponent component2 = new EcdComponent();

        component1.setCname("comp1");
        component1.setCompId(100L);
        component1.setDname("COMP1");

        component2.setCname("comp2");
        component2.setCompId(200L);
        component2.setDname("COMP2");

        List<EcdComponent> compsList = new ArrayList<EcdComponent>();
        compsList.add(component1);
        compsList.add(component2);

        when(controllerEndpointService.getComponents()).thenReturn(compsList);

        String actualResult = subject.getComponents(mockedRequest);
        assertTrue(actualResult.contains("comp1"));
    }

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
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();

        Mockito.when(cfyClient.getTenants()).thenReturn(sampleData);

        String tenantStr = subject.getTenants(mockedRequest);
        assertTrue(tenantStr.contains("dyh1b"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetTenantStatusForService() throws Exception {
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

        when(cfyClient.getTenants()).thenReturn(cloudifyTenantList);

        when(cfyClient.getTenantInfoFromDeploy(Mockito.any())).thenReturn(cldDeployedTenantList);

        when(cfyClient.getExecutionsSummary(Mockito.any(), Mockito.any()))
            .thenReturn(cloudifyExecutionList).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult = subject.getTenantStatusForService(mockedRequest, deplIds);
        assertTrue(actualResult.contains("successful"));

        actualResult = subject.getTenantStatusForService(mockedRequest, deplIds);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getTenantStatusForService(mockedRequest, deplIds);
        assertTrue(actualResult.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testCreateBlueprint() throws Exception {
        ServiceTypeUploadRequest bpUploadReq = ServiceTypeUploadRequest.from(bpItem);

        when(inventoryClient.addServiceType(Matchers.<ServiceTypeRequest>any()))
            .thenThrow(BlueprintParseException.class).thenThrow(httpException)
            .thenThrow(Exception.class).thenReturn(bpItem);

        String actual1 = subject.createBlueprint(mockedRequest, bpUploadReq);
        assertTrue(actual1.contains("error"));

        String actual2 = subject.createBlueprint(mockedRequest, bpUploadReq);
        assertTrue(actual2.contains("error"));

        String actual3 = subject.createBlueprint(mockedRequest, bpUploadReq);
        assertTrue(actual3.contains("error"));

        String actual = subject.createBlueprint(mockedRequest, bpUploadReq);
        assertTrue(actual.contains("app1"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetBlueprintsByPage() {
        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("_include", "typeName,typeId,typeVersion");
        mockedRequest1.addParameter("name", "xyz");

        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream1).thenThrow(Exception.class).thenThrow(httpException);

        String result = subject.getBlueprintsByPage(mockedRequest1);
        assertTrue(result.contains("xyz"));

        result = subject.getBlueprintsByPage(mockedRequest1);
        assertTrue(result.contains("error"));

        result = subject.getBlueprintsByPage(mockedRequest1);
        assertTrue(result.contains("error"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testQueryBlueprintFilter() {
        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("_include", "typeName,typeId,typeVersion");
        mockedRequest1.addParameter("name", "xyz");

        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream1).thenThrow(Exception.class).thenThrow(httpException);

        String result = subject.queryBlueprintFilter(mockedRequest1);
        assertTrue(result.contains("xyz"));

        result = subject.queryBlueprintFilter(mockedRequest1);
        assertTrue(result.contains("error"));

        result = subject.queryBlueprintFilter(mockedRequest1);
        assertTrue(result.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetDeploymentsByPage() {
        CloudifyDeployedTenant cldDeplTenant =
            new CloudifyDeployedTenant("dcae_dtiapi_1902", "dcae_dtiapi_1902", "tenant1");

        List<CloudifyDeployedTenant> cldDeplTenantList = new ArrayList<CloudifyDeployedTenant>();

        cldDeplTenantList.add(cldDeplTenant);

        CloudifyDeployedTenantList cldDeployedTenantList =
            new CloudifyDeployedTenantList(cldDeplTenantList, null);

        CloudifyTenant cldTenant = new CloudifyTenant("tenant1", "tenant1", "tenant_id1");

        List<CloudifyTenant> cldfyTenantList = new ArrayList<CloudifyTenant>();
        cldfyTenantList.add(cldTenant);

        CloudifyTenantList cloudifyTenantList = new CloudifyTenantList(cldfyTenantList, null);

        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("_include", "id");
        mockedRequest1.addParameter("searchBy", "dti");

        Collection<Service> items = deplList.items;

        when(cfyClient.getTenants()).thenReturn(cloudifyTenantList);

        when(cfyClient.getTenantInfoFromDeploy(Mockito.any())).thenReturn(cldDeployedTenantList)
            .thenThrow(Exception.class).thenThrow(httpException).thenReturn(cldDeployedTenantList)
            .thenReturn(cldDeployedTenantList);

        Stream<Service> sampleStream1 = items.stream();
        Stream<Service> sampleStream2 = items.stream();
        Stream<Service> sampleStream3 = items.stream();

        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream1)
            .thenReturn(sampleStream2).thenReturn(sampleStream3).thenThrow(Exception.class)
            .thenThrow(httpException);

        String result = subject.getDeploymentsByPage("dcae_dtiapi_1902", mockedRequest1);
        assertTrue(result.contains("dti"));

        result = subject.getDeploymentsByPage("dcae_dtiapi_1902", mockedRequest1);
        assertTrue(result.contains("dti"));

        result = subject.getDeploymentsByPage("dcae_dtiapi_1902", mockedRequest1);
        assertTrue(result.contains("dti"));

        result = subject.getDeploymentsByPage("dcae_dtiapi_1902", mockedRequest1);
        assertTrue(result.contains("error"));

        result = subject.getDeploymentsByPage("dcae_dtiapi_1902", mockedRequest1);
        assertTrue(result.contains("error"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetAllDeploymentsByPage() {
        CloudifyDeployedTenant cldDeplTenant =
            new CloudifyDeployedTenant("dcae_dtiapi_1902", "dcae_dtiapi_1902", "tenant1");

        List<CloudifyDeployedTenant> cldDeplTenantList = new ArrayList<CloudifyDeployedTenant>();

        cldDeplTenantList.add(cldDeplTenant);

        CloudifyDeployedTenantList cldDeployedTenantList =
            new CloudifyDeployedTenantList(cldDeplTenantList, null);

        CloudifyTenant cldTenant = new CloudifyTenant("tenant1", "tenant1", "tenant_id1");

        List<CloudifyTenant> cldfyTenantList = new ArrayList<CloudifyTenant>();
        cldfyTenantList.add(cldTenant);

        CloudifyTenantList cloudifyTenantList = new CloudifyTenantList(cldfyTenantList, null);

        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("_include", "id");
        mockedRequest1.addParameter("searchBy", "dti");

        Collection<Service> items = deplList.items;

        when(cfyClient.getTenants()).thenReturn(cloudifyTenantList);

        when(cfyClient.getTenantInfoFromDeploy(Mockito.any())).thenReturn(cldDeployedTenantList)
            .thenThrow(Exception.class).thenThrow(httpException).thenReturn(cldDeployedTenantList)
            .thenReturn(cldDeployedTenantList);

        Stream<Service> sampleStream1 = items.stream();
        Stream<Service> sampleStream2 = items.stream();
        Stream<Service> sampleStream3 = items.stream();

        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream1)
            .thenReturn(sampleStream2).thenReturn(sampleStream3).thenThrow(Exception.class)
            .thenThrow(httpException);

        String result = subject.getAllDeploymentsByPage(mockedRequest1);
        assertTrue(result.contains("dti"));

        result = subject.getAllDeploymentsByPage(mockedRequest1);
        assertTrue(result.contains("dti"));

        result = subject.getAllDeploymentsByPage(mockedRequest1);
        assertTrue(result.contains("dti"));

        result = subject.getAllDeploymentsByPage(mockedRequest1);
        assertTrue(result.contains("error"));

        result = subject.getAllDeploymentsByPage(mockedRequest1);
        assertTrue(result.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetDeploymentRevisions() throws Exception {
        CloudifyNodeInstance cfyNodeInstance = new CloudifyNodeInstance("id1", null);

        List<CloudifyNodeInstance> cfyNodeInstanceItems = new ArrayList<CloudifyNodeInstance>();
        cfyNodeInstanceItems.add(cfyNodeInstance);

        CloudifyNodeInstanceList cfyNodeInstList =
            new CloudifyNodeInstanceList(cfyNodeInstanceItems, null);

        when(cfyClient.getNodeInstanceVersion(Mockito.any(), Mockito.any()))
            .thenReturn(cfyNodeInstList).thenThrow(Exception.class).thenThrow(httpException);

        String actualResult =
            subject.getDeploymentRevisions("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("id1"));

        actualResult = subject.getDeploymentRevisions("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.getDeploymentRevisions("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("error"));
    }

    @Test
    public final void testGetDeploymentInputs() throws Exception {

        Map<String, Object> inputHash = new HashMap<String, Object>();
        inputHash.put("key1", "value1");

        CloudifyDeployment cldDeployment =
            new CloudifyDeployment("description", "blueprint_id", "created_at", "updated_at", "id",
                inputHash, null, null, null, null, null, null, "tenant");

        List<CloudifyDeployment> cfyDeployItems = new ArrayList<CloudifyDeployment>();
        cfyDeployItems.add(cldDeployment);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);
        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeployList = new CloudifyDeploymentList(cfyDeployItems, metadata);

        when(cfyClient.getDeploymentInputs(Matchers.any(), Matchers.any()))
            .thenReturn(cldDeployList);

        String actualResult = subject.getDeploymentInputs("deploymentId", "tenant", mockedRequest);
        assertTrue(actualResult.contains("blueprint_id"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testModifyDeployment() throws Exception {
        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "install",
            false, "bp1", "id1", "tenant1", "error", "execution_id1", null, false, false);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tenant", "tenant1");
        params.put("workflow", "upgrade");

        CloudifyExecutionRequest cfyExecReq = new CloudifyExecutionRequest("deployment_id",
            "upgrade", false, false, "tenant1", params);

        CloudifyNodeInstanceId cfyNodeInst = new CloudifyNodeInstanceId("node_instance_id1");

        List<CloudifyNodeInstanceId> cfyNodeInstItems = new ArrayList<CloudifyNodeInstanceId>();

        cfyNodeInstItems.add(cfyNodeInst);

        CloudifyNodeInstanceIdList cfyNodeInstList =
            new CloudifyNodeInstanceIdList(cfyNodeInstItems, null);

        when(cfyClient.getNodeInstanceId(Mockito.any(), Mockito.any())).thenReturn(cfyNodeInstList);

        when(cfyClient.startExecution(Matchers.<CloudifyExecutionRequest>any()))
            .thenReturn(cfyExecObj).thenThrow(Exception.class).thenThrow(httpException);

        String inputParamStr = "{\"tenant\": \"tenant1\", \"workflow\":\"upgrade\"}";

        InputStream is = new ByteArrayInputStream(inputParamStr.getBytes());

        String actualResult = subject.modifyDeployment("depId", mockedRequest, is);
        assertTrue(actualResult.contains("execution_id1"));

        actualResult = subject.modifyDeployment("depId", mockedRequest, is);
        assertTrue(actualResult.contains("error"));

        actualResult = subject.modifyDeployment("depId", mockedRequest, is);
        assertTrue(actualResult.contains("error"));

    }

    @Test
    public final void testGetServicesForType() throws Exception {
        ServiceRef expectedSrvc = new ServiceRef("dcae_dtiapi_1902", "432423", "433434");
        Collection<ServiceRef> expectedSrvcIds = new ArrayList<ServiceRef>();
        expectedSrvcIds.add(expectedSrvc);
        ServiceRefList expectedSrvcRefList = new ServiceRefList(expectedSrvcIds, 1);

        when(inventoryClient.getServicesForType(Matchers.<ServiceQueryParams>any()))
            .thenReturn(expectedSrvcRefList);
        String actual = subject.getServicesForType(mockedRequest, "typeId1");
        assertTrue(actual.contains("typeId1"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testCreateDeployment() throws Exception {
        DeploymentInput deployInput1 = new DeploymentInput("component1", "tag1",
            "xyz1731-helm-1906", 1906, "blueprintId", null, "tenant1");

        DeploymentInput deployInput2 = new DeploymentInput("component1", "tag1",
            "xyz1731-helm-1906", 1906, null, null, "tenant1");

        DeploymentResponseLinks expectLink = new DeploymentResponseLinks("self", "status");
        DeploymentResponse expectResp = new DeploymentResponse("req1", expectLink);

        Collection<ServiceType> items = bpList2.items;
        Stream<ServiceType> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream1);

        String actualResp0 = subject.createDeployment(mockedRequest, deployInput2);
        assertTrue(actualResp0.contains("error"));

        StringBuffer expectedStrBuff = new StringBuffer();
        expectedStrBuff.append("http://oom.s2.com");
        when(mockedRequest.getRequestURL()).thenReturn(expectedStrBuff);

        when(deploymentHandlerClient.putDeployment(Matchers.anyString(), Matchers.anyString(),
            Matchers.<DeploymentRequest>any())).thenReturn(expectResp).thenThrow(badReqError)
                .thenThrow(srvcExistError).thenThrow(serverError).thenThrow(downStrmError)
                .thenThrow(Exception.class);

        String actualResp = subject.createDeployment(mockedRequest, deployInput1);
        assertTrue(actualResp.contains("component1"));

        actualResp = subject.createDeployment(mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateDeployment() throws Exception {

        DeploymentInput deployInput1 = new DeploymentInput("component1", "tag1",
            "xyz1731-helm-1906", 1906, "blueprintId", null, "tenant1");

        DeploymentResponseLinks expectLink = new DeploymentResponseLinks("self", "status");
        DeploymentResponse expectResp = new DeploymentResponse("req1", expectLink);

        Collection<ServiceType> items = bpList2.items;
        Stream<ServiceType> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream1);

        when(deploymentHandlerClient.updateDeployment(Matchers.anyString(), Matchers.anyString(),
            Matchers.<DeploymentRequest>any())).thenReturn(expectResp).thenThrow(badReqError)
                .thenThrow(srvcExistError).thenThrow(serverError).thenThrow(downStrmError)
                .thenThrow(Exception.class);

        String actualResp = subject.updateDeployment("id1", mockedRequest, deployInput1);
        assertTrue(actualResp.contains("req1"));

        actualResp = subject.updateDeployment("id1", mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.updateDeployment("id1", mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.updateDeployment("id1", mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.updateDeployment("id1", mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.updateDeployment("id1", mockedRequest, deployInput1);
        assertTrue(actualResp.contains("error"));
    }

    @Test
    public final void testGetExecutionByDeploymentId() throws Exception {

        CloudifyExecution cldExecution =
            new CloudifyExecution("successful", "created_at", "install", false, "bp1", "id1",
                "tenant1", "error", "execution_id1", null, false, false);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        when(cfyClient.getExecutionsSummary(Mockito.any(), Mockito.any()))
            .thenReturn(cloudifyExecutionList);

        String actualResult =
            subject.getExecutionByDeploymentId("dep_id", "tenant1", mockedRequest);
        assertTrue(actualResult.contains("execution_id1"));
    }

    @Test
    public final void testDeleteBlueprint() throws Exception {
        String expected = "{\"202\": \"OK\"}";
        doNothing().doThrow(serviceTypeException).doThrow(Exception.class).when(inventoryClient)
            .deleteServiceType(Matchers.anyString());

        String actual = subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse);
        assertEquals(expected, actual);

        actual = subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testDeleteDeployment() throws Exception {
        doNothing().doThrow(badReqError).doThrow(serverError).doThrow(downStrmError)
            .doThrow(notFoundError).doThrow(Exception.class).when(deploymentHandlerClient)
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testCancelExecution() throws Exception {
        List<String> tenants = new ArrayList<String>();
        tenants.add("tenant1");

        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.put("tenant", tenants);

        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "cancel",
            false, "bp1", "id1", "tenant1", "error", "execution_id1", null, false, false);

        when(cfyClient.cancelExecution(Mockito.any(), Mockito.any(), Mockito.any()))
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
