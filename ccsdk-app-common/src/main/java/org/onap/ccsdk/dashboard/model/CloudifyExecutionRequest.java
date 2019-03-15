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

public class CloudifyExecutionRequest extends ECTransportModel {

    /** A unique identifier for the deployment. */
    public String deployment_id;
    /** A unique identifier for the workflow */
    public String workflow_id;
    public Boolean allow_custom_parameters;
    public Boolean force;
    public String tenant;
    /** Parameters: retrieve using the GET /deployments */
    public Map<String, Object> parameters;

    public String getDeployment_id() {
        return deployment_id;
    }

    public String getWorkflow_id() {
        return workflow_id;
    }

    public Boolean getAllow_custom_parameters() {
        return allow_custom_parameters;
    }

    public Boolean getForce() {
        return force;
    }

    public String getTenant() {
        return tenant;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setDeployment_id(String deployment_id) {
        this.deployment_id = deployment_id;
    }

    public void setWorkflow_id(String workflow_id) {
        this.workflow_id = workflow_id;
    }

    public void setAllow_custom_parameters(Boolean allow_custom_parameters) {
        this.allow_custom_parameters = allow_custom_parameters;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @JsonCreator
    public CloudifyExecutionRequest(@JsonProperty("deployment_id") String deployment_id,
            @JsonProperty("workflow_id") String workflow_id,
            @JsonProperty("allow_custom_parameters") Boolean allowCustomParameters,
            @JsonProperty("force") Boolean force, @JsonProperty("tenant") String tenant,
            @JsonProperty("parameters") Map<String, Object> parameters) {
        this.deployment_id = deployment_id;
        this.workflow_id = workflow_id;
        this.allow_custom_parameters = allowCustomParameters;
        this.force = force;
        this.tenant = tenant;
        this.parameters = parameters;
    }

}
