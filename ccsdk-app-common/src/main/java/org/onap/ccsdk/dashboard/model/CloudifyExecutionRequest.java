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
 * Model for message POST-ed to controller to create a Cloudify Execution:
 * 
 * <pre>
 * {
	"deployment_id" : "deployment-id",	 
	"workflow_name" : "workflow-name",
    "allow_custom_parameter" : "true|false",
    "force" : "true|false",
    "parameters":
       {
           
       }
  }
 * </pre>
 */
public final class CloudifyExecutionRequest extends ECTransportModel {

	/** A unique identifier for the deployment. */
	public final String deployment_id;
	/** A unique identifier for the workflow */
	public final String workflow_name;
	public final Boolean allow_custom_parameter;
	public final Boolean force;
	/** Parameters: retrieve using the GET /deployments */
	public final Map<String, Object> parameters;

	@JsonCreator
	public CloudifyExecutionRequest(@JsonProperty("deployment_id") String deployment_id,
			@JsonProperty("workflow_name") String workflow_name,
			@JsonProperty("allow_custom_parameter") Boolean allowCustomParameter, @JsonProperty("force") Boolean force,
			@JsonProperty("parameters") Map<String, Object> parameters) {
		this.deployment_id = deployment_id;
		this.workflow_name = workflow_name;
		this.allow_custom_parameter = allowCustomParameter;
		this.force = force;
		this.parameters = parameters;
	}

}
