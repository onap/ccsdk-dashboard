/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *******************************************************************************/

package org.onap.ccsdk.dashboard.model.cloudify;

import java.util.Map;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model with fields only for the top-level attributes. All complex child
 * structures are represented simply as generic collections.
 */
public final class CloudifyExecution extends ECTransportModel {

    /** A unique identifier for the execution. */
    public final String id;
    /** The executions status. */
    public final String status;
    /** The time the execution was queued at. */
    public final String created_at;
    /** The time the execution ended in successful, failed or cancelled state */
    public final String ended_at;
    /** The id/name of the workflow the execution is of. */
    public final String workflow_id;
    /** true if the execution is of a system workflow. */
    public final Boolean is_system_workflow;
    /** The id of the blueprint the execution is in the context of. */
    public final String blueprint_id;
    /** The id of the deployment the execution is in the context of. */
    public final String deployment_id;
    /** The tenant used to deploy */
    public final String tenant_name;
    /** The execution’s error message on execution failure. */
    public final String error;
    /** A dict of the workflow parameters passed when starting the execution. */
    public final Map<String, Object> parameters;

    @JsonCreator
    public CloudifyExecution(@JsonProperty("status") String status,
        @JsonProperty("created_at") String created_at, @JsonProperty("ended_at") String ended_at,
        @JsonProperty("workflow_id") String workflow_id,
        @JsonProperty("is_system_workflow") Boolean is_system_workflow,
        @JsonProperty("blueprint_id") String blueprint_id,
        @JsonProperty("deployment_id") String deployment_id,
        @JsonProperty("tenant_name") String tenant_name, @JsonProperty("error") String error,
        @JsonProperty("id") String id, @JsonProperty("parameters") Map<String, Object> parameters) {

        this.status = status;
        this.created_at = created_at;
        this.ended_at = ended_at;
        this.workflow_id = workflow_id;
        this.is_system_workflow = is_system_workflow;
        this.blueprint_id = blueprint_id;
        this.deployment_id = deployment_id;
        this.tenant_name = tenant_name;
        this.error = error;
        this.id = id;
        this.parameters = parameters;
    }

}
