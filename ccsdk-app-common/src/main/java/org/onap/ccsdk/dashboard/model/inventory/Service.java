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
package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Service {

    /** Service ID of the Service */
    private final String serviceId;
    /** Link to the Service */
    private final Link selfLink;
    /** Creation date of the Service */
    private final String created;
    /** Last modified date of the Service */
    private final String modified;
    /** Link to the Service Type */
    private final Link typeLink;
    /** vnfId of the Service */
    private final String vnfId;
    /** Link to the vnf of the Service */
    private final Link vnfLink;
    /** vnfType of the Service */
    private final String vnfType;
    /** vnfLocation of the Service */
    private final String vnfLocation;
    /** Reference to a Cloudify deployment */
    private final String deploymentRef;
    /** Collection of ServiceComponent */
    private final Collection<ServiceComponent> components;
    /** internal role based setting */
    private Optional<Boolean> canDeploy;
    /** tenant name for this service */
    private String tenant;
    /** install execution workflow status */
    private String installStatus;
    /** true if helm plugin is used */
    public Boolean isHelm;
    /** true if helm status is enabled */
    public Boolean helmStatus;
    /** Consul service health status */
    private String healthStatus;

    @JsonCreator
    public Service(@JsonProperty("serviceId") String serviceId,
        @JsonProperty("selfLink") Link selfLink, @JsonProperty("created") String created,
        @JsonProperty("modified") String modified, @JsonProperty("typeLink") Link typeLink,
        @JsonProperty("vnfId") String vnfId, @JsonProperty("vnfLink") Link vnfLink,
        @JsonProperty("vnfType") String vnfType, @JsonProperty("vnfLocation") String vnfLocation,
        @JsonProperty("deploymentRef") String deploymentRef,
        @JsonProperty("components") Collection<ServiceComponent> components) {
        this.serviceId = serviceId;
        this.selfLink = selfLink;
        this.created = created;
        this.modified = modified;
        this.typeLink = typeLink;
        this.vnfId = vnfId;
        this.vnfLink = vnfLink;
        this.vnfType = vnfType;
        this.vnfLocation = vnfLocation;
        this.deploymentRef = deploymentRef;
        this.components = components;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Link getSelfLink() {
        return selfLink;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public Link getTypeLink() {
        return typeLink;
    }

    public String getVnfId() {
        return vnfId;
    }

    public Link getVnfLink() {
        return vnfLink;
    }

    public String getVnfType() {
        return vnfType;
    }

    public String getVnfLocation() {
        return vnfLocation;
    }

    public String getDeploymentRef() {
        return deploymentRef;
    }

    public Collection<ServiceComponent> getComponents() {
        return components;
    }

    // Used for back end search, only searches the fields displayed in the front
    // end.
    public boolean contains(String searchString) {
        if (StringUtils.containsIgnoreCase(this.getDeploymentRef(), searchString)
            || StringUtils.containsIgnoreCase(this.getServiceId(), searchString)
            || StringUtils.containsIgnoreCase(this.getCreated(), searchString)
            || StringUtils.containsIgnoreCase(this.getModified(), searchString)
            || StringUtils.containsIgnoreCase(this.getTenant(), searchString)) {
            return true;
        }
        return false;
    }

    public Optional<Boolean> getCanDeploy() {
        return canDeploy;
    }

    public void setCanDeploy(Optional<Boolean> canDeploy) {
        this.canDeploy = canDeploy;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public ServiceRef createServiceRef() {
        return new ServiceRef(serviceId, created, modified);
    }
    /*
     * public static class ServiceRefBuilder { private String serviceId; private
     * String created; private String modified;
     * 
     * public ServiceRefBuilder mapFromService(Service srvc) { this.serviceId =
     * srvc.getServiceId(); this.created = srvc.getCreated(); this.modified =
     * srvc.getModified(); return this; }
     * 
     * public ServiceRef build() { return new ServiceRef(serviceId, created,
     * modified); } }
     */

    public String getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(String installStatus) {
        this.installStatus = installStatus;
    }

    public Boolean getIsHelm() {
        return isHelm;
    }

    public void setIsHelm(Boolean isHelm) {
        this.isHelm = isHelm;
    }

    public Boolean getHelmStatus() {
        return helmStatus;
    }

    public void setHelmStatus(Boolean helmStatus) {
        this.helmStatus = helmStatus;
    }

    public String getHealthStatus() {
        return this.healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
}
