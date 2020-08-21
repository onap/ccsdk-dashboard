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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypeUploadRequest {

    /** Owner of the Service Type */
    public String owner;
    /** Name of the Service Type */
    public String typeName;
    /** Version number of the Service Type */
    public Integer typeVersion;
    /** String representation of a Cloudify blueprint with unbound variables */
    public String blueprintTemplate;
    /** Application controller name */
    public String application;
    /** onboarding component name */
    public String component;

    @JsonCreator
    public ServiceTypeUploadRequest(@JsonProperty("owner") String owner,
        @JsonProperty("typeName") String typeName, @JsonProperty("typeVersion") Integer typeVersion,
        @JsonProperty("blueprintTemplate") String blueprintTemplate,
        @JsonProperty("application") String application,
        @JsonProperty("component") String component) {
        this.owner = owner;
        this.typeName = typeName;
        this.typeVersion = typeVersion;
        this.blueprintTemplate = blueprintTemplate;
        this.application = application;
        this.component = component;
    }

    public static ServiceTypeUploadRequest from(ServiceType serviceType) {
        return new ServiceTypeUploadRequest(serviceType.getOwner(), serviceType.getTypeName(),
            serviceType.getTypeVersion(), serviceType.getBlueprintTemplate(),
            serviceType.getApplication(), serviceType.getComponent());
    }

    public String getBlueprintTemplate() {
        return this.blueprintTemplate;

    }
}
