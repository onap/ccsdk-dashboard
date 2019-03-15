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

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceRequest {

    /** ID of the associated service type */
    public String typeId;
    /** Id of the associated VNF that this service is monitoring */
    public String vnfId;
    /** The type of the associated VNF that this service is monitoring */
    public String vnfType;
    /** Location identifier of the associated VNF that this service is monitoring */
    public String vnfLocation;
    /** Reference to a Cloudify deployment */
    public String deploymentRef;
    /**
     * Collection of ServiceComponentRequest objects that this service is composed
     * of
     */
    public Collection<ServiceComponentRequest> components;

    @JsonCreator
    public ServiceRequest(@JsonProperty("typeId") String typeId, @JsonProperty("vnfId") String vnfId,
            @JsonProperty("vnfType") String vnfType, @JsonProperty("vnfLocation") String vnfLocation,
            @JsonProperty("deploymentRef") String deploymentRef,
            @JsonProperty("components") Collection<ServiceComponentRequest> components) {
        this.typeId = typeId;
        this.vnfId = vnfId;
        this.vnfType = vnfType;
        this.vnfLocation = vnfLocation;
        this.deploymentRef = deploymentRef;
        this.components = components;
    }

    public static ServiceRequest from(String typeId, Service service) {

        // Convert the Collection<ServiceComponent> in service to
        // Collection<ServiceComponentRequest> for serviceRequest
        final Collection<ServiceComponent> serviceComponents = service.getComponents();
        final Collection<ServiceComponentRequest> serviceComponentRequests = new ArrayList<ServiceComponentRequest>();

        for (ServiceComponent sc : serviceComponents) {
            serviceComponentRequests.add(ServiceComponentRequest.from(sc));
        }

        return new ServiceRequest(typeId, service.getVnfId(), service.getVnfType(), service.getVnfLocation(),
                service.getDeploymentRef(), serviceComponentRequests);
    }
}
