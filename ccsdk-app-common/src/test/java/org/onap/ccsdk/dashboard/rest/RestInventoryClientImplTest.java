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
import org.onap.ccsdk.dashboard.util.DashboardProperties;
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

    ServiceType bpItem = null;
    ServiceTypeList bpList = null;
    ServiceTypeList bpListNext = null;

    ServiceTypeRequest bpUploadItem = null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new Jdk8Module());
        getExpectedBueprints();
        getExpectedDeployments();
        PowerMockito.mockStatic(DashboardProperties.class);
        when(DashboardProperties.getControllerProperty("dev",
            DashboardProperties.CONTROLLER_SUBKEY_INVENTORY_URL)).thenReturn("https://invt.com");
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

        int totalCount = 2;
        deplList = new ServiceList(items, totalCount, paginationLinks);
        deplListNext = new ServiceList(items, totalCount, paginationLinks2);
    }

    public void getExpectedBueprints()
        throws JsonParseException, JsonMappingException, IOException {
        bpItem = new ServiceType.Builder("xyz1731", "xyz1731-helm-1906", 1906,
            "tosca_definitions_version: cloudify_dsl_1_3", "", "app1", "comp1").build();
        Collection<ServiceType> items = new ArrayList<ServiceType>();
        items.add(bpItem);

        String pageLinks =
            "{\"previousLink\":null,\"nextLink\":{\"rel\":\"next\",\"href\":\"https://invt.com:30123/dcae-services/?offset=25\"}}";
        String pageLinks2 = "{\"previousLink\":null,\"nextLink\":null}";
        ServiceTypeList.PaginationLinks paginationLinks =
            objectMapper.readValue(pageLinks, ServiceTypeList.PaginationLinks.class);
        ServiceTypeList.PaginationLinks paginationLinks2 =
            objectMapper.readValue(pageLinks2, ServiceTypeList.PaginationLinks.class);

        int totalCount = 2;
        bpList = new ServiceTypeList(items, totalCount, paginationLinks);
        bpListNext = new ServiceTypeList(items, totalCount, paginationLinks2);
    }

    @Test
    @Ignore
    public final void testInit() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testGetServiceTypes() {
        /*
         * String serviceTypeStr =
         * "{\"paginationLinks\":{\"previousLink\":null,\"nextLink\":null}},\"totalCount\":32,\"items\":[{\"owner\":\"xyz1731\",\"typeName\":\"xyz1731-helm-1906\",\"typeVersion\":1906,\"blueprintTemplate\":\"tosca_definitions_version: cloudify_dsl_1_3\\nimports:\\n  - http://www.getcloudify.org/spec/cloudify/4.2/types.yaml\\n  - http://dockercentral.it.att.com:8093/nexus/repository/rawcentral/com.att.dcae.controller/type_files/helm/3.0.1/helm-type.yaml\\ninputs:\\n  deployment-description:\\n    default: ''\\n  chart-name:\\n    default: ''\\n  tiller-server:\\n    default: ''\\n  tiller-server-port:\\n    default: ''\\n  namespace:\\n    default: ''\\n  chart-repo-url:\\n    default: http://32.68.14.161/charts\\n  chart-version :\\n    default: ''\\n  config-url:\\n    default: ''\\n  config-format:\\n    default: ''\\n  tls-enable:\\n    description: enable helm TSL\\n    type: boolean\\n    default: false\\n  config-dir:\\n    description: config dir\\n    type: string\\n    default: '/opt/manager/resources/'\\n  stable-repo-url:\\n    description: URL for stable repository\\n    type: string\\n    default: 'http://32.68.14.161/stable'\\nnode_templates:\\n  ecompcomponent:\\n    type: onap.nodes.component\\n    properties:\\n      tiller-server-ip: { get_input: tiller-server }\\n      tiller-server-port: { get_input: tiller-server-port }\\n      component-name: { get_input: chart-name }\\n      chart-repo-url: { concat: [ 'http://', { get_secret: controller_helm_user }, ':', { get_secret: controller_helm_password }, '@', { get_input: chart-repo-url } ] }\\n      chart-version: { get_input: chart-version }\\n      namespace: { get_input: namespace }\\n      config-url:  { concat: [ 'http://', { get_secret: controller_helm_user }, ':', { get_secret: controller_helm_password }, '@', { get_input: config-url } ] }\\n      config-format: { get_input: config-format}\\n      tls-enable: { get_input: tls-enable}\\n      ca: { get_secret: ca_value}\\n      cert: { get_secret: cert_value}\\n      key: { get_secret: key_value}\\n      config-dir: { get_input: config-dir}\\n      stable-repo-url: { concat: [ 'http://', { get_secret: controller_helm_user }, ':', { get_secret: controller_helm_password }, '@', { get_input: stable-repo-url } ] }\\noutputs:\\n  ecompcomponent-helm-output:\\n    description: helm value and helm history\\n    value:\\n      helm-value: { get_attribute: [ecompcomponent, current-helm-value] }\\n      helm-history: { get_attribute: [ecompcomponent, helm-history] }\",\"serviceIds\":[],\"vnfTypes\":[],\"serviceLocations\":[],\"asdcServiceId\":\"\",\"asdcResourceId\":\"\",\"asdcServiceURL\":null,\"application\":\"ECOMP\",\"component\":\"controller\",\"typeId\":\"72b8577a-5043-43ab-9aa5-6808b8f53967\",\"selfLink\":{\"rel\":\"self\",\"href\":\"https://ecompc-invt-dev-s5.ecomp.idns.cip.att.com:30123/dcae-service-types/72b8577a-5043-43ab-9aa5-6808b8f53967\"},\"created\":1551214264536,\"deactivated\":null}]"
         * ; ServiceTypeList sampleData = null; try { sampleData =
         * objectMapper.readValue(serviceTypeStr, ServiceTypeList.class); } catch
         * (Exception e) { }
         */
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> expectedResult = items.stream();

        ResponseEntity<ServiceTypeList> response =
            new ResponseEntity<ServiceTypeList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeList> response2 =
            new ResponseEntity<ServiceTypeList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeList>>any())).thenReturn(response)
                .thenReturn(response2);

        Stream<ServiceType> actualResult = subject.getServiceTypes();
        assertNotNull(actualResult);
        assertTrue(expectedResult.count() == actualResult.count());
    }

    @Test
    public final void testGetServiceTypesServiceTypeQueryParams() {
        ServiceTypeQueryParams qryParms = new ServiceTypeQueryParams.Builder()
            .asdcResourceId("asdcResourceId").asdcServiceId("asdcServiceId").onlyActive(true)
            .onlyLatest(false).serviceId("serviceId").serviceLocation("serviceLocation")
            .typeName("typeName").vnfType("vnfType").build();
        Collection<ServiceType> items = bpList.items;
        Stream<ServiceType> expectedResult = items.stream();

        ResponseEntity<ServiceTypeList> response =
            new ResponseEntity<ServiceTypeList>(bpList, HttpStatus.OK);

        ResponseEntity<ServiceTypeList> response2 =
            new ResponseEntity<ServiceTypeList>(bpListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ServiceTypeList>>any())).thenReturn(response)
                .thenReturn(response2);

        Stream<ServiceType> actualResult = subject.getServiceTypes(qryParms);
        assertNotNull(actualResult);
        assertTrue(expectedResult.count() == actualResult.count());
    }

    @Test
    public final void testGetServiceType() {
        Optional<ServiceType> expectedResult = Optional.of(bpItem);

        ResponseEntity<ServiceType> response =
            new ResponseEntity<ServiceType>(bpItem, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<ServiceType>>any()))
                .thenReturn(response);

        Optional<ServiceType> actualResult = subject.getServiceType("432432423");
        assertTrue(expectedResult.get().getTypeName().equals(actualResult.get().getTypeName()));
    }

    @Test
    public final void testAddServiceTypeServiceType() {

        when(mockRest.postForObject(Matchers.anyString(), Matchers.<HttpEntity<?>>any(),
            Matchers.<Class<ServiceType>>any())).thenReturn(bpItem);

        ServiceType actualResult = subject.addServiceType(bpItem);
        assertTrue(actualResult.getTypeName().contains("xyz"));
    }

    @Test
    public final void testAddServiceTypeServiceTypeRequest() {
        ServiceTypeRequest srvcReq = ServiceTypeRequest.from(bpItem);

        when(mockRest.postForObject(Matchers.anyString(), Matchers.<HttpEntity<?>>any(),
            Matchers.<Class<ServiceType>>any())).thenReturn(bpItem);

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

        subject.deleteServiceType("4243234");
    }

    @Test
    public final void testGetServices() {

        Collection<Service> items = deplList.items;
        Stream<Service> expectedResult = items.stream();

        ResponseEntity<ServiceList> response =
            new ResponseEntity<ServiceList>(deplList, HttpStatus.OK);

        ResponseEntity<ServiceList> response2 =
            new ResponseEntity<ServiceList>(deplListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<ServiceList>>any()))
                .thenReturn(response).thenReturn(response2);

        Stream<Service> actualResult = subject.getServices();
        assertNotNull(actualResult);
        assertTrue(expectedResult.count() == actualResult.count());

    }

    @Test
    public final void testGetServicesForType() {
        ResponseEntity<ServiceList> response =
            new ResponseEntity<ServiceList>(deplList, HttpStatus.OK);

        ResponseEntity<ServiceList> response2 =
            new ResponseEntity<ServiceList>(deplListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<ServiceList>>any()))
                .thenReturn(response).thenReturn(response2);
        ServiceQueryParams qryParms = new ServiceQueryParams.Builder().typeId("typeId").build();

        ServiceRefList actualResult = subject.getServicesForType(qryParms);
        assertTrue(actualResult.items.size() == 2);
        ServiceRef actualSrvcItem = (ServiceRef) actualResult.items.toArray()[0];
        assertTrue(actualSrvcItem.getServiceId().contains("dtiapi"));

    }

    @Test
    public final void testGetServicesServiceQueryParams() {
        ServiceQueryParams qryParms = new ServiceQueryParams.Builder().typeId("typeId")
            .vnfId("vnfId").vnfLocation("vnfLocation").vnfType("vnfType")
            .componentType("componentType").shareable(false).created("43443423").build();

        Collection<Service> items = deplList.items;
        Stream<Service> expectedResult = items.stream();

        ResponseEntity<ServiceList> response =
            new ResponseEntity<ServiceList>(deplList, HttpStatus.OK);

        ResponseEntity<ServiceList> response2 =
            new ResponseEntity<ServiceList>(deplListNext, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<ServiceList>>any()))
                .thenReturn(response).thenReturn(response2);

        Stream<Service> actualResult = subject.getServices(qryParms);
        assertTrue(expectedResult.count() == actualResult.count());
    }

    @Test
    public final void testGetService() {
        Optional<Service> expectedResult = Optional.of(deplItem);

        ResponseEntity<Service> response = new ResponseEntity<Service>(deplItem, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<Service>>any()))
                .thenReturn(response);

        Optional<Service> actualResult = subject.getService("432432423");
        assertTrue(
            expectedResult.get().getDeploymentRef().equals(actualResult.get().getDeploymentRef()));
    }

    @Test
    public final void testPutService() {

        ResponseEntity<Service> response = new ResponseEntity<Service>(deplItem, HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.PUT),
            Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<Service>>any()))
                .thenReturn(response);

        subject.putService("423423", deplItem);
    }

    @Test
    public final void testDeleteService() throws Exception {
        ResponseEntity<ApiResponseMessage> response =
            new ResponseEntity<ApiResponseMessage>(HttpStatus.OK);

        when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.DELETE),
            Matchers.<HttpEntity<?>>any(),
            Matchers.<ParameterizedTypeReference<ApiResponseMessage>>any())).thenReturn(response);

        subject.deleteService("4243234");
    }

}
