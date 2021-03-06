/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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
 *******************************************************************************/

package org.onap.ccsdk.dashboard.model.cloudify;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceRefCfyList {
    /** Number of Service objects */
    public final Integer totalCount;
    /** Collection containing all of the returned Service objects */
    public final Collection<CloudifyDeployedTenant> items;

    /**
     * @return the items
     */
    public Collection<CloudifyDeployedTenant> getItems() {
        return items;
    }

    @JsonCreator
    public ServiceRefCfyList(@JsonProperty("items") Collection<CloudifyDeployedTenant> items,
        @JsonProperty("totalCount") Integer totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }
}
