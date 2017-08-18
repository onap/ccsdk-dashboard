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
package org.onap.oom.dashboard.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to create a Cloudify Blueprint:
 * 
 * <pre>
  {
	"blueprint_id" : "blueprint-id",
	"blueprint_filename" : "name.yaml",
	"zip_url" : "url"
  }
 * </pre>
 */
public final class CloudifyBlueprintUpload extends ECTransportModel {

	/** A unique identifier for the blueprint. */
	public final String blueprint_id;
	/** The blueprint’s main file name. */
	public final String blueprint_filename;
	/** The zip file URL. */
	public final String zip_url;

	@JsonCreator
	public CloudifyBlueprintUpload(@JsonProperty("blueprint_id") String blueprint_id,
			@JsonProperty("blueprint_filename") String blueprint_filename, @JsonProperty("zip_url") String zip_url) {
		this.blueprint_id = blueprint_id;
		this.blueprint_filename = blueprint_filename;
		this.zip_url = zip_url;
	}

}
