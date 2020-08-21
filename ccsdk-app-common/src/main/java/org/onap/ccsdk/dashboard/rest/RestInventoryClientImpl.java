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

package org.onap.ccsdk.dashboard.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.onap.ccsdk.dashboard.exceptions.DashboardControllerException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.inventory.ApiResponseMessage;
import org.onap.ccsdk.dashboard.model.inventory.Link;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummaryList;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@org.springframework.stereotype.Service
@Component
public class RestInventoryClientImpl extends RestClientBase implements InventoryClient {
    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(RestInventoryClientImpl.class);

    private String baseUrl;
    public static final String SERVICE_TYPES = "dcae-service-types";
    public static final String SERVICES = "dcae-services";
    public static final String SERVICES_GROUPBY = "dcae-services-groupby";
    public static final String HEALTH_CHECK = "healthcheck";

    /**
     * For caching data
     */
    private AbstractCacheManager cacheManager;

    @PostConstruct
    public void init() throws DashboardControllerException {
        String webapiUrl = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_INVENTORY_URL);
        if (webapiUrl == null)
            throw new IllegalArgumentException("Null URL not permitted");
        URL url = null;
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new DashboardControllerException("Failed to parse URL", ex);
        }
        String urlScheme = webapiUrl.split(":")[0];
        if (restTemplate == null) {
            createRestTemplate(url, null, null, urlScheme);
        }
    }

    public String checkHealth() {
        String url = buildUrl(new String[] {baseUrl, HEALTH_CHECK}, null);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<String>() {});
        return response.getBody();
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 30000)
    public void cacheServiceTypes() {
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheServiceTypes begin");
        String url =
            buildUrl(new String[] {baseUrl, SERVICE_TYPES}, new String[] {"onlyLatest", "false"});
        ResponseEntity<ServiceTypeSummaryList> response = restTemplate.exchange(url, HttpMethod.GET,
            null, new ParameterizedTypeReference<ServiceTypeSummaryList>() {});
        Collection<ServiceTypeSummary> collection = response.getBody().items;
        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceTypeSummaryList>() {});
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }
        ReadWriteLock lock = new ReentrantReadWriteLock();
        // put into cache
        lock.writeLock().lock();
        getCacheManager().putObject(SERVICE_TYPES, collection);
        lock.writeLock().unlock();
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheServiceTypes end");
    }

    public Stream<ServiceTypeSummary> getServiceTypes() throws Exception {
        String url =
            buildUrl(new String[] {baseUrl, SERVICE_TYPES}, new String[] {"onlyLatest", "false"});
        ResponseEntity<ServiceTypeSummaryList> response = restTemplate.exchange(url, HttpMethod.GET,
            null, new ParameterizedTypeReference<ServiceTypeSummaryList>() {});
        Collection<ServiceTypeSummary> collection = response.getBody().items;
        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceTypeSummaryList>() {});
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }
        ReadWriteLock lock = new ReentrantReadWriteLock();
        // put into cache
        lock.writeLock().lock();
        getCacheManager().putObject(SERVICE_TYPES, collection);
        lock.writeLock().unlock();
        return collection.stream();
    }

    @Autowired
    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public AbstractCacheManager getCacheManager() {
        return cacheManager;
    }

    public Stream<ServiceTypeSummary> getServiceTypes(
        ServiceTypeQueryParams serviceTypeQueryParams) {

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
        if (serviceTypeQueryParams.getApplication() != null) {
            map.put("application", serviceTypeQueryParams.getApplication());
        }
        if (serviceTypeQueryParams.getComponent() != null) {
            map.put("component", serviceTypeQueryParams.getComponent());
        }
        ArrayList<String> params = new ArrayList<>();
        for (Entry<String, String> ent : map.entrySet()) {
            params.add(ent.getKey());
            params.add(ent.getValue());
        }

        String url = buildUrl(new String[] {baseUrl, SERVICE_TYPES},
            params.toArray(new String[params.size()]));
        ResponseEntity<ServiceTypeSummaryList> response = restTemplate.exchange(url, HttpMethod.GET,
            null, new ParameterizedTypeReference<ServiceTypeSummaryList>() {});
        Collection<ServiceTypeSummary> collection = response.getBody().items;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceTypeSummaryList>() {});
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }
        return collection.stream();
    }

    public ServiceType addServiceType(ServiceType serviceType)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, SERVICE_TYPES}, null);

        // Take the ServiceType object and create a ServiceTypeRequest from it
        ServiceTypeRequest serviceTypeRequest = ServiceTypeRequest.from(serviceType);
        ServiceType upldBp = restTemplate.postForObject(url, serviceTypeRequest, ServiceType.class);
        List<ServiceTypeSummary> itemList = this.getServiceTypes().collect(Collectors.toList());
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        getCacheManager().removeObject(SERVICE_TYPES);
        // put updated collection back into cache
        getCacheManager().putObject(SERVICE_TYPES, itemList);
        lock.writeLock().unlock();
        return upldBp;
    }

    @SuppressWarnings("unchecked")
    public ServiceType addServiceType(ServiceTypeRequest serviceTypeRequest)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, SERVICE_TYPES}, null);
        ServiceType uplBp = restTemplate.postForObject(url, serviceTypeRequest, ServiceType.class);
        // update application cache with new record to refresh screen immediately
        // query inventory for the newly uploaded entry,
        // using query params (typeName, owner, app, component)
        List<ServiceTypeSummary> itemList = this.getServiceTypes().collect(Collectors.toList());
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        // put updated collection back into cache
        getCacheManager().putObject(SERVICE_TYPES, itemList);
        lock.writeLock().unlock();
        return uplBp;
    }

    public Optional<ServiceType> getServiceType(String typeId)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, SERVICE_TYPES, typeId}, null);
        ResponseEntity<ServiceType> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<ServiceType>() {});
        return Optional.ofNullable(response.getBody());
    }

    @SuppressWarnings("unchecked")
    public void deleteServiceType(String typeId)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, SERVICE_TYPES, typeId}, null);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            restTemplate.exchange(url,
                HttpMethod.DELETE, null, new ParameterizedTypeReference<ApiResponseMessage>() {});
            // update the application cache
            lock.readLock().lock();
            List<ServiceTypeSummary> itemList =
                (List<ServiceTypeSummary>) getCacheManager().getObject(SERVICE_TYPES);
            lock.readLock().unlock();
            if (itemList == null) {
                itemList = getServiceTypes().collect(Collectors.toList());
            }
            Predicate<ServiceTypeSummary> typeIdFilter =
                p -> p.getTypeId().isPresent() && !p.getTypeId().get().equals(typeId);
            itemList = (List<ServiceTypeSummary>) itemList.stream().filter(typeIdFilter)
                .collect(Collectors.toList());
            lock.writeLock().lock();
            getCacheManager().removeObject(SERVICE_TYPES);
            // put updated collection back into cache
            getCacheManager().putObject(SERVICE_TYPES, itemList);
            lock.writeLock().unlock();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 410) {
                throw new ServiceTypeAlreadyDeactivatedException(e.getMessage());
            } else if (e.getStatusCode().value() == 404) {
                throw new ServiceTypeNotFoundException(e.getMessage());
            }
        }
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
        String url =
            buildUrl(new String[] {baseUrl, SERVICES}, params.toArray(new String[params.size()]));
        ResponseEntity<ServiceList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<ServiceList>() {});
        Collection<Service> collection = response.getBody().items;
        int itemCnt = response.getBody().totalCount;

        // Continue retrieving items on the next page if they exist
        Link nextLink = response.getBody().paginationLinks.nextLink;
        while (nextLink != null) {
            url = response.getBody().paginationLinks.nextLink.href;
            response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ServiceList>() {});
            collection.addAll(response.getBody().items);
            nextLink = response.getBody().paginationLinks.nextLink;
        }
        List<ServiceRef> srvcRefList =
            collection.stream().map(e -> e.createServiceRef()).collect(Collectors.toList());

        return new ServiceRefList(srvcRefList, itemCnt);
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }
}
