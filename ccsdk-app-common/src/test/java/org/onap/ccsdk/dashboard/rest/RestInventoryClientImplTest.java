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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.model.inventory.ApiResponseMessage;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummaryList;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DashboardProperties.class})
public class RestInventoryClientImplTest {

    @Mock
    RestTemplate mockRest;

    @InjectMocks
    RestInventoryClientImpl subject = new RestInventoryClientImpl();

    protected final ObjectMapper objectMapper = new ObjectMapper();

    ServiceList deplList = null;
    ServiceList deplListNext = null;
    Service deplItem = null;

    ServiceTypeSummary bpItem, bpItem2 = null;
    ServiceTypeSummaryList bpList, bpList2 = null;

    ServiceType bpItemFull = null;
    ServiceTypeList bpItemFullList = null;
    
    ServiceTypeSummaryList bpListNext = null;

    ServiceTypeRequest bpUploadItem = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        getExpectedBlueprints();
        getExpectedDeployments();
        PowerMockito.mockStatic(DashboardProperties.class);
        when(DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_INVENTORY_URL)).thenReturn("https://invt.com");
        CacheManager testCache = new CacheManager();
        subject.setCacheManager(testCache); 
        this.subject.init();
    }

    @After
    public void tearDown() throws Exception {
    }

    public void getExpectedDeployments()
        throws JsonParseException, JsonMappingException, IOException {
        deplItem = new Service("dcae_dtiapi_1902", null, "1552335532348", "1552335532348", null,
            "dummyVnfId", null, "dummyVnfType", "dummyLocation", "dcae_dtiapi_1902", null);
        Collection<Service> items = new ArrayList<Service>();
        items.add(deplItem);

        String pageLinks =
            "{\"previousLink\":null,\"nextLink\":{\"rel\":\"next\",\"href\":\"https://invt.com:30123/dcae-services/?offset=25\"}}";
        String pageLinks2 = "{\"previousLink\":null,\"nextLink\":null}";

        ServiceList.PaginationLinks paginationLinks =
            objectMapper.readValue(pageLinks, ServiceList.PaginationLinks.class);
        ServiceList.PaginationLinks paginationLinks2 =
            objectMapper.readValue(pageLinks2, ServiceList.PaginationLinks.class);

        int totalCount = 1;
        deplList = new ServiceList(items, totalCount, paginationLinks2);
        deplListNext = new ServiceList(items, totalCount, paginationLinks2);
    }
    
    public void getExpectedBlueprints()
        throws JsonParseException, JsonMappingException, IOException {
        
        bpItem = new ServiceTypeSummary.Builder().application("app1").component("comp1").
        typeName("xyz1731-helm-1906").owner("xyz1731").typeVersion(1906).build();
        
        bpItem2 = new ServiceTypeSummary("xyz1731", "xyz1731-helm-1906", 1906, "app1", "comp1", "123-456-789",
            "342343", true);
        bpItemFull = new ServiceType.Builder("xyz1731", "xyz1731-helm-1906", 1906,
            "tosca_definitions_version: cloudify_dsl_1_3", "", "app1", "comp1").build();

        Collection<ServiceTypeSummary> items = new ArrayList<ServiceTypeSummary>();
        items.add(bpItem);
        Collection<ServiceTypeSummary> items2 = new ArrayList<ServiceTypeSummary>();
        items2.add(bpItem2);        
        
        String pageLinks =
            "{\"previousLink\":null,\"nextLink\":{\"rel\":\"next\",\"href\":\"https://invt.com:30123/dcae-service-types/?offset=25\"}}";
        String pageLinks2 = "{\"previousLink\":null,\"nextLink\":null}";
        ServiceTypeSummaryList.PaginationLinks paginationLinks =
            objectMapper.readValue(pageLinks, ServiceTypeSummaryList.PaginationLinks.class);
        ServiceTypeSummaryList.PaginationLinks paginationLinks2 =
            objectMapper.readValue(pageLinks2, ServiceTypeSummaryList.PaginationLinks.class);

        int totalCount = 2;
        bpList = new ServiceTypeSummaryList(items, totalCount, paginationLinks);
        bpList2 = new ServiceTypeSummaryList(items2, totalCount, paginationLinks);
        bpListNext = new ServiceTypeSummaryList(items, totalCount, paginationLinks2);
    }

    @Test
    public final void testCheckHealth() {
        String expectStr = "Inventory mS health check";
        ResponseEntity<String> response = 
            new ResponseEntity<String>(expectStr, HttpStatus.OK);
        
        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<String>>any())).thenReturn(response)
                .thenReturn(response);
        
        String actualStr = subject.checkHealth();
        assertTrue(actualStr.equals(expectStr));
    }

    @Test
    public final void testGetServiceTypes() {
        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> expectedResult = items.stream();

        ResponseEntity<ServiceTypeSummaryList> response =
            new ResponseEntity<ServiceTypeSummaryList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeSummaryList> response2 =
            new ResponseEntity<ServiceTypeSummaryList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeSummaryList>>any())).thenReturn(response)
                .thenReturn(response2);

        Stream<ServiceTypeSummary> actualResult = subject.getServiceTypes();
        assertNotNull(actualResult);
        assertTrue(expectedResult.count() == actualResult.count());
    }

    @Test
    public final void testCacheServiceTypes() {
        ResponseEntity<ServiceTypeSummaryList> response =
            new ResponseEntity<ServiceTypeSummaryList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeSummaryList> response2 =
            new ResponseEntity<ServiceTypeSummaryList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeSummaryList>>any())).thenReturn(response)
                .thenReturn(response2);
        
        subject.cacheServiceTypes();
    }
    
    @Test
    public final void testGetServiceTypesServiceTypeQueryParams() {
        ServiceTypeQueryParams qryParms = new ServiceTypeQueryParams.Builder()
            .asdcResourceId("asdcResourceId").asdcServiceId("asdcServiceId").onlyActive(true)
            .onlyLatest(false).serviceId("serviceId").serviceLocation("serviceLocation")
            .typeName("typeName").vnfType("vnfType").build();
        Collection<ServiceTypeSummary> items = bpList.items;
        Stream<ServiceTypeSummary> expectedResult = items.stream();

        ResponseEntity<ServiceTypeSummaryList> response =
            new ResponseEntity<ServiceTypeSummaryList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeSummaryList> response2 =
            new ResponseEntity<ServiceTypeSummaryList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeSummaryList>>any())).thenReturn(response)
                .thenReturn(response2);

        Stream<ServiceTypeSummary> actualResult = subject.getServiceTypes(qryParms);
        assertNotNull(actualResult);
        assertTrue(expectedResult.count() == actualResult.count());
    }

    @Test
    public final void testGetServiceType() {
        Optional<ServiceType> expectedResult = Optional.of(bpItemFull);

        ResponseEntity<ServiceType> response =
            new ResponseEntity<ServiceType>(bpItemFull, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<ServiceType>>any()))
                .thenReturn(response);

        Optional<ServiceType> actualResult = subject.getServiceType("432432423");
        assertTrue(expectedResult.get().getTypeName().equals(actualResult.get().getTypeName()));
    }

    @Test
    public final void testGetServicesForType() throws Exception {
        String typeId = "44234234";
        ServiceRef expectedSrvc = new ServiceRef("dcae_dtiapi_1902", "432423", "433434");
        Collection<ServiceRef> expectedSrvcIds = new ArrayList<ServiceRef>();
        expectedSrvcIds.add(expectedSrvc);
        ServiceRefList expectedSvcRefList = new ServiceRefList(expectedSrvcIds, 1);
        ResponseEntity<ServiceList> response =
            new ResponseEntity<ServiceList>(deplList, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<ServiceList>>any()))
                .thenReturn(response);
        
        ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
        ServiceRefList actualSvcRefList = subject.getServicesForType(qryParams);
        assertTrue(actualSvcRefList.totalCount == expectedSvcRefList.totalCount);  
    }
    
    @Test
    public final void testAddServiceTypeServiceType() {

        when(mockRest.postForObject(Matchers.anyString(), Matchers.<HttpEntity<?>>any(),
            Matchers.<Class<ServiceType>>any())).thenReturn(bpItemFull);

        ResponseEntity<ServiceTypeSummaryList> response =
            new ResponseEntity<ServiceTypeSummaryList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeSummaryList> response2 =
            new ResponseEntity<ServiceTypeSummaryList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeSummaryList>>any())).thenReturn(response)
                .thenReturn(response2);
        
        ServiceType actualResult = subject.addServiceType(bpItemFull);
        assertTrue(actualResult.getTypeName().contains("xyz"));
    }

    @Test
    public final void testAddServiceTypeServiceTypeRequest() {
        ServiceTypeRequest srvcReq = ServiceTypeRequest.from(bpItemFull);

        when(mockRest.postForObject(Matchers.anyString(), Matchers.<HttpEntity<?>>any(),
            Matchers.<Class<ServiceType>>any())).thenReturn(bpItemFull);
        
        ResponseEntity<ServiceTypeSummaryList> response =
            new ResponseEntity<ServiceTypeSummaryList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeSummaryList> response2 =
            new ResponseEntity<ServiceTypeSummaryList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeSummaryList>>any())).thenReturn(response)
                .thenReturn(response2);
        ServiceType actualResult = subject.addServiceType(srvcReq);
        assertTrue(actualResult.getTypeName().contains("xyz"));
    }

    @Test
    public final void testDeleteServiceType() throws Exception {
        ResponseEntity<ApiResponseMessage> response =
            new ResponseEntity<ApiResponseMessage>(HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ApiResponseMessage>>any())).thenReturn(response);
        
        ResponseEntity<ServiceTypeSummaryList> response1 =
            new ResponseEntity<ServiceTypeSummaryList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeSummaryList> response2 =
            new ResponseEntity<ServiceTypeSummaryList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeSummaryList>>any())).thenReturn(response1)
                .thenReturn(response2);
        
        subject.deleteServiceType("4243234");
    }

}
