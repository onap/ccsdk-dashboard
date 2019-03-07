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
package org.onap.ccsdk.dashboard.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudifyDeployedTenant extends ECTransportModel {
	
	/** A unique identifier for the deployment. */
	public final String id;
	/** tenant where the deployment was done */
	public final String tenant_name;
	/** The id of the blueprint the deployment is based on. */
	public final String blueprint_id;
	
	@JsonCreator
	public CloudifyDeployedTenant(@JsonProperty("id") String id,
			@JsonProperty("blueprint_id") String blueprint_id, 
			@JsonProperty("tenant_name") String tenant_name) {
		this.id = id;
		this.blueprint_id = blueprint_id;
		this.tenant_name = tenant_name;
	}
}
