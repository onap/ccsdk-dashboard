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
import java.util.List;
import java.util.Map;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.inventory.Blueprint;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummaryList;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.util.CacheManager;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
@PrepareForTest({Blueprint.class})
public class InventoryControllerTest extends MockitoTestSuite {

    @Mock
    private CloudifyClient cfyClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private InventoryController subject;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    private HttpServerErrorException httpException =
        new HttpServerErrorException(HttpStatus.BAD_GATEWAY);

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

    ServiceTypeSummary bpItem, bpItem2 = null;
    ServiceTypeSummaryList bpList, bpList2 = null;

    ServiceType bpItemFull = null;
    ServiceTypeList bpItemFullList = null;

    ServiceTypeRequest bpUploadItem = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        getExpectedBlueprints();
        createBpUploadItem();
        mockedRequest = getMockedRequest();
        mockedResponse = getMockedResponse();
        CacheManager testCache = new CacheManager();
        Map<String, List<ServiceTypeSummary>> bpPerOwner =
            new HashMap<String, List<ServiceTypeSummary>>();
        bpPerOwner.put("xyz1731", (List<ServiceTypeSummary>) bpList2.items);
        testCache.putObject("owner_bp_map", bpPerOwner);
        subject.setCacheManager(testCache);
        /*
         * PowerMockito.mockStatic(SystemProperties.class);
         * Mockito
         * .when(SystemProperties.getProperty("cache_switch"))
         * .thenReturn("1");
         */
        PowerMockito.mockStatic(Blueprint.class);
    }

    public void getExpectedBlueprints()
        throws JsonParseException, JsonMappingException, IOException {
        bpItem = new ServiceTypeSummary.Builder().application("app1").component("comp1")
            .typeName("xyz1731-helm-1906").owner("xyz1731").typeVersion(1906).build();

        bpItem2 = new ServiceTypeSummary("xyz1731", "xyz1731-helm-1906", 1906, "app1", "comp1",
            "123-456-789", "342343", true);
        bpItemFull = new ServiceType.Builder("xyz1731", "xyz1731-helm-1906", 1906,
            "tosca_definitions_version: cloudify_dsl_1_3", "", "app1", "comp1").build();

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
    public final void testGetOwnersByPage() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetAllServiceTypeNames() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getAllServiceTypeNames(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetAllServiceTypeIds() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getAllServiceTypeIds(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        // mockedRequest.addParameter("searchBy", "xyz");
        mockedRequest.addParameter("sortBy", "owner");

        // # 1st case
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

        // # 2nd case
        HttpSession session = mockedRequest.getSession();
        HashMap<String, Boolean> comp_deploy_tab = new HashMap<String, Boolean>();
        comp_deploy_tab.put("comp1", true);
        Set<String> userApps = new TreeSet<String>();
        userApps.add("comp1");
        when(session.getAttribute("comp_access")).thenReturn(comp_deploy_tab);
        when(session.getAttribute("role_level")).thenReturn("app");
        when(session.getAttribute("authComponents")).thenReturn(userApps);

        Stream<ServiceTypeSummary> sampleStream2 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream2);
        /*
         * Mockito.when(inventoryClient.getServiceTypes(Matchers.<ServiceTypeQueryParams>any()))
         * .thenReturn(sampleStream2);
         */
        String result2 = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result2.contains("xyz"));

    }

    @Test
    public final void testGetServiceTypesByPage_appDevUser() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        HttpSession session = mockedRequest.getSession();
        HashMap<String, Boolean> comp_deploy_tab = new HashMap<String, Boolean>();
        comp_deploy_tab.put("comp1", true);
        Set<String> userApps = new TreeSet<String>();
        userApps.add("comp1");
        when(session.getAttribute("comp_access")).thenReturn(comp_deploy_tab);
        when(session.getAttribute("role_level")).thenReturn("app_dev");
        when(session.getAttribute("authComponents")).thenReturn(userApps);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream);

        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_containsFilter() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("searchBy", "contains:xyz1731-helm-1906");
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream);

        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("error"));
    }

    @Test
    public final void testGetServiceTypesByPage_AllFilters() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        mockedRequest.addParameter("searchBy",
            "serviceRef:xyz1731-helm-1906;app:app1;comp:comp1;owner:xyz1731;");
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);

        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> sampleStream = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream);

        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

    }

    @Test
    public final void testGetServiceTypesByPage_comp() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        mockedRequest.addParameter("sortBy", "component");

        Collection<ServiceTypeSummary> items = bpList2.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));

    }

    @Test
    public final void testGetServiceTypesByPage_typeId() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        mockedRequest.addParameter("sortBy", "typeId");
        Collection<ServiceTypeSummary> items = bpList2.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_typeName() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        mockedRequest.addParameter("sortBy", "typeName");
        Collection<ServiceTypeSummary> items = bpList2.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();
        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_typeVer() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        mockedRequest.addParameter("sortBy", "typeVersion");
        Collection<ServiceTypeSummary> items = bpList2.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @Test
    public final void testGetServiceTypesByPage_created() throws Exception {
        User user = mockUser.mockUser();
        user.setLoginId("tester");
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
        mockedRequest.addParameter("sortBy", "created");
        Collection<ServiceTypeSummary> items = bpList2.items;
        Stream<ServiceTypeSummary> sampleStream1 = items.stream();

        Mockito.when(inventoryClient.getServiceTypes()).thenReturn(sampleStream1);
        String result = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(result.contains("xyz"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetServiceTypesByPage_Exception() throws Exception {
        when(inventoryClient.getServiceTypes()).thenThrow(RestClientException.class)
            .thenThrow(httpException);

        String errResp = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(errResp.contains("error"));

        errResp = subject.getServiceTypesByPage(mockedRequest);
        assertTrue(errResp.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUploadServiceTypeBlueprint() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        String expected = "{\"201\": \"OK\"}";

        when(inventoryClient.addServiceType(Mockito.<ServiceTypeRequest>any()))
            .thenThrow(httpException).thenThrow(Exception.class).thenReturn(null);

        String actual2 = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertTrue(actual2.contains("error"));

        String actual3 = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertTrue(actual3.contains("error"));

        String actual = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertEquals(expected, actual);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUploadServiceTypeBlueprint_badBp() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();

        when(Blueprint.parse(bpUploadItem.getBlueprintTemplate()))
            .thenThrow(BlueprintParseException.class);

        String actual1 = subject.uploadServiceTypeBlueprint(mockedRequest, bpUploadItem);
        assertTrue(actual1.contains("error"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateServiceTypeBlueprint() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        String expected = "{\"201\": \"OK\"}";

        Mockito.when(inventoryClient.addServiceType(Mockito.<ServiceType>any()))
            .thenThrow(httpException).thenThrow(Exception.class).thenReturn(null);

        String actual2 = subject.updateServiceTypeBlueprint(mockedRequest, bpItemFull);
        assertTrue(actual2.contains("error"));

        String actual3 = subject.updateServiceTypeBlueprint(mockedRequest, bpItemFull);
        assertTrue(actual3.contains("error"));

        String actual = subject.updateServiceTypeBlueprint(mockedRequest, bpItemFull);
        assertEquals(expected, actual);
    }

    @Test
    public final void testUpdateServiceTypeBlueprint_badBp() throws Exception {
        MockHttpServletRequestWrapper mockedRequest = getMockedRequest();

        when(Blueprint.parse(bpUploadItem.getBlueprintTemplate()))
            .thenThrow(BlueprintParseException.class);

        String actual1 = subject.updateServiceTypeBlueprint(mockedRequest, bpItemFull);
        assertTrue(actual1.contains("error"));

    }

    @Test
    public final void testDeleteServiceType() throws Exception {
        // String expected = "{\"202\": \"OK\"}";
        String expected = "{\"204\": \"Blueprint deleted\"}";
        List<CloudifyDeployedTenant> deplForBp = new ArrayList<>();
        deplForBp.clear();
        Mockito.when(cfyClient.getDeploymentForBlueprint(Mockito.anyString()))
            .thenReturn(deplForBp);

        List<ServiceRef> srvcRefList = new ArrayList<>();
        srvcRefList.clear();
        int itemCnt = 0;
        ServiceRefList mockSvcRefList = new ServiceRefList(srvcRefList, itemCnt);

        Mockito.when(inventoryClient.getServicesForType(Mockito.<ServiceQueryParams>any()))
            .thenReturn(mockSvcRefList);

        doNothing().doThrow(serviceTypeException).doThrow(Exception.class).when(inventoryClient)
            .deleteServiceType(Mockito.anyString());

        String actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertEquals(expected, actual);

        actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));

        actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
    }

    @Test
    public final void testDeleteServiceType_withDepl() throws Exception {
        CloudifyDeployedTenant mockCfyDeplTen =
            new CloudifyDeployedTenant("id1", "tenant", "45435435", "54543534");

        List<CloudifyDeployedTenant> deplForBp = new ArrayList<>();
        deplForBp.add(mockCfyDeplTen);
        Mockito.when(cfyClient.getDeploymentForBlueprint(Mockito.anyString()))
            .thenReturn(deplForBp);

        String actual = subject.deleteServiceType("srvcId", mockedRequest, mockedResponse);
        assertTrue(actual.contains("error"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testViewServiceTypeBlueprintContentById() throws Exception {
        Optional<ServiceType> expected = Optional.of(bpItemFull);
        when(inventoryClient.getServiceType(Mockito.anyString())).thenReturn(expected)
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

        when(inventoryClient.getServicesForType(Mockito.<ServiceQueryParams>any()))
            .thenReturn(expectedSrvcRefList);
        String actual = subject.getServicesForType(mockedRequest, testTypeIds);
        assertTrue(actual.contains(testTypeIds[0]));
    }

}
