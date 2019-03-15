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

public class InventoryProperty {

    /** Number of Service objects */
    public Integer count;
    /** Service property value */
    public String propertyValue;
    /** Link to a list of Services that all have this property value */
    public Link dcaeServiceQueryLink;

    @JsonCreator
    public InventoryProperty(@JsonProperty("count") Integer count, @JsonProperty("propertyValue") String propertyValue,
            @JsonProperty("dcaeServiceQueryLink") Link dcaeServiceQueryLink) {
        this.count = count;
        this.propertyValue = propertyValue;
        this.dcaeServiceQueryLink = dcaeServiceQueryLink;
    }
}
