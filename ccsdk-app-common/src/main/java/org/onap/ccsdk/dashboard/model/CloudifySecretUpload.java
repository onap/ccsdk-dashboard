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

public class CloudifySecretUpload extends ECTransportModel {

	/** The secret's name */
	public final String name;
	/** The secret’s value */
	public final String value;
	/** Update value if secret already exists */
	public final boolean update_if_exists;
	/** Defines who can see the secret. Can be private, tenant or global*/
	public final String visibility;
	/** Determines who can see the value of the secret. */
	public final boolean is_hidden_value;
	/** The tenant name for this secret */
	public final String tenant;
	
	@JsonCreator
	public CloudifySecretUpload(
			@JsonProperty("name") String name,
			@JsonProperty("value") String value,
			@JsonProperty("update_if_exists") boolean update_if_exists,
			@JsonProperty("visibility") String visibility,
			@JsonProperty("is_hidden_value") boolean is_hidden_value,
			@JsonProperty("tenant") String tenant)  {
		this.name = name;
		this.value = value;
		this.update_if_exists = update_if_exists;
		this.visibility = visibility;
		this.is_hidden_value = is_hidden_value;
		this.tenant = tenant;
	}
}

