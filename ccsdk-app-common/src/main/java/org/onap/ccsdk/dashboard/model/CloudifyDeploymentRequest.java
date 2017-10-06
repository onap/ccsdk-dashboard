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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to create a Cloudify Deployment:
 * 
 * <pre>
	{
		"deployment_id" : "deployment-id",
		"blueprint_id" : "blueprint-id", 
		"parameters" :
             { 
                "p1" : "v1"
              }
  	}
 * </pre>
 */
public final class CloudifyDeploymentRequest extends ECTransportModel {

	/** A unique identifier for the deployment. */
	public final String deployment_id;
	/** A unique identifier for the blueprint. */
	public final String blueprint_id;
	/**
	 * These values are input for the deployment which can be retrieved from the
	 * GET /blueprint API this is :plan.input field in GET /blueprint
	 */
	public final Map<String, Object> parameters;

	@JsonCreator
	public CloudifyDeploymentRequest(@JsonProperty("deployment_id") String deployment_id,
			@JsonProperty("blueprint_id") String blueprint_id,
			@JsonProperty("parameters") Map<String, Object> parameters) {
		this.deployment_id = deployment_id;
		this.blueprint_id = blueprint_id;
		this.parameters = parameters;
	}

}
