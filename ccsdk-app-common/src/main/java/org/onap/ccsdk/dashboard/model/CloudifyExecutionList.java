/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
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
package org.onap.ccsdk.dashboard.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudifyExecutionList extends ECTransportModel {
	
	public final List<CloudifyExecution> items;
	public final Metadata metadata;

    @JsonCreator
    public CloudifyExecutionList(@JsonProperty("items") List<CloudifyExecution> items, @JsonProperty("metadata") Metadata metadata){
		this.items = items;
		this.metadata = metadata;
	}

	public static final class Metadata {
		public final Pagination pagination;

        @JsonCreator
        public Metadata(@JsonProperty("pagination") Pagination pagination){
            this.pagination = pagination;
        }
        
		public static final class Pagination {
			public final long total;
			public final long offset;
			public final long size;

            @JsonCreator
            public Pagination(@JsonProperty("total") long total, @JsonProperty("offset") long offset, @JsonProperty("size") long size){
				this.total = total;
				this.offset = offset;
				this.size = size;
			}
		}
	}
		
}
