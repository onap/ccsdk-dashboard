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
 *******************************************************************************/

package org.onap.ccsdk.dashboard.rest;

import java.util.Optional;
import java.util.stream.Stream;

import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeActiveException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Defines the interface of the Inventory Client.
 */
public interface InventoryClient {

    /**
     * Run inventory service health check
     * 
     */
    public String checkHealth();

    /**
     * Get and store in cache, list of all DCAE Service Type objects.
     * 
     * @return void
     */
    public void cacheServiceTypes();

    /**
     * Gets a list of all DCAE Service Type objects.
     * 
     * @return Collection<ServiceType>
     */
    public Stream<ServiceTypeSummary> getServiceTypes() throws Exception;

    /**
     * Gets a list of all DCAE Service Type objects that fall under a specified
     * filter.
     * 
     * @param serviceTypeQueryParams ServiceTypeQueryParams object containing query
     * parameters.
     * 
     * @return Collection<ServiceType>
     */
    public Stream<ServiceTypeSummary> getServiceTypes(
        ServiceTypeQueryParams serviceTypeQueryParams);

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
     * instances
     */
    public ServiceType addServiceType(ServiceType serviceType)
        throws Exception;

    /**
     * Inserts a new DCAE Service Type, or updates an existing instance associated
     * with the name typeName. Updates are only allowed iff there are no running
     * DCAE services of the requested type.
     * 
     * @param serviceType Service Type to be uploaded.
     * @return
     * 
     * @throws ServiceTypeActiveException if the service type exists and has active
     * instances
     */
    public ServiceType addServiceType(ServiceTypeRequest serviceTypeRequest)
        throws Exception;

    /**
     * Gets a single DCAE Service Type object with the ID typeId.
     * 
     * @param typeId ID of the DCAE Service Type to be retrieved.
     * 
     * @return Optional<ServiceType>
     */
    public Optional<ServiceType> getServiceType(String typeId)
        throws Exception;

    /**
     * Deactivates an existing DCAE Service Type instance with the ID typeId.
     * 
     * @param typeId ID of the DCAE Service Type to be deactivated.
     * 
     * @exception ServiceTypeNotFoundException Thrown if the DCAE Service
     * Type is not found.
     * 
     * @exception ServiceTypeAlreadyDeactivatedException Thrown if the DCAE Service
     * Type is already
     * deactivated.
     */
    public void deleteServiceType(String typeId)
        throws Exception;

    /**
     * Gets a list of all DCAE Service References that match a service type filter.
     * 
     * @param serviceQueryParams ServiceQueryParams object containing query
     * parameters.
     * 
     * @return ServiceRefList
     */
    public ServiceRefList getServicesForType(ServiceQueryParams serviceQueryParams);

    public String getBaseUrl();

}
