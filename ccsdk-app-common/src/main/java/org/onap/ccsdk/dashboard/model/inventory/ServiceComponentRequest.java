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

public class ServiceComponentRequest {

    /** Component ID of the Service Component */
    public String componentId;
    /** Component Type of the Service Component */
    public String componentType;
    /**
     * Specifies the name of the underlying source service responsible for this
     * component
     */
    public String componentSource;
    /**
     * Used to determine if this component can be shared amongst different Services
     */
    public Integer shareable;

    @JsonCreator
    public ServiceComponentRequest(@JsonProperty("componentId") String componentId,
            @JsonProperty("componentType") String componentType,
            @JsonProperty("componentSource") String componentSource, @JsonProperty("shareable") Integer shareable) {
        this.componentId = componentId;
        this.componentType = componentType;
        this.componentSource = componentSource;
        this.shareable = shareable;
    }

    public static ServiceComponentRequest from(ServiceComponent sc) {
        return new ServiceComponentRequest(sc.componentId, sc.componentType, sc.componentSource, sc.shareable);
    }
}
