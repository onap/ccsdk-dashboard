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
package org.onap.oom.dashboard.rest;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import org.onap.oom.dashboard.model.CloudifyBlueprintContent;
import org.onap.oom.dashboard.model.CloudifyBlueprintList;
import org.onap.oom.dashboard.model.CloudifyBlueprintUpload;
import org.onap.oom.dashboard.model.CloudifyDeploymentList;
import org.onap.oom.dashboard.model.CloudifyDeploymentRequest;
import org.onap.oom.dashboard.model.CloudifyExecution;
import org.onap.oom.dashboard.model.CloudifyExecutionList;
import org.onap.oom.dashboard.model.CloudifyExecutionRequest;
import org.onap.oom.dashboard.model.ConsulDatacenter;
import org.onap.oom.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.oom.dashboard.model.ConsulNodeInfo;
import org.onap.oom.dashboard.model.ConsulServiceHealth;
import org.onap.oom.dashboard.model.ConsulServiceHealthHistory;
import org.onap.oom.dashboard.model.ConsulServiceInfo;

/**
 * Defines the interface of the Controller REST client.
 */
public interface IControllerRestClient {

    String blueprintsPath = "blueprints";
    String viewBlueprintsPath = "viewblueprints";
    String deploymentsPath = "deployments";
    String executionsPath = "executions";
    String healthServicesPath = "healthservices";

    /**
     * Gets the list of Cloudify blueprints.
     *
     * @return CloudifyBlueprintList
     */
    CloudifyBlueprintList getBlueprints();

    /**
     * Gets the Cloudify blueprint metadata for the specified ID
     *
     * @param id
     *            Blueprint ID
     * @return CloudifyBlueprintList of size 1; null if not found
     */
    CloudifyBlueprintList getBlueprint(String id);

    /**
     * Gets the Cloudify blueprint content for the specified ID
     *
     * @param id
     *            Blueprint ID
     * @return Blueprint content
     */
    CloudifyBlueprintContent viewBlueprint(String id);

    /**
     * Uploads a Cloudify blueprint.
     *
     * @param blueprint
     *            Cloudify Blueprint to upload
     * @return CloudifyBlueprintList of size 1; null if not found
     */
    CloudifyBlueprintList uploadBlueprint(CloudifyBlueprintUpload blueprint);

    /**
     * Deletes the Cloudify blueprint with the specified id.
     *
     * @param id
     *            Blueprint ID
     * @return Status code; e.g., 200, 202, 204.
     */
    int deleteBlueprint(String id);

    /**
     * Gets the list of Cloudify deployments.
     *
     * @return CloudifyDeploymentList
     */
    CloudifyDeploymentList getDeployments();

    /**
     * Gets the Cloudify deployment for the specified ID
     *
     * @param id
     *            Deployment ID
     * @return CloudifyDeploymentList of size 1; null if not found.
     */
    CloudifyDeploymentList getDeployment(String id);

    /**
     * Creates a Cloudify deployment.
     *
     * @param deployment
     *            Deployment details
     * @return CloudifyDeploymentList of size 1
     */
    CloudifyDeploymentList createDeployment(CloudifyDeploymentRequest deployment);

    /**
     * Deletes the Cloudify deployment with the specified id.
     *
     * @param id
     *            Deployment ID
     * @param ignoreLiveNodes
     *            Boolean indicator whether to delete even if live nodes exist
     * @return Status code; e.g., 200, 202, 204.
     */
    int deleteDeployment(String id, boolean ignoreLiveNodes);

    /**
     * Gets the Cloudify executions for the specified deployment ID
     *
     * @param deploymentId
     *            Deployment ID
     * @return CloudifyExecutionList
     */
    CloudifyExecutionList getExecutions(String deploymentId);

    /**
     * Gets the Cloudify execution for the specified execution ID and deployment
     * ID
     *
     * @param executionId
     *            Execution ID
     * @param deploymentId
     *            Deployment ID
     * @return CloudifyExecutionList of size 1
     */
    CloudifyExecutionList getExecution(String executionId, String deploymentId);

    /**
     * Starts a Cloudify execution.
     *
     * @param execution
     *            Execution details
     * @return CloudifyExecution
     */
    CloudifyExecution startExecution(CloudifyExecutionRequest execution);

    /**
     * Deletes the Cloudify execution with the specified ids.
     *
     * @param executionId
     *            execution ID
     * @param deploymentId
     *            Deployment ID
     * @param action
     *            either "cancel" or "force-cancel"
     * @return Status code; e.g., 200, 202, 204.
     */
    int cancelExecution(String executionId, String deploymentId, String action);

    /**
     * Registers a service with Consul for health check.
     *
     * @param registration
     *            Details about the service to be registered.
     * @return Result of registering a service
     */
    URI registerService(ConsulHealthServiceRegistration registration);

    /**
     * Deregister a service with Consul for health check.
     *
     * @param serviceName
     *            Name of the service to be deregister.
     * @return Response code
     */
    int deregisterService(String serviceName);

    /**
     * Gets all the services that are monitored by Consul.
     *
     * @return List of ConsulServiceHealth
     */
    List<ConsulServiceInfo> getServices();

    /**
     * Gets the status for the specified service on all nodes.
     *
     * @param serviceName
     *            Service name
     * @return List of ConsulServiceHealth
     */
    List<ConsulServiceHealth> getServiceHealth(String serviceName);

    /**
     * Gets the status for the specified service on all nodes for the specified
     * time window.
     *
     * @param serviceName
     *            Service name
     * @param start
     *            Start (earliest point) of the time window
     * @param end
     *            End (latest point) of the time window
     * @return List of ConsulServiceHealth
     */
    List<ConsulServiceHealthHistory> getServiceHealthHistory(String serviceName, Instant start, Instant end);

    /**
     * Gets all the nodes that are monitored by Consul.
     *
     * @return List of ConsulNodeHealth
     */
    List<ConsulNodeInfo> getNodes();

    /**
     * Gets the status for all registered services running on the specified
     * node.
     *
     * @param nodeId
     *            Node ID
     * @return List of ConsulServiceHealth
     */
    List<ConsulServiceHealth> getNodeServicesHealth(String nodeId);

    /**
     * Gets all the data centers that are monitored by Consul.
     *
     * @return List of ConsulDatacenter objects
     */
    List<ConsulDatacenter> getDatacenters();
}
