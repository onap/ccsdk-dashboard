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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model with fields only for the top-level attributes. All complex child
 * structures are represented as generic collections.
 */
public final class CloudifyBlueprint extends ECTransportModel {

	/** A unique identifier for the blueprint. */
	public final String id;
	/** The blueprint’s main file name. */
	public final String main_file_name;
	/** The blueprint’s description. */
	public final String description;
	/** The time the blueprint was uploaded to the manager. */
	public final String created_at;
	/** The last time the blueprint was updated. */
	public final String updated_at;
	/** The parsed result of the blueprint. */
	public final Map<String, Object> plan;

	@JsonCreator
	public CloudifyBlueprint(@JsonProperty("main_file_name") String main_file_name,
			@JsonProperty("description") String description, @JsonProperty("created_at") String created_at,
			@JsonProperty("updated_at") String updated_at, @JsonProperty("id") String id,
			@JsonProperty("plan") Map<String, Object> plan) {
		this.main_file_name = main_file_name;
		this.description = description;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.id = id;
		this.plan = plan;
	}

}
