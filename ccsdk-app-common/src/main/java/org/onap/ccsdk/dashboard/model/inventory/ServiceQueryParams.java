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

public class ServiceQueryParams {

    private final String typeId;
    private final String vnfId;
    private final String vnfType;
    private final String vnfLocation;
    private final String componentType;
    private final Boolean shareable;
    private final String created;

    // Non-instantiable
    private ServiceQueryParams() {
        this.typeId = null;
        this.vnfId = null;
        this.vnfType = null;
        this.vnfLocation = null;
        this.componentType = null;
        this.shareable = null;
        this.created = null;
    }

    private ServiceQueryParams(String typeId, String vnfId, String vnfType, String vnfLocation,
        String componentType, Boolean shareable, String created) {
        this.typeId = typeId;
        this.vnfId = vnfId;
        this.vnfType = vnfType;
        this.vnfLocation = vnfLocation;
        this.componentType = componentType;
        this.shareable = shareable;
        this.created = created;
    }

    public static class Builder {
        private String typeId;
        private String vnfId;
        private String vnfType;
        private String vnfLocation;
        private String componentType;
        private Boolean shareable;
        private String created;

        public Builder typeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder vnfId(String vnfId) {
            this.vnfId = vnfId;
            return this;
        }

        public Builder vnfType(String vnfType) {
            this.vnfType = vnfType;
            return this;
        }

        public Builder vnfLocation(String vnfLocation) {
            this.vnfLocation = vnfLocation;
            return this;
        }

        public Builder componentType(String componentType) {
            this.componentType = componentType;
            return this;
        }

        public Builder shareable(Boolean shareable) {
            this.shareable = shareable;
            return this;
        }

        public Builder created(String created) {
            this.created = created;
            return this;
        }

        public ServiceQueryParams build() {
            return new ServiceQueryParams(typeId, vnfId, vnfType, vnfLocation, componentType,
                shareable, created);
        }
    }

    public String getTypeId() {
        return this.typeId;
    }

    public String getVnfId() {
        return this.vnfId;
    }

    public String getVnfType() {
        return this.vnfType;
    }

    public String getVnfLocation() {
        return this.vnfLocation;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public Boolean getShareable() {
        return this.shareable;
    }

    public String getCreated() {
        return this.created;
    }
}
