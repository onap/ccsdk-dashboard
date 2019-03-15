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

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeActiveException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.inventory.InventoryProperty;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;

/**
 * Defines the interface of the Inventory Client.
 */
public interface InventoryClient {

    /**
     * Gets a list of all DCAE Service Type objects.
     * 
     * @return Collection<ServiceType>
     */
    public Stream<ServiceType> getServiceTypes();

    /**
     * Gets a list of all DCAE Service Type objects that fall under a specified
     * filter.
     * 
     * @param serviceTypeQueryParams ServiceTypeQueryParams object containing query
     *                               parameters.
     * 
     * @return Collection<ServiceType>
     */
    public Stream<ServiceType> getServiceTypes(ServiceTypeQueryParams serviceTypeQueryParams);

    /**
     * Inserts a new DCAE Service Type, or updates an existing instance associated
     * with the name typeName. Updates are only allowed iff there are no running
     * DCAE services of the requested type.
     * 
     * @param serviceType Service Type to be uploaded.
     * 
     * @return ServiceType
     * 
     * @throws ServiceTypeActiveException if the service type exists and has active
     *                                    instances
     */
    public ServiceType addServiceType(ServiceType serviceType) throws ServiceTypeActiveException;

    /**
     * Inserts a new DCAE Service Type, or updates an existing instance associated
     * with the name typeName. Updates are only allowed iff there are no running
     * DCAE services of the requested type.
     * 
     * @param serviceType Service Type to be uploaded.
     * @return
     * 
     * @throws ServiceTypeActiveException if the service type exists and has active
     *                                    instances
     */
    public ServiceType addServiceType(ServiceTypeRequest serviceTypeRequest) throws ServiceTypeActiveException;

    /**
     * Gets a single DCAE Service Type object with the ID typeId.
     * 
     * @param typeId ID of the DCAE Service Type to be retrieved.
     * 
     * @return Optional<ServiceType>
     */
    public Optional<ServiceType> getServiceType(String typeId);

    /**
     * Deactivates an existing DCAE Service Type instance with the ID typeId.
     * 
     * @param typeId ID of the DCAE Service Type to be deactivated.
     * 
     * @exception ServiceTypeNotFoundException           Thrown if the DCAE Service
     *                                                   Type is not found.
     * 
     * @exception ServiceTypeAlreadyDeactivatedException Thrown if the DCAE Service
     *                                                   Type is already
     *                                                   deactivated.
     */
    public void deleteServiceType(String typeId)
            throws ServiceTypeNotFoundException, ServiceTypeAlreadyDeactivatedException;

    /**
     * Gets a list of all DCAE Service objects.
     * 
     * @return Collection<Service>
     */
    public Stream<Service> getServices();

    /**
     * Gets a list of all DCAE Service objects that fall under a specified filter.
     * 
     * @param serviceQueryParams ServiceQueryParams object containing query
     *                           parameters.
     * 
     * @return Collection<Service>
     */
    public Stream<Service> getServices(ServiceQueryParams serviceQueryParams);

    /**
     * Gets a list of all DCAE Service References that match a service type filter.
     * 
     * @param serviceQueryParams ServiceQueryParams object containing query
     *                           parameters.
     * 
     * @return ServiceRefList
     */
    public ServiceRefList getServicesForType(ServiceQueryParams serviceQueryParams);

    /**
     * Gets a set of properties on Service objects that match the provided
     * propertyName
     * 
     * @param propertyName Property to find unique values. Restricted to type,
     *                     vnfType, vnfLocation.
     * 
     * @return Set<InventoryProperty>
     */

    public Set<InventoryProperty> getPropertiesOfServices(String propertyName);

    /**
     * Gets a single DCAE Service object corresponding to the specified serviceId.
     * 
     * @param serviceId Service ID of the DCAE Service to be retrieved.
     * 
     * @return Service
     */

    public Optional<Service> getService(String serviceId);

    /**
     * Puts a new DCAE Service with the specified serviceId, or updates an existing
     * DCAE Service corresponding to the specified serviceId.
     * 
     * @param typeId  Type ID of the associated DCAE Service Type
     * 
     * @param service DCAE Service to be uploaded.
     */
    public void putService(String typeId, Service service);

    /**
     * Deletes an existing DCAE Service object corresponding to the specified
     * serviceId.
     * 
     * @param serviceId Service ID of the DCAE Service to be deleted.
     * 
     * @exception ServiceNotFoundException           Thrown if the DCAE Service is
     *                                               not found.
     * 
     * @exception ServiceAlreadyDeactivatedException Thrown if the DCAE Service is
     *                                               already deactivated.
     * 
     */

    public void deleteService(String serviceId) throws ServiceNotFoundException, ServiceAlreadyDeactivatedException;

}
