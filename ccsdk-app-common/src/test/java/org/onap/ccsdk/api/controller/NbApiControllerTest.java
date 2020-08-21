/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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
 *******************************************************************************/

package org.onap.ccsdk.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceId;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenantList;
import org.onap.ccsdk.dashboard.model.consul.ConsulDeploymentHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentInput;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponseLinks;
import org.onap.ccsdk.dashboard.model.inventory.Blueprint;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummaryList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeUploadRequest;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.util.CacheManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest({Blueprint.class})
public class NbApiControllerTest extends MockitoTestSuite {

    @Mock
    InventoryClient inventoryClient;

    @Mock
    DeploymentHandlerClient deploymentHandlerClient;

    @Mock
    CloudifyClient cfyClient;

    @Mock
    ConsulClient consulClient;

    @Mock
    ServletUriComponentsBuilder uriBuilder;

    @InjectMocks
    NbApiController subject;

    private HttpStatusCodeException httpException =
        new HttpServerErrorException(HttpStatus.BAD_GATEWAY);

    protected final ObjectMapper objectMapper = new ObjectMapper();

    HttpServletRequest mockedRequest;
    HttpServletResponse mockedResponse;

    MockUser mockUser = new MockUser();
    ServiceList deplList = null;
    Service deplItem = null;

    ServiceTypeSummary bpItem, bpItem2 = null;
    ServiceTypeSummaryList bpList, bpList2 = null;
    ServiceType bpItemFull = null;
    ServiceTypeList bpItemFullList = null;
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
        CacheManager testCache = new CacheManager();
        subject.setCacheManager(testCache);
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        StringBuffer urlBuff = new StringBuffer();
        urlBuff.append("http://orcl.com");
        // Mockito.when(mockedRequest.getRemoteUser()).thenReturn("tester");
        Mockito.when(mockedRequest.getRequestURL()).thenReturn(urlBuff);
        PowerMockito.mockStatic(Blueprint.class);
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

        /*
         * bpItem = new ServiceTypeSummary.Builder().application("DCAE").component("dcae").
         * typeName("xyz1731-helm-1906").owner("xyz1731").typeVersion(1906).build();
         */

        bpItem = new ServiceTypeSummary("xyz1730", "xyz1730-helm-1905", 1905, "DCAE", "dcae",
            "123-456-321", "342343", true);

        bpItem2 = new ServiceTypeSummary("xyz1731", "xyz1731-helm-1906", 1906, "DCAE", "dcae",
            "123-456-789", "342343", true);
        bpItemFull = new ServiceType.Builder("xyz1731", "xyz1731-helm-1906", 1906,
            "tosca_definitions_version: cloudify_dsl_1_3", "", "DCAE", "dcae").build();

        Collection<ServiceTypeSummary> items = new ArrayList<ServiceTypeSummary>();
        items.add(bpItem);

        Collection<ServiceTypeSummary> items2 = new ArrayList<ServiceTypeSummary>();
        items2.add(bpItem2);

        String pageLinks2 =
            "{\"previousLink\":null,\"nextLink\":{\"rel\":\"next\",\"href\":\"https://invt.com:30123/dcae-services/?offset=25\"}}";
        ServiceTypeSummaryList.PaginationLinks paginationLinks =
            objectMapper.readValue(pageLinks2, ServiceTypeSummaryList.PaginationLinks.class);
        int totalCount = 1;
        bpList = new ServiceTypeSummaryList(items, totalCount, paginationLinks);
        bpList2 = new ServiceTypeSummaryList(items2, totalCount, paginationLinks);

    }

    public void createBpUploadItem() {
        bpUploadItem = ServiceTypeRequest.from(bpItemFull);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testCreateBlueprint() throws Exception {
        // Given
        String bpTemplate = "tosca_definitions_version: cloudify_dsl_1_3";
        ServiceTypeUploadRequest bpUploadReq =
            new ServiceTypeUploadRequest("dcae_user", "mod1", 2008, bpTemplate, "DCAE", "dcae");

        // When
        when(inventoryClient.addServiceType(Mockito.<ServiceTypeRequest>any()))
            .thenThrow(httpException).thenThrow(Exception.class).thenReturn(bpItemFull);

        // Then
        String actual =
            subject.createBlueprint(mockedRequest, mockedResponse, bpUploadReq, uriBuilder);
        assertTrue(actual.contains("error"));
        actual = subject.createBlueprint(mockedRequest, mockedResponse, bpUploadReq, uriBuilder);
        assertTrue(actual.contains("error"));
        actual = subject.createBlueprint(mockedRequest, mockedResponse, bpUploadReq, uriBuilder);
        assertTrue(actual.contains("xyz"));
    }

    @Test
    public final void testCreateBlueprint_badBp() throws Exception {

        String bpTemplate = "tosca_definitions_version: cloudify_dsl_1_3";
        ServiceTypeUploadRequest bpUploadReq =
            new ServiceTypeUploadRequest("dcae_user", "mod1", 2008, bpTemplate, "DCAE", "dcae");

        when(Blueprint.parse(bpUploadReq.getBlueprintTemplate()))
            .thenThrow(BlueprintParseException.class);

        String actual1 =
            subject.createBlueprint(mockedRequest, mockedResponse, bpUploadReq, uriBuilder);
        assertTrue(actual1.contains("error"));
    }

    @Test
    public final void testCreateBlueprint_badReq() throws Exception {
        String bpTemplate = "tosca_definitions_version: cloudify_dsl_1_3";
        ServiceTypeUploadRequest bpUploadReq =
            new ServiceTypeUploadRequest("dcae_user", "mod1", 2008, bpTemplate, "DCAE", "");

        String actual =
            subject.createBlueprint(mockedRequest, mockedResponse, bpUploadReq, uriBuilder);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testQueryBlueprint() throws Exception {

        Optional<ServiceType> optionBp = Optional.ofNullable(bpItemFull);
        when(inventoryClient.getServiceType(Mockito.any())).thenReturn(optionBp)
            .thenThrow(Exception.class);

        String actual =
            subject.queryBlueprint("123-343", mockedRequest, mockedResponse, uriBuilder);
        assertTrue(actual.contains("xyz"));

        actual = subject.queryBlueprint("123-343", mockedRequest, mockedResponse, uriBuilder);
        assertTrue(actual.contains("error"));
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

    @Test
    public final void testGetBlueprintsByPage() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        String filterStr =
            "{\"owner\": \"xyz1730\", \"name\": \"xyz1730-helm-1905\", \"id\": \"123\"}";

        mockedRequest.addParameter("filters", filterStr);
        mockedRequest.addParameter("sort", "name");

        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("DCAE_WRITE");
        userApps.add("dcae");

        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1)
            .thenThrow(Exception.class);
        String result =
            subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("xyz"));

        result = subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("error"));
    }

    @Test
    public final void testGetBlueprintsByPage_sortByOwner() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sort", "owner");

        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("ECOMPC_DCAE_WRITE");
        userApps.add("dcae");

        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1)
            .thenThrow(Exception.class);
        String result =
            subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("xyz"));

        result = subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("error"));
    }

    @Test
    public final void testGetBlueprintsByPage_sortByTypeId() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sort", "typeId");

        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("ECOMPC_DCAE_WRITE");
        userApps.add("dcae");

        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1)
            .thenThrow(Exception.class);
        String result =
            subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("xyz"));

        result = subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("error"));
    }

    @Test
    public final void testGetBlueprintsByPage_sortByCreated() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sort", "created");

        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("ECOMPC_DCAE_WRITE");
        userApps.add("dcae");

        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1)
            .thenThrow(Exception.class);
        String result =
            subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("xyz"));

        result = subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("error"));
    }

    @Test
    public final void testGetBlueprintsByPage_sortByVersion() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sort", "typeVersion");

        Set<String> userRoleSet = new HashSet<String>();
        Set<String> userApps = new TreeSet<>();
        userRoleSet.add("Standard User");
        userRoleSet.add("ECOMPC_DCAE_WRITE");
        userApps.add("dcae");

        Mockito.when(mockedRequest.getAttribute("userRoles")).thenReturn(userRoleSet);
        Mockito.when(mockedRequest.getAttribute("userApps")).thenReturn(userApps);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1)
            .thenThrow(Exception.class);
        String result =
            subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("xyz"));

        result = subject.getBlueprintsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(result.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetDeployment() throws HttpStatusCodeException, Exception {
        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);

        when(cfyClient.getDeployment(Mockito.any())).thenReturn(cldDeplList)
            .thenThrow(Exception.class);

        String actual = subject.getDeployment("id1", mockedRequest);
        assertTrue(actual.contains("id1"));

        actual = subject.getDeployment("id1", mockedRequest);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testGetDeploymentsByPage() throws Exception {
        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);

        when(cfyClient.getDeploymentsWithFilter(Mockito.any())).thenReturn(items)
            .thenThrow(Exception.class);

        String actual =
            subject.getDeploymentsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(actual.contains("id1"));

        actual = subject.getDeploymentsByPage(mockedRequest, 1, 1, uriBuilder, mockedResponse);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testGetDeploymentInputs() throws Exception {
        CloudifyDeployment cldDepl = new CloudifyDeployment("description", "blueprint_id",
            "created_at", "updated_at", "id1", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);

        when(cfyClient.getDeploymentInputs(Mockito.any(), Mockito.any())).thenReturn(cldDeplList)
            .thenThrow(httpException).thenThrow(Exception.class);

        String actual = subject.getDeploymentInputs("dep_id", "tenant1", mockedRequest);
        assertTrue(actual.contains("id1"));

        actual = subject.getDeploymentInputs("dep_id", "tenant1", mockedRequest);
        assertTrue(actual.contains("error"));

        actual = subject.getDeploymentInputs("dep_id", "tenant1", mockedRequest);
        assertTrue(actual.contains("error"));

    }

    @Test
    public final void testGetServicesForType() throws Exception {
        String testTypeIds = "44234234";
        ServiceRef expectedSrvc = new ServiceRef("dcae_dtiapi_1902", "432423", "433434");
        Collection<ServiceRef> expectedSrvcIds = new ArrayList<ServiceRef>();
        expectedSrvcIds.add(expectedSrvc);
        ServiceRefList expectedSrvcRefList = new ServiceRefList(expectedSrvcIds, 1);

        when(inventoryClient.getServicesForType(any(ServiceQueryParams.class)))
            .thenReturn(expectedSrvcRefList);
        String actual = subject.getServicesForType(mockedRequest, testTypeIds);
        assertTrue(actual.contains(testTypeIds));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testCreateDeployment() throws Exception {
        // Given
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("key1", "value1");
        inputs.put("key2", "value2");
        inputs.put("key3", "value3");
        inputs.put("key4", 100);

        DeploymentInput deployInput1 =
            new DeploymentInput("component1", "tag1", "xyz1731-helm-1906", 1906, "blueprintId",
                inputs, "tenant1", null, true, true, true, false, true, false);

        DeploymentInput deployInput2 =
            new DeploymentInput("component1", "tag1", "xyz1731-helm-1906", 1906, null, inputs,
                "tenant1", null, true, true, true, false, true, false);

        DeploymentResponseLinks expectLink = new DeploymentResponseLinks("self", "status");
        DeploymentResponse expectResp = new DeploymentResponse("req1", expectLink);

        Collection<ServiceTypeSummary> items = bpList2.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();

        Optional<ServiceType> optionBp = Optional.ofNullable(bpItemFull);

        StringBuffer expectedStrBuff = new StringBuffer();
        expectedStrBuff.append("http://oom.s2.com");

        // When
        when(inventoryClient.getServiceTypes(any(ServiceTypeQueryParams.class)))
            .thenReturn(sampleStream1);

        when(inventoryClient.getServiceType(anyString())).thenReturn(optionBp);

        when(mockedRequest.getRequestURL()).thenReturn(expectedStrBuff);

        when(deploymentHandlerClient.putDeployment(anyString(), anyString(),
            any(DeploymentRequest.class))).thenReturn(expectResp).thenReturn(expectResp)
                .thenThrow(badReqError).thenThrow(srvcExistError).thenThrow(serverError)
                .thenThrow(downStrmError).thenThrow(Exception.class);

        // Then
        String actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput1);
        assertTrue(actualResp.contains("component1"));

        actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput2);
        assertTrue(actualResp.contains("component1"));

        actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput1);
        assertTrue(actualResp.contains("error"));

        actualResp = subject.createDeployment(mockedRequest, mockedResponse, deployInput1);
        assertTrue(actualResp.contains("error"));

    }

    @Test
    public final void testGetExecutionByDeploymentId() throws Exception {
        CloudifyExecution cldExecution = new CloudifyExecution("successful", "created_at",
            "ended_at", "install", false, "bp1", "id1", "tenant1", "error", "execution_id1", null);

        List<CloudifyExecution> cldExecutionList = new ArrayList<CloudifyExecution>();

        cldExecutionList.add(cldExecution);

        CloudifyExecutionList cloudifyExecutionList =
            new CloudifyExecutionList(cldExecutionList, null);

        when(cfyClient.getExecutionsSummary(Mockito.any(), Mockito.any()))
            .thenReturn(cloudifyExecutionList).thenThrow(httpException).thenThrow(Exception.class);;

        String actual = subject.getExecutionByDeploymentId("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("execution_id1"));

        actual = subject.getExecutionByDeploymentId("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("error"));

        actual = subject.getExecutionByDeploymentId("deploymentId", "tenant", mockedRequest);
        assertTrue(actual.contains("error"));

    }

    @Test
    public final void testGetServiceHealthByDeploymentId() throws Exception {
        String[] svcTags = {"cfytenantname=onap"};
        ConsulServiceHealth consulSrvcHlth = new ConsulServiceHealth("cjlvmcnsl00",
            "service:pgaas1_Service_ID", "Service 'pgaasServer1' check", "passing",
            "This is a pgaas1_Service_ID health check",
            "HTTP GET http://srvc.com:8000/healthcheck/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.com|secondaries=0 |maintenance= |down=1 pgaas2.com| \" }\n",
            "pgaas1_Service_ID", "pgaasServer1", svcTags, 190199, 199395);

        ConsulDeploymentHealth cnslDeployHlth =
            new ConsulDeploymentHealth.Builder(consulSrvcHlth).build();

        when(consulClient.getServiceHealthByDeploymentId(Mockito.any())).thenReturn(cnslDeployHlth)
            .thenThrow(httpException).thenThrow(Exception.class);

        String actual = subject.getServiceHealthByDeploymentId("deploymentId", mockedRequest);
        assertTrue(actual.contains("cjlvmcnsl00"));

        actual = subject.getServiceHealthByDeploymentId("deploymentId", mockedRequest);
        assertTrue(actual.contains("error"));

        actual = subject.getServiceHealthByDeploymentId("deploymentId", mockedRequest);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testDeleteBlueprint() throws Exception {
        // String expected = "{\"202\": \"OK\"}";
        String expected = "{\"204\": \"Blueprint deleted\"}";
        List<CloudifyDeployedTenant> deplForBp = new ArrayList<>();
        deplForBp.clear();
        Mockito.when(cfyClient.getDeploymentForBlueprint(anyString())).thenReturn(deplForBp);

        Optional<ServiceType> optionBp = Optional.ofNullable(bpItemFull);
        Mockito.when(inventoryClient.getServiceType(anyString())).thenReturn(optionBp);

        List<ServiceRef> srvcRefList = new ArrayList<>();
        srvcRefList.clear();
        int itemCnt = 0;
        ServiceRefList mockSvcRefList = new ServiceRefList(srvcRefList, itemCnt);

        Mockito.when(inventoryClient.getServicesForType(any(ServiceQueryParams.class)))
            .thenReturn(mockSvcRefList);

        doNothing().doThrow(serviceTypeException).doThrow(Exception.class).when(inventoryClient)
            .deleteServiceType(anyString());

        String actual =
            subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse, uriBuilder);
        assertEquals(expected, actual);

        actual = subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse, uriBuilder);
        assertTrue(actual.contains("error"));

        actual = subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse, uriBuilder);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testDeleteBlueprint_withDepl() throws Exception {
        CloudifyDeployedTenant mockCfyDeplTen =
            new CloudifyDeployedTenant("id1", "tenant", "45435435", "54543534");

        Optional<ServiceType> optionBp = Optional.ofNullable(bpItemFull);
        Mockito.when(inventoryClient.getServiceType(anyString())).thenReturn(optionBp);

        List<CloudifyDeployedTenant> deplForBp = new ArrayList<>();
        deplForBp.add(mockCfyDeplTen);
        Mockito.when(cfyClient.getDeploymentForBlueprint(anyString())).thenReturn(deplForBp);

        String actual =
            subject.deleteBlueprint("srvcId", mockedRequest, mockedResponse, uriBuilder);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testDeleteDeployment() throws Exception {
        CloudifyDeployment cldDepl =
            new CloudifyDeployment("description", "blueprint_id", "created_at", "updated_at",
                "dcae_dep_id", null, null, null, null, null, null, null, "tenant1");

        List<CloudifyDeployment> items = new ArrayList<CloudifyDeployment>();
        items.add(cldDepl);

        CloudifyDeploymentList.Metadata.Pagination pageObj =
            new CloudifyDeploymentList.Metadata.Pagination(1, 0, 1);

        CloudifyDeploymentList.Metadata metadata = new CloudifyDeploymentList.Metadata(pageObj);

        CloudifyDeploymentList cldDeplList = new CloudifyDeploymentList(items, metadata);

        Mockito.when(cfyClient.getDeployment(anyString(), anyString())).thenReturn(cldDeplList);

        doNothing().doThrow(badReqError).doThrow(serverError).doThrow(downStrmError)
            .doThrow(notFoundError).doThrow(Exception.class).when(deploymentHandlerClient)
            .deleteDeployment(anyString(), anyString());

        String actual =
            subject.deleteDeployment("dcae_dep_id", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("dcae_dep_id"));

        actual = subject.deleteDeployment("dcae_dep_id", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dcae_dep_id", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dcae_dep_id", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dcae_dep_id", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteDeployment("dcae_dep_id", mockedRequest, "tenant1", mockedResponse);
        assertTrue(actual.contains("error"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCancelExecution() throws Exception {
        List<String> tenants = new ArrayList<String>();
        tenants.add("tenant1");

        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.put("tenant", tenants);

        CloudifyExecution cfyExecObj = new CloudifyExecution("successful", "created_at", "ended_at",
            "install", false, "bp1", "id1", "tenant1", "error", "execution_id1", null);

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

    @SuppressWarnings("unchecked")
    @Test
    public final void testModifyDeployment() throws Exception {
        CloudifyExecution cldExecution = new CloudifyExecution("successful", "created_at",
            "ended_at", "install", false, "bp1", "id1", "tenant1", "error", "execution_id1", null);

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

        String secretTokenStr =
            "{\"created_at\": \"created_ts\", \"key\": \"acl_key\", \"updated_at\": \"updated_ts\", \"value\": \"acl_token_val\", \"visibility\": \"global\", \"is_hidden_value\": \"false\", \"tenant_name\": \"tenant\", \"resource_availability\": \"rsrc\"}";
        CloudifySecret secretData = null;
        try {
            secretData = objectMapper.readValue(secretTokenStr, CloudifySecret.class);
        } catch (Exception e) {

        }
        when(cfyClient.getSecret(Mockito.any(), Mockito.any())).thenReturn(secretData);
        when(cfyClient.startExecution(any(CloudifyExecutionRequest.class))).thenReturn(cldExecution)
            .thenThrow(Exception.class).thenThrow(httpException);

        String inputParamStr = "{\"tenant\": \"tenant1\", \"workflow\":\"upgrade\"}";

        InputStream is1 = new ByteArrayInputStream(inputParamStr.getBytes());
        String actualResult = subject.modifyDeployment("depId", mockedRequest, is1);
        assertTrue(actualResult.contains("execution_id1"));

        InputStream is2 = new ByteArrayInputStream(inputParamStr.getBytes());
        actualResult = subject.modifyDeployment("depId", mockedRequest, is2);
        assertTrue(actualResult.contains("error"));

        InputStream is3 = new ByteArrayInputStream(inputParamStr.getBytes());
        actualResult = subject.modifyDeployment("depId", mockedRequest, is3);
        assertTrue(actualResult.contains("error"));

    }

}
