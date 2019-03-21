/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.ccsdk.dashboard.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.inventory.ApiResponseMessage;
import org.onap.ccsdk.dashboard.model.inventory.InventoryProperty;
import org.onap.ccsdk.dashboard.model.inventory.Link;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceGroupByResults;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@org.springframework.stereotype.Service
public class RestInventoryClientImpl extends RestClientBase implements InventoryClient {

    private String baseUrl;
    public static final String SERVICE_TYPES = "dcae-service-types";
    public static final String SERVICES = "dcae-services";
    public static final String SERVICES_GROUPBY = "dcae-services-groupby";

    @PostConstruct
    public void init() {
        String webapiUrl = DashboardProperties.getControllerProperty("dev",
            DashboardProperties.CONTROLLER_SUBKEY_INVENTORY_URL);
        if (webapiUrl == null)
            throw new IllegalArgumentException("Null URL not permitted");
        URL url = null;
        String urlScheme = "http";
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Failed to parse URL", ex);
        }
        urlScheme = webapiUrl.split(":")[0];
        if (restTemplate == null) {
            createRestTemplate(url, null, null, urlScheme);
        }

    }

    public Stream<ServiceType> getServiceTypes() {
        String url = buildUrl(new String[] { baseUrl, SERVICE_TYPES }, null);
        ResponseEntity<ServiceTypeList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceTypeList>() {
                });
        Collection<ServiceType> collection = response.getBody().items;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<ServiceTypeList>() {
                    });
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }

        return collection.stream();
    }

    public Stream<ServiceType> getServiceTypes(ServiceTypeQueryParams serviceTypeQueryParams) {

        // Only utilize the parameters that aren't null
        HashMap<String, String> map = new HashMap<>();
        if (serviceTypeQueryParams.getTypeName() != null) {
            map.put("typeName", serviceTypeQueryParams.getTypeName());
        }
        if (serviceTypeQueryParams.getOnlyLatest() != null) {
            map.put("onlyLatest", Boolean.toString(serviceTypeQueryParams.getOnlyLatest()));
        }
        if (serviceTypeQueryParams.getOnlyActive() != null) {
            map.put("onlyActive", Boolean.toString(serviceTypeQueryParams.getOnlyActive()));
        }
        if (serviceTypeQueryParams.getVnfType() != null) {
            map.put("vnfType", serviceTypeQueryParams.getVnfType());
        }
        if (serviceTypeQueryParams.getServiceId() != null) {
            map.put("serviceId", serviceTypeQueryParams.getServiceId());
        }
        if (serviceTypeQueryParams.getServiceLocation() != null) {
            map.put("serviceLocation", serviceTypeQueryParams.getServiceLocation());
        }
        if (serviceTypeQueryParams.getAsdcServiceId() != null) {
            map.put("asdcServiceId", serviceTypeQueryParams.getAsdcServiceId());
        }
        if (serviceTypeQueryParams.getAsdcResourceId() != null) {
            map.put("asdcResourceId", serviceTypeQueryParams.getAsdcResourceId());
        }
        ArrayList<String> params = new ArrayList<>();
        for (Entry<String, String> ent : map.entrySet()) {
            params.add(ent.getKey());
            params.add(ent.getValue());
        }

        String url = buildUrl(new String[] { baseUrl, SERVICE_TYPES }, params.toArray(new String[params.size()]));
        ResponseEntity<ServiceTypeList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceTypeList>() {
                });
        Collection<ServiceType> collection = response.getBody().items;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<ServiceTypeList>() {
                    });
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }

        return collection.stream();
    }

    public ServiceType addServiceType(ServiceType serviceType) {
        String url = buildUrl(new String[] { baseUrl, SERVICE_TYPES }, null);

        // Take the ServiceType object and create a ServiceTypeRequest from it
        ServiceTypeRequest serviceTypeRequest = ServiceTypeRequest.from(serviceType);

        return restTemplate.postForObject(url, serviceTypeRequest, ServiceType.class);
    }

    public ServiceType addServiceType(ServiceTypeRequest serviceTypeRequest) {
        String url = buildUrl(new String[] { baseUrl, SERVICE_TYPES }, null);

        return restTemplate.postForObject(url, serviceTypeRequest, ServiceType.class);
    }

    public Optional<ServiceType> getServiceType(String typeId) {
        String url = buildUrl(new String[] { baseUrl, SERVICE_TYPES, typeId }, null);
        ResponseEntity<ServiceType> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceType>() {
                });
        return Optional.ofNullable(response.getBody());
    }

    public void deleteServiceType(String typeId)
            throws ServiceTypeNotFoundException, ServiceTypeAlreadyDeactivatedException {
        String url = buildUrl(new String[] { baseUrl, SERVICE_TYPES, typeId }, null);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, null, new ParameterizedTypeReference<ApiResponseMessage>() {
            });
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 410) {
                throw new ServiceTypeAlreadyDeactivatedException(e.getMessage());
            } else if (e.getStatusCode().value() == 404) {
                throw new ServiceTypeNotFoundException(e.getMessage());
            }
        }
    }

    public Stream<Service> getServices() {
        String url = buildUrl(new String[] { baseUrl, SERVICES }, null);
        ResponseEntity<ServiceList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceList>() {
                });
        Collection<Service> collection = response.getBody().items;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ServiceList>() {
            });
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }

        return collection.stream();
    }

    public ServiceRefList getServicesForType(ServiceQueryParams serviceQueryParams) {

        // Only utilize the typeId
        HashMap<String, String> map = new HashMap<>();
        if (serviceQueryParams.getTypeId() != null) {
            map.put("typeId", serviceQueryParams.getTypeId());
        }
        ArrayList<String> params = new ArrayList<>();
        for (Entry<String, String> ent : map.entrySet()) {
            params.add(ent.getKey());
            params.add(ent.getValue());
        }

        String url = buildUrl(new String[] { baseUrl, SERVICES }, params.toArray(new String[params.size()]));
        ResponseEntity<ServiceList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceList>() {
                });
        Collection<Service> collection = response.getBody().items;
        int itemCnt = response.getBody().totalCount;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ServiceList>() {
            });
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }

        List<ServiceRef> srvcRefList = collection.stream().map(e -> e.createServiceRef()).collect(Collectors.toList());

        return new ServiceRefList(srvcRefList, itemCnt);
    }

    public Stream<Service> getServices(ServiceQueryParams serviceQueryParams) {

        // Only utilize the parameters that aren't null
        HashMap<String, String> map = new HashMap<>();
        if (serviceQueryParams.getTypeId() != null) {
            map.put("typeId", serviceQueryParams.getTypeId());
        }
        if (serviceQueryParams.getVnfId() != null) {
            map.put("vnfId", serviceQueryParams.getVnfId());
        }
        if (serviceQueryParams.getVnfType() != null) {
            map.put("vnfType", serviceQueryParams.getVnfType());
        }
        if (serviceQueryParams.getVnfLocation() != null) {
            map.put("vnfLocation", serviceQueryParams.getVnfLocation());
        }
        if (serviceQueryParams.getComponentType() != null) {
            map.put("componentType", serviceQueryParams.getComponentType());
        }
        if (serviceQueryParams.getShareable() != null) {
            map.put("shareable", Boolean.toString(serviceQueryParams.getShareable()));
        }
        if (serviceQueryParams.getCreated() != null) {
            map.put("created", serviceQueryParams.getCreated());
        }
        ArrayList<String> params = new ArrayList<>();
        for (Entry<String, String> ent : map.entrySet()) {
            params.add(ent.getKey());
            params.add(ent.getValue());
        }

        String url = buildUrl(new String[] { baseUrl, SERVICES }, params.toArray(new String[params.size()]));
        ResponseEntity<ServiceList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceList>() {
                });
        Collection<Service> collection = response.getBody().items;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<ServiceList>() {
            });
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }

        return collection.stream();
    }

    /*
     * public Set<InventoryProperty> getPropertiesOfServices(String propertyName) {
     * String url = buildUrl(new String[] {baseUrl, SERVICES_GROUPBY, propertyName},
     * null); ResponseEntity<ServiceGroupByResults> response =
     * restTemplate.exchange(url, HttpMethod.GET, null, new
     * ParameterizedTypeReference<ServiceGroupByResults>() { }); return
     * response.getBody().propertyValues; }
     */
    public Optional<Service> getService(String serviceId) {
        String url = buildUrl(new String[] { baseUrl, SERVICES, serviceId }, null);
        ResponseEntity<Service> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Service>() {
                });
        return Optional.ofNullable(response.getBody());
    }

    public void putService(String typeId, Service service) {
        String url = buildUrl(new String[] { baseUrl, SERVICES, service.getServiceId() }, null);

        ServiceRequest serviceRequest = ServiceRequest.from(typeId, service);

        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<ServiceRequest>(serviceRequest),
                new ParameterizedTypeReference<Service>() {
                });
    }

    public void deleteService(String serviceId) throws ServiceNotFoundException, ServiceAlreadyDeactivatedException {
        String url = buildUrl(new String[] { baseUrl, SERVICES, serviceId }, null);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, null, new ParameterizedTypeReference<ApiResponseMessage>() {
            });
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 410) {
                throw new ServiceAlreadyDeactivatedException(e.getMessage());
            } else if (e.getStatusCode().value() == 404) {
                throw new ServiceNotFoundException(e.getMessage());
            }
        }
    }
}
