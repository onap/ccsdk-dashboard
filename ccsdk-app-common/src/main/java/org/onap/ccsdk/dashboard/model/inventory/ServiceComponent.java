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

public class ServiceComponent {

    /** Component ID of the Service Component */
    public String componentId;
    /** Link to the Service Component */
    public Link componentLink;
    /** Creation date of the Service Component */
    public String created;
    /** Last modified date of the Service Component */
    public String modified;
    /** Component Type of the Service Component */
    public String componentType;
    /**
     * Specifies the name of the underlying source service responsible for this
     * component
     */
    public String componentSource;
    /** Status of the Service Component */
    public String status;
    /** Location of the Service Component */
    public String location;
    /**
     * Used to determine of this component can be shared amongst different Services
     */
    public Integer shareable;

    @JsonCreator
    public ServiceComponent(@JsonProperty("componentId") String componentId,
            @JsonProperty("componentLink") Link componentLink, @JsonProperty("created") String created,
            @JsonProperty("modified") String modified, @JsonProperty("componentType") String componentType,
            @JsonProperty("componentSource") String componentSource, @JsonProperty("status") String status,
            @JsonProperty("location") String location, @JsonProperty("shareable") Integer shareable) {
        this.componentId = componentId;
        this.componentLink = componentLink;
        this.created = created;
        this.modified = modified;
        this.componentType = componentType;
        this.componentSource = componentSource;
        this.status = status;
        this.location = location;
        this.shareable = shareable;
    }
}
