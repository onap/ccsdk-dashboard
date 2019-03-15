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

public class ServiceTypeQueryParams {

    private final String typeName;
    private final Boolean onlyLatest;
    private final Boolean onlyActive;
    private final String vnfType;
    private final String serviceId;
    private final String serviceLocation;
    private final String asdcServiceId;
    private final String asdcResourceId;
    private final String application;
    private final String component;

    // Non-instantiable
    private ServiceTypeQueryParams() {
        this.typeName = null;
        this.onlyLatest = null;
        this.onlyActive = null;
        this.vnfType = null;
        this.serviceId = null;
        this.serviceLocation = null;
        this.asdcServiceId = null;
        this.asdcResourceId = null;
        this.application = null;
        this.component = null;
    }

    private ServiceTypeQueryParams(String typeName, Boolean onlyLatest, Boolean onlyActive, String vnfType,
            String serviceId, String serviceLocation, String asdcServiceId, String asdcResourceId, String application,
            String component) {
        this.typeName = typeName;
        this.onlyLatest = onlyLatest;
        this.onlyActive = onlyActive;
        this.vnfType = vnfType;
        this.serviceId = serviceId;
        this.serviceLocation = serviceLocation;
        this.asdcServiceId = asdcServiceId;
        this.asdcResourceId = asdcResourceId;
        this.application = application;
        this.component = component;
    }

    public static class Builder {
        private String typeName;
        private Boolean onlyLatest;
        private Boolean onlyActive;
        private String vnfType;
        private String serviceId;
        private String serviceLocation;
        private String asdcServiceId;
        private String asdcResourceId;
        private String application;
        private String component;

        public Builder typeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public Builder onlyLatest(Boolean onlyLatest) {
            this.onlyLatest = onlyLatest;
            return this;
        }

        public Builder onlyActive(Boolean onlyActive) {
            this.onlyActive = onlyActive;
            return this;
        }

        public Builder vnfType(String vnfType) {
            this.vnfType = vnfType;
            return this;
        }

        public Builder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder serviceLocation(String serviceLocation) {
            this.serviceLocation = serviceLocation;
            return this;
        }

        public Builder asdcServiceId(String asdcServiceId) {
            this.asdcServiceId = asdcServiceId;
            return this;
        }

        public Builder asdcResourceId(String asdcResourceId) {
            this.asdcResourceId = asdcResourceId;
            return this;
        }

        public ServiceTypeQueryParams build() {
            return new ServiceTypeQueryParams(typeName, onlyLatest, onlyActive, vnfType, serviceId, serviceLocation,
                    asdcServiceId, asdcResourceId, application, component);
        }
    }

    public String getTypeName() {
        return this.typeName;
    }

    public Boolean getOnlyLatest() {
        return this.onlyLatest;
    }

    public Boolean getOnlyActive() {
        return this.onlyActive;
    }

    public String getVnfType() {
        return this.vnfType;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getServiceLocation() {
        return this.serviceLocation;
    }

    public String getAsdcServiceId() {
        return this.asdcServiceId;
    }

    public String getAsdcResourceId() {
        return this.asdcResourceId;
    }

    public String getApplication() {
        return this.application;
    }

    public String getComponent() {
        return this.component;
    }
}
