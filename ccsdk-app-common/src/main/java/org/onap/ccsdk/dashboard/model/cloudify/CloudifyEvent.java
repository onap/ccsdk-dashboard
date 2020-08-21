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

import java.util.LinkedList;
import java.util.List;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudifyEvent extends ECTransportModel {

    /** The id of the blueprint the execution is in the context of. */
    public final String blueprint_id;
    /** The id of the deployment the execution is in the context of. */
    public final String deployment_id;
    /** List of errors that happened while executing a given task */
    public final List<CloudifyErrorCause> error_causes;
    /** The executions status. */
    public final String event_type;
    /** The time the execution was queued at. */
    public final String execution_id;
    /** log level */
    public final String level;
    /** logger id */
    public final String logger;
    /** message text */
    public final String message;
    /** node instance id */
    public final String node_instance_id;
    /** node name */
    public final String node_name;
    /** Operation path */
    public final String operation;
    /** time at which the event occurred on the executing machine */
    public final String reported_timestamp;
    /** time at which the event was logged on the management machine */
    public final String timestamp;
    /** resource is a cloudify_event or a cloudify_log */
    public final String type;
    /** The id/name of the workflow the execution is of. */
    public final String workflow_id;

    @JsonCreator
    public CloudifyEvent(@JsonProperty("blueprint_id") String blueprint_id,
        @JsonProperty("deployment_id") String deployment_id,
        @JsonProperty("error_causes") List<CloudifyErrorCause> error_causes,
        @JsonProperty("event_type") String event_type,
        @JsonProperty("execution_id") String execution_id, @JsonProperty("level") String level,
        @JsonProperty("logger") String logger, @JsonProperty("message") String message,
        @JsonProperty("node_instance_id") String node_instance_id,
        @JsonProperty("node_name") String node_name, @JsonProperty("operation") String operation,
        @JsonProperty("reported_timestamp") String reported_timestamp,
        @JsonProperty("timestamp") String timestamp, @JsonProperty("type") String type,
        @JsonProperty("workflow_id") String workflow_id) {

        this.blueprint_id = blueprint_id;
        this.deployment_id = deployment_id;
        this.error_causes =
            (error_causes == null) ? new LinkedList<CloudifyErrorCause>() : error_causes;
        this.event_type = event_type;
        this.execution_id = execution_id;
        this.level = level;
        this.logger = logger;
        this.message = message;
        this.node_instance_id = node_instance_id;
        this.node_name = node_name;
        this.operation = operation;
        this.reported_timestamp = reported_timestamp;
        this.timestamp = timestamp;
        this.type = type;
        this.workflow_id = workflow_id;
    }
}
