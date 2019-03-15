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
 * Model for message POST-ed to controller to update a Cloudify Deployment:
 * 
 * NOTE: THIS IS NOT HOW THE REQUEST TO CLOUDIFY'S ENDPOINT LOOKS. THE REQUEST
 * IS CONSTRUCTED IN PROPER FORMAT IN THE API HANDLER
 * 
 * <pre>
 * {
	"deployment_id" : "deployment-id",	 
	"workflow_name" : "workflow-name",
    "allow_custom_parameter" : "true|false",
    "force" : "true|false",
    "node_instance_id": "node-instance-id",
    "limits_cpu": limits_cpu,
    "limits_mem": limits_mem,
    "image": "image",
    "replicas": replicas,
    "container_name": "container_name"
  }
 * </pre>
 */
public final class CloudifyDeploymentUpdateRequest extends ECTransportModel {

    /** A unique identifier for the deployment. */
    public final String deployment_id;
    /** A unique identifier for the workflow */
    public final String workflow_name;
    public final Boolean allow_custom_parameter;
    public final Boolean force;
    /** Parameters: retrieve using the GET /deployments */
    // public final Map<String, Object> parameters;
    public final String node_instance_id;
    public final String limits_cpu;
    public final String limits_mem;
    public final String image;
    public final Number replicas;
    public final String container_name;

    @JsonCreator
    public CloudifyDeploymentUpdateRequest(@JsonProperty("deployment_id") String deployment_id,
            @JsonProperty("workflow_name") String workflow_name,
            @JsonProperty("allow_custom_parameter") Boolean allowCustomParameter, @JsonProperty("force") Boolean force,
            @JsonProperty("node_instance_id") String node_instance_id, @JsonProperty("limits_cpu") String limits_cpu,
            @JsonProperty("limits_mem") String limits_mem, @JsonProperty("image") String image,
            @JsonProperty("replicas") Number replicas, @JsonProperty("container_name") String container_name) {

        this.deployment_id = deployment_id;
        this.workflow_name = workflow_name;
        this.allow_custom_parameter = allowCustomParameter;
        this.force = force;
        // this.parameters = parameters;
        this.node_instance_id = node_instance_id;
        this.limits_cpu = limits_cpu;
        this.limits_mem = limits_mem;
        this.image = image;
        this.replicas = replicas;
        this.container_name = container_name;
    }

}
