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
package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypeRequest {

    /** Owner of the Service Type */
    public String owner;
    /** Name of the Service Type */
    public String typeName;
    /** Version number of the Service Type */
    public Integer typeVersion;
    /** String representation of a Cloudify blueprint with unbound variables */
    public String blueprintTemplate;
    /** controller application name */
    public String application;
    /** onboarding component name */
    public String component;
    /**
     * Collection of service ids used to associate with the Service Type. Service
     * Types with this property as null or empty means they apply for every service
     * id.
     */
    public Collection<String> serviceIds;
    /** Collection of vnfTypes associated with the Service Type */
    public Collection<String> vnfTypes;
    /**
     * Collection of service locations that are used to associate with the Service
     * Type. Service Types with this property as null or empty means they apply for
     * every service location.
     */
    public Collection<String> serviceLocations;
    /**
     * Id of the service this Service Type is associated with. Value source is from
     * ASDC's notification event's field 'serviceInvariantUUID'."
     */
    public Optional<String> asdcServiceId;
    /**
     * Id of the vf/vnf instance this Service Type is associated with. Value source
     * is from ASDC's notification event's field 'resourceInvariantUUID'."
     */
    public Optional<String> asdcResourceId;
    /** URL to the ASDC Service Model */
    public Optional<String> asdcServiceURL;

    @JsonCreator
    public ServiceTypeRequest(@JsonProperty("owner") String owner, @JsonProperty("typeName") String typeName,
            @JsonProperty("typeVersion") Integer typeVersion,
            @JsonProperty("blueprintTemplate") String blueprintTemplate,
            @JsonProperty("application") String application, @JsonProperty("component") String component,
            @JsonProperty("serviceIds") Collection<String> serviceIds,
            @JsonProperty("vnfTypes") Collection<String> vnfTypes,
            @JsonProperty("serviceLocations") Collection<String> serviceLocations,
            @JsonProperty("asdcServiceId") String asdcServiceId, @JsonProperty("asdcResourceId") String asdcResourceId,
            @JsonProperty("asdcServiceURL") String asdcServiceURL) {
        this(owner, typeName, typeVersion, blueprintTemplate, application, component, serviceIds, vnfTypes,
                serviceLocations, Optional.ofNullable(asdcServiceId), Optional.ofNullable(asdcResourceId),
                Optional.ofNullable(asdcServiceURL));
    }

    public ServiceTypeRequest(String owner, String typeName, Integer typeVersion, String blueprintTemplate,
            String application, String component, Collection<String> serviceIds, Collection<String> vnfTypes,
            Collection<String> serviceLocations, Optional<String> asdcServiceId, Optional<String> asdcResourceId,
            Optional<String> asdcServiceURL) {
        this.owner = owner;
        this.typeName = typeName;
        this.typeVersion = typeVersion;
        this.blueprintTemplate = blueprintTemplate;
        this.application = application;
        this.component = component;
        this.serviceIds = serviceIds;
        this.vnfTypes = vnfTypes;
        this.serviceLocations = serviceLocations;
        this.asdcServiceId = asdcServiceId;
        this.asdcResourceId = asdcResourceId;
        this.asdcServiceURL = asdcServiceURL;
    }

    public static ServiceTypeRequest from(ServiceType serviceType) {
        return new ServiceTypeRequest(serviceType.getOwner(), serviceType.getTypeName(), serviceType.getTypeVersion(),
                serviceType.getBlueprintTemplate(), serviceType.getApplication(), serviceType.getComponent(),
                serviceType.getServiceIds(), serviceType.getVnfTypes(), serviceType.getServiceLocations(),
                serviceType.getAsdcServiceId(), serviceType.getAsdcResourceId(), serviceType.getAsdcServiceURL());
    }

    public String getBlueprintTemplate() {
        return this.blueprintTemplate;
    }
}
