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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceRef {

    /** Service ID of the Service */
    public final String id;
    /** Creation date of the Service */
    public final String created_at;
    /** Last modified date of the Service */
    public final String updated_at;
    public final String tenant_name = "";
    
    @JsonCreator
    public ServiceRef(
        @JsonProperty("id") String serviceId, 
        @JsonProperty("created_at") String created,
        @JsonProperty("updated_at") String modified
        ) {
        this.id = serviceId;
        this.created_at = created;
        this.updated_at = modified;

    }
/*
    public String getServiceId() {
        return serviceId;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }
*/
    /*
     * private ServiceRef ( String serviceId, String created, String modified) {
     * this.serviceId = serviceId; this.created = created; this.modified = modified;
     * }
     */

}
