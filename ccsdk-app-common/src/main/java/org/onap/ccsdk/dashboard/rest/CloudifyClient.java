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
package org.onap.ccsdk.dashboard.rest;

import java.util.Map;

import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateResponse;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;

/**
 * @author rp5662
 *
 */
public interface CloudifyClient {

    /**
     * Get the execution logs
     * 
     */
    public CloudifyEventList getEventlogs(String execution_id, String tenant);

    /**
     * Gets the list of Cloudify tenants.
     * 
     * @return CloudifyBlueprintList
     */
    public CloudifyTenantList getTenants();

    /**
     * Starts a Cloudify execution.
     * 
     * @param execution Execution details
     * @return CloudifyExecution
     */
    public CloudifyExecution startExecution(CloudifyExecutionRequest execution);

    /**
     * Deletes the Cloudify execution with the specified ids.
     * 
     * @param executionId  execution ID
     * @param deploymentId Deployment ID
     * @param action       either "cancel" or "force-cancel"
     * @return Status code; e.g., 200, 202, 204.
     */
    public CloudifyExecution cancelExecution(final String executionId, Map<String, String> parameters,
            final String tenant);

    /**
     * Get the node-instance-id.
     * 
     * @param deploymentId deployment ID
     * @param nodeId       node ID
     * @param tenant       tenant name
     * @return CloudifyNodeInstanceList
     */
    public CloudifyNodeInstanceIdList getNodeInstanceId(String deploymentId, String nodeId, String tenant);

    /**
     * Gets all the deployments with include filters for tenant name
     * 
     * @return List of CloudifyDeployedTenant objects
     */
    public CloudifyDeployedTenantList getTenantInfoFromDeploy(String tenant);

    /**
     * Get the node-instance-id.
     * 
     * @param deploymentId deployment ID
     * @param tenant       tenant name
     *
     * @return CloudifyNodeInstanceList
     */
    public CloudifyNodeInstanceIdList getNodeInstanceId(String id, String tenant);

    /**
     * Initiate a deployment update in cloudify
     * 
     * @param execution
     * @return
     */
    public CloudifyDeploymentUpdateResponse updateDeployment(CloudifyDeploymentUpdateRequest execution);

    /**
     * Query execution information for a deployment ID passed as input
     * 
     * @param deploymentId
     * @param tenant
     * @return
     */
    public CloudifyExecutionList getExecutions(final String deploymentId, final String tenant);

    /**
     * Query execution summary for a deployment ID passed as input
     * 
     * @param deploymentId
     * @param tenant
     * @return
     */
    public CloudifyExecutionList getExecutionsSummary(final String deploymentId, final String tenant);

    /**
     * Get cloudify node-instance-revisions.
     * 
     * @param deploymentId deployment ID
     * @param nodeId       node ID
     * @param tenant       tenant name
     * @return CloudifyNodeInstanceList
     */
    public CloudifyNodeInstanceList getNodeInstanceVersion(String deploymentId, String nodeId, String tenant);

    /**
     * Get cloudify node-instance-revisions
     * 
     * @param bp_id
     * @param tenant
     * @return
     */
    public CloudifyNodeInstanceList getNodeInstanceVersion(String bp_id, String tenant);

    /**
     * Query deployment object from cloudify
     * 
     * @param id
     * @param tenant
     * @return
     */
    public CloudifyDeploymentList getDeployment(String id, String tenant);

    /**
     * Query deployment object from cloudify
     * 
     * @param id
     * @return
     */
    public CloudifyDeploymentList getDeployment(String id);

    /**
     * Query deployments from cloudify
     * 
     */
    public CloudifyDeploymentList getDeployments();

    /**
     * Query a blueprint object matching the blueprint ID in cloudify
     * 
     * @param id
     * @param tenant
     * @return
     */
    public CloudifyBlueprintList getBlueprint(String id, String tenant);

    /**
     * Query deployment inputs for a deployment ID in the cloudify tenant
     * 
     * @param id
     * @param tenant
     * @return
     */
    public CloudifyDeploymentList getDeploymentInputs(String id, String tenant);
}
