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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DashboardProperties.class})
public class InventoryControllerTest extends MockitoTestSuite {

    @Mock
    private CloudifyClient restClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private InventoryController subject = new InventoryController();

    protected final ObjectMapper objectMapper = new ObjectMapper();

    private HttpServerErrorException httpException =
        new HttpServerErrorException(HttpStatus.BAD_GATEWAY);

    private ServiceNotFoundException serviceException =
        new ServiceNotFoundException("Invalid deployment");

    private ServiceTypeNotFoundException serviceTypeException =
        new ServiceTypeNotFoundException("Invalid blueprint");

    @Mock
    UserUtils userUtils = new UserUtils();

    @Mock
    User epuser;

    @Mock
    AppUtils appUtils = new AppUtils();

    HttpServletRequest mockedRequest;
    HttpServletResponse mockedResponse;

    MockUser mockUser = new MockUser();
    ServiceList deplList = null;
    Service deplItem = null;

    ServiceType bpItem = null;
    ServiceTypeList bpList = null;

    ServiceTypeRequest bpUploadItem = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        getExpectedDeployments();
        getExpectedBueprints();
        createBpUploadItem();
        mockedRequest = getMockedRequest();
        mockedResponse = getMockedResponse();
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
    }

    public void createBpUploadItem() {
        bpUploadItem = ServiceTypeRequest.from(bpItem);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGetServiceTypesByPage() {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("searchBy", "xyz");
        mockedRequest.addParameter("sortBy", "owner");

        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Collection<ServiceType> items = bpList.items;

        Stream<ServiceType> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        HttpSession session = mockedRequest.getSession();
        HashMap<String, Boolean> comp_deploy_tab = new HashMap<String, Boolean>();
        comp_deploy_tab.put("comp1", true);

        Set<String> userApps = new TreeSet<String>();
        userApps.add("comp1");

        when(session.getAttribute("comp_access")).thenReturn(comp_deploy_tab);
        when(session.getAttribute("role_level")).thenReturn("app");
        when(session.getAttribute("authComponents")).thenReturn(userApps);

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_appl() {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "application");

        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_comp() {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "component");

        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_typeId() {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "typeId");
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_typeName() {

        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "typeName");
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));

    }

    @Test
    public final void testGetServiceTypesByPage_typeVer() {

        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "typeVersion");
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));

    }

    @Test
    public final void testGetServiceTypesByPage_created() {

        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "created");
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream1 = items.stream();

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<ServiceType> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream2);
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_Auth() {

        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("searchBy", "xyz");

        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream = items.stream();

        HttpSession session = mockedRequest.getSession();
        HashMap<String, Boolean> comp_deploy_tab = new HashMap<String, Boolean>();
        comp_deploy_tab.put("comp1", true);

        Set<String> userApps = new TreeSet<String>();
        userApps.add("comp1");

        when(session.getAttribute("comp_access")).thenReturn(comp_deploy_tab);
        when(session.getAttribute("role_level")).thenReturn("ops");
        when(session.getAttribute("authComponents")).thenReturn(userApps);

        Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
            .thenReturn(sampleStream);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetServiceTypesByPage_Exception() {
        PowerMockito.mockStatic(DashboardProperties.class);
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream = items.stream();
        when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");
        when(inventoryClient.getServiceTypes()).thenThrow(RestClientException.class)
            .thenThrow(httpException).thenReturn(sampleStream);

        String errResp = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(errResp.contains("error"));

        errResp = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(errResp.contains("error"));

        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_Filter() {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("searchBy", "xyz");
        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);

        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> sampleStream = items.stream();
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream);

        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServicesByPage_auth() throws IOException {
        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");
        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("searchBy", "dti");

        Collection<Service> items = deplList.items;

        HttpSession session = mockedRequest1.getSession();
        HashMap<String, Boolean> comp_deploy_tab = new HashMap<String, Boolean>();
        comp_deploy_tab.put("dcae", true);

        Set<String> userApps = new TreeSet<String>();
        userApps.add("dcae");

        when(session.getAttribute("comp_access")).thenReturn(comp_deploy_tab);
        when(session.getAttribute("role_level")).thenReturn("app");
        when(session.getAttribute("authComponents")).thenReturn(userApps);

        Stream<Service> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream1);

        String result1 = subject.getServicesByPage(mockedRequest1);
        assertTrue(result1.contains("dti"));

    }

    @Test
    public final void testGetServicesByPage_auth_ops() throws IOException {
        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");
        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("searchBy", "dti");

        Collection<Service> items = deplList.items;

        HttpSession session = mockedRequest1.getSession();
        HashMap<String, Boolean> comp_deploy_tab = new HashMap<String, Boolean>();
        comp_deploy_tab.put("dcae", true);

        Set<String> userApps = new TreeSet<String>();
        userApps.add("dcae");

        when(session.getAttribute("comp_access")).thenReturn(comp_deploy_tab);
        when(session.getAttribute("role_level")).thenReturn("ops");
        when(session.getAttribute("authComponents")).thenReturn(userApps);

        Stream<Service> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream1);

        String result1 = subject.getServicesByPage(mockedRequest1);
        assertTrue(result1.contains("dti"));

    }

    @Test
    public final void testGetServicesByPage() throws IOException {
        /*
         * User user = mockUser.mockUser(); user.setLoginId("tester");
         */
        MockHttpServletRequestWrapper mockedRequest1 = getMockedRequest();
        mockedRequest1.addParameter("searchBy", "dti");
        mockedRequest1.addParameter("sortBy", "deploymentRef");

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Collection<Service> items = deplList.items;

        Stream<Service> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream1);

        String result1 = subject.getServicesByPage(mockedRequest1);
        assertTrue(result1.contains("dti"));
    }

    @Test
    public final void testGetServicesByPage_sort_serviceId() throws IOException {

        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "serviceId");

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Collection<Service> items = deplList.items;

        Stream<Service> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream1);

        String result = subject.getServicesByPage(mockedRequest);
        assertTrue(result.contains("dti"));

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<Service> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream2);

        String result2 = subject.getServicesByPage(mockedRequest);
        assertTrue(result2.contains("dti"));
    }

    @Test
    public final void testGetServicesByPage_sort_created() throws IOException {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "created");

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Collection<Service> items = deplList.items;

        Stream<Service> sampleStream = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream);

        String result = subject.getServicesByPage(mockedRequest);
        assertTrue(result.contains("dti"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<Service> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream2);

        String result2 = subject.getServicesByPage(mockedRequest);
        assertTrue(result2.contains("dti"));

    }

    @Test
    public final void testGetServicesByPage_sort_modified() throws IOException {

        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("sortBy", "modified");

        PowerMockito.mockStatic(DashboardProperties.class);
        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("os");

        Collection<Service> items = deplList.items;

        Stream<Service> sampleStream = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream);

        String result = subject.getServicesByPage(mockedRequest);
        assertTrue(result.contains("dti"));

        Mockito
            .when(DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth"))
            .thenReturn("auth");

        Stream<Service> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServices()).thenReturn(sampleStream2);

        String result2 = subject.getServicesByPage(mockedRequest);
        assertTrue(result2.contains("dti"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUploadServiceTypeBlueprint() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        String expected = "{\"201\": \"OK\"}";
        when(inventoryClient.addServiceType(Matchers.<ServiceTypeRequest>any()))
            .thenThrow(BlueprintParseException.class).thenThrow(httpException)
            .thenThrow(Exception.class).thenReturn(null);

        String actual1 = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertTrue(actual1.contains("error"));

        String actual2 = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertTrue(actual2.contains("error"));

        String actual3 = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertTrue(actual3.contains("error"));

        String actual = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertEquals(expected, actual);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateServiceTypeBlueprint() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        String expected = "{\"201\": \"OK\"}";
        Mockito.when(inventoryClient.addServiceType(Matchers.<ServiceType>any()))
            .thenThrow(BlueprintParseException.class).thenThrow(httpException)
            .thenThrow(Exception.class).thenReturn(null);

        String actual1 = subject.updateServiceTypeBlueprint(mockedRequest, bpItem);
        assertTrue(actual1.contains("error"));

        String actual2 = subject.updateServiceTypeBlueprint(mockedRequest, bpItem);
        assertTrue(actual2.contains("error"));

        String actual3 = subject.updateServiceTypeBlueprint(mockedRequest, bpItem);
        assertTrue(actual3.contains("error"));

        String actual = subject.updateServiceTypeBlueprint(mockedRequest, bpItem);
        assertEquals(expected, actual);
    }

    @Test
    public final void testDeleteService() throws Exception {
        String expected = "{\"202\": \"OK\"}";
        doNothing().doThrow(serviceException).doThrow(Exception.class).when(inventoryClient)
            .deleteService(Matchers.anyString());

        String actual = subject.deleteService("srvcId", mockedRequest, mockedResponse);
        assertEquals(expected, actual);

        actual = subject.deleteService("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteService("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testDeleteServiceType() throws Exception {
        String expected = "{\"202\": \"OK\"}";
        doNothing().doThrow(serviceTypeException).doThrow(Exception.class).when(inventoryClient)
            .deleteServiceType(Matchers.anyString());

        String actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertEquals(expected, actual);

        actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testViewServiceTypeBlueprintContentById() throws Exception {
        Optional<ServiceType> expected = Optional.of(bpItem);
        when(inventoryClient.getServiceType(Matchers.anyString())).thenReturn(expected)
            .thenThrow(httpException).thenThrow(Exception.class);

        String result = subject.viewServiceTypeBlueprintContentById("typeId", mockedRequest);
        assertTrue(result.contains("xyz"));

        result = subject.viewServiceTypeBlueprintContentById("typeId", mockedRequest);
        assertTrue(result.contains("error"));

        result = subject.viewServiceTypeBlueprintContentById("typeId", mockedRequest);
        assertTrue(result.contains("error"));
    }

    @Test
    public final void testGetServicesForType() throws Exception {
        String[] testTypeIds = {"44234234"};
        ServiceRef expectedSrvc = new ServiceRef("dcae_dtiapi_1902", "432423", "433434");
        Collection<ServiceRef> expectedSrvcIds = new ArrayList<ServiceRef>();
        expectedSrvcIds.add(expectedSrvc);
        ServiceRefList expectedSrvcRefList = new ServiceRefList(expectedSrvcIds, 1);

        when(inventoryClient.getServicesForType(Matchers.<ServiceQueryParams>any()))
            .thenReturn(expectedSrvcRefList);
        String actual = subject.getServicesForType(mockedRequest, testTypeIds);
        assertTrue(actual.contains(testTypeIds[0]));
    }

    /*
     * @Test public final void testGetItemListForPageWrapper() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testGetServicesForType() {
     * fail("Not yet implemented"); // TODO }
     * 
     * 
     * 
     * @Test public final void testViewServiceTypeBlueprintContentById() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testDeleteServiceType() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testDeleteService() { fail("Not yet implemented"); //
     * TODO }
     * 
     * @Test public final void testUpdateServiceTypeBlueprint() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testUploadServiceTypeBlueprint() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testGetAppProperties() { fail("Not yet implemented");
     * // TODO }
     * 
     * @Test public final void testGetOrSetControllerEndpointSelectionLong() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testGetOrSetControllerEndpointSelection() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testGetInventoryClientHttpServletRequest() {
     * fail("Not yet implemented"); // TODO }
     * 
     * @Test public final void testGetInventoryClientLong() {
     * fail("Not yet implemented"); // TODO }
     */
}
