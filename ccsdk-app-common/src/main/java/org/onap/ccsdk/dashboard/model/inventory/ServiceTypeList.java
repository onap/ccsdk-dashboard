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

import java.util.Collection;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypeList extends ECTransportModel {

    /** Number of ServiceType objects */
    public final Integer totalCount;
    /** Collection containing all of the returned ServiceType objects */
    public final Collection<ServiceType> items;
    /** Links to the previous and next page of items */
    public final PaginationLinks paginationLinks;

    @JsonCreator
    public ServiceTypeList(@JsonProperty("items") Collection<ServiceType> items,
            @JsonProperty("totalCount") Integer totalCount, @JsonProperty("links") PaginationLinks paginationLinks) {
        this.items = items;
        this.totalCount = totalCount;
        this.paginationLinks = paginationLinks;
    }

    /** InlineResponse200Links */
    public static final class PaginationLinks {
        public final Link previousLink;
        public final Link nextLink;

        @JsonCreator
        public PaginationLinks(@JsonProperty("previousLink") Link previousLink,
                @JsonProperty("nextLink") Link nextLink) {
            this.previousLink = previousLink;
            this.nextLink = nextLink;
        }
    }
}
