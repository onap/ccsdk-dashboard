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

import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeActiveException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.inventory.InventoryProperty;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class RestInventoryClientMockImpl implements InventoryClient {

    private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(RestInventoryClientMockImpl.class);
    /**
     * For mock outputs
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestInventoryClientMockImpl() {
        // Do not serialize null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Register Jdk8Module() for Stream and Optional types
        objectMapper.registerModule(new Jdk8Module());
    }

    private String getMockDataContent(final String path) {
        String result = null;
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null)
                throw new Exception("Failed to find resource at path " + path);
            Scanner scanner = new Scanner(is, "UTF-8");
            result = scanner.useDelimiter("\\A").next();
            scanner.close();
            is.close();
        } catch (Exception ex) {
            logger.error("getMockDataContent failed", ex);
            throw new RuntimeException(ex);
        }
        return result;
    }

    /**
     * Creates an input stream using the specified path and requests the mapper
     * create an object of the specified type.
     * 
     * @param modelClass Model class
     * @param path       Path to classpath resource
     * @return Instance of modelClass
     */
    private ECTransportModel getMockData(final Class<? extends ECTransportModel> modelClass, final String path) {
        ECTransportModel result = null;
        String json = getMockDataContent(path);
        try {
            result = (ECTransportModel) objectMapper.readValue(json, modelClass);
        } catch (Exception ex) {
            logger.error("getMockData failed", ex);
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public Stream<ServiceType> getServiceTypes() {
        ServiceTypeList mockData = (ServiceTypeList) getMockData(ServiceTypeList.class, "/serviceTypesList.json");
        Collection<ServiceType> collection = mockData.items;

        return collection.stream();
    }

    @Override
    public Stream<ServiceType> getServiceTypes(ServiceTypeQueryParams serviceTypeQueryParams) {
        ServiceTypeList mockData = (ServiceTypeList) getMockData(ServiceTypeList.class, "/serviceTypesList.json");
        Collection<ServiceType> collection = mockData.items;

        return collection.stream();
    }

    @Override
    public ServiceRefList getServicesForType(ServiceQueryParams serviceQueryParams) {
        return null;
    }

    @Override
    public ServiceType addServiceType(ServiceType serviceType) throws ServiceTypeActiveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceType addServiceType(ServiceTypeRequest serviceTypeRequest) throws ServiceTypeActiveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ServiceType> getServiceType(String typeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteServiceType(String typeId)
            throws ServiceTypeNotFoundException, ServiceTypeAlreadyDeactivatedException {
        // TODO Auto-generated method stub

    }

    @Override
    public Stream<Service> getServices() {
        ServiceList mockData = (ServiceList) getMockData(ServiceList.class, "/serviceList.json");
        Collection<Service> collection = mockData.items;

        return collection.stream();
    }

    @Override
    public Stream<Service> getServices(ServiceQueryParams serviceQueryParams) {
        ServiceList mockData = (ServiceList) getMockData(ServiceList.class, "/serviceList.json");
        Collection<Service> collection = mockData.items;

        return collection.stream();
    }

    @Override
    public Set<InventoryProperty> getPropertiesOfServices(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Service> getService(String serviceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putService(String typeId, Service service) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteService(String serviceId) throws ServiceNotFoundException, ServiceAlreadyDeactivatedException {
        // TODO Auto-generated method stub

    }
}
