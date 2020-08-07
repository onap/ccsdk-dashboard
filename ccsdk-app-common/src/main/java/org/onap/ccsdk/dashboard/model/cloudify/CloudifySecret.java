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
package org.onap.ccsdk.dashboard.model.cloudify;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudifySecret extends ECTransportModel {

	/** The time when the secret was created */
	public final String created_at;
	/** The secret’s key, unique per tenant */
	public final String key;
    /** The time the secret was last updated at */
	public final String updated_at;
	/** The secret’s value */
	public final String value;
	/** Defines who can see the secret. Can be private, tenant or global*/
	public final String visibility;
	/** Determines who can see the value of the secret. */
	public final String is_hidden_value;

	public final String tenant_name;
	
	public final String resource_availability;
	
	@JsonCreator
	public CloudifySecret(
			@JsonProperty("created_at") String created_at,
			@JsonProperty("key") String key,
			@JsonProperty("updated_at") String updated_at,
			@JsonProperty("value") String value,
			@JsonProperty("visibility") String visibility,
			@JsonProperty("is_hidden_value") String is_hidden_value,
			@JsonProperty("tenant_name") String tenant_name,
			@JsonProperty("resource_availability") String resource_availability)  {
		this.created_at = created_at;
		this.key = key;
		this.updated_at = updated_at;
		this.value = value;
		this.visibility = visibility;
		this.is_hidden_value = is_hidden_value;
		this.tenant_name = tenant_name;
		this.resource_availability = resource_availability;
	}
	
	   /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
