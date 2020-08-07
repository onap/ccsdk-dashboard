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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentExt;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentHelm;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPluginList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenantList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;

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
     * @return CloudifyTenantList
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
     * Get the node-instance-id.
     * 
     * @param deploymentId deployment ID
     * @param tenant       tenant name
     *
     * @return CloudifyNodeInstanceList
     */
    public CloudifyNodeInstanceIdList getNodeInstanceId(String id, String tenant);

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
    public CloudifyDeploymentList getDeployments(String tenant, int pageSize, int pageOffset);
    
    /**
     * Query deployments from cloudify and filter based
     * on given input key and value
     * At the moment only supports key "contains" not "equals"
     * For value it supports only "equals" not "contains"
     * @param inputKey
     * @param inputValue
     * @param returnFullDeployment If true, returns full deployment obj, otherwise only some attributes
     * @return
     */
    public List<CloudifyDeployment> getDeploymentsByInputFilter(String inputKey, String inputValue) throws Exception;
    
    public List<CloudifyDeployment> getDeploymentsWithFilter(HttpServletRequest request, String filter) throws Exception;

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
    
    /**
     * Query a cloudify secret under a tenant scope
     * 
     * @param secretName
     * @param tenant
     * @return
     */
    public CloudifySecret getSecret(String secretName, String tenant);

    /**
     * Query install workflow execution summary for a deployment ID
     * 
     * @param deploymentId
     * @param tenant
     * @return
     */
    public CloudifyExecutionList getInstallExecutionSummary(String deploymentId, String tenant);
    
    /**
     * 
     * Delete Blueprint
     * 
     * @param blueprint ID
     */
    public void deleteBlueprint(String bpName, String tenant);
    /**
     * 
     * Query deployment node instances
     * 
     * @param deploymentId
     * @param tenant
     * @return
     */
    public CloudifyNodeInstanceIdList getNodeInstances(String deploymentId, String tenant);

    public List<CloudifyDeployment> getDeployments(String tenant, int pageSize, int pageOffset,
        boolean recurse);

    public List<CloudifyDeployment> getDeployments(String tenant, int pageSize, int pageOffset,
        boolean recurse, boolean cache);
    
    public byte[] viewBlueprint(String tenant, String id);

    public void cacheDeployments();

    public CloudifyDeployment getDeploymentResource(final String id, final String tenant);

    public List<CloudifyDeployment> getDeploymentsWithFilter(HttpServletRequest request) throws Exception;

    public CloudifyNodeInstanceList getNodeInstanceDetails(String deploymentId, String tenant);
    
    public List<CloudifyDeployedTenant> getDeploymentForBlueprint(final String bpId);

    public List<CloudifyDeploymentExt> updateWorkflowStatus(List<CloudifyDeployment> itemList);
    
    public List<CloudifyDeploymentHelm> updateHelmInfo(List<CloudifyDeployment> itemList);

    public List<String> getDeploymentNamesWithFilter(HttpServletRequest request)
        throws Exception;
    
    public CloudifyPluginList getPlugins();

    public CloudifyExecutionList getExecutionsSummaryPerTenant(String tenant);
    
    public CloudifyExecution getExecutionIdSummary(final String id, final String tenant);
}
