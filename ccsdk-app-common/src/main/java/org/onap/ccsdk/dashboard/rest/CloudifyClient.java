/**
 * 
 */
package org.onap.ccsdk.dashboard.rest;

import java.util.Map;

import org.onap.ccsdk.dashboard.model.CloudifyBlueprintContent;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintUpload;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateResponse;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.CloudifySecret;
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
	 * @param execution
	 *            Execution details
	 * @return CloudifyExecution
	 */
	public CloudifyExecution startExecution(CloudifyExecutionRequest execution);

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
	public CloudifyExecution cancelExecution(final String executionId, Map<String, String> parameters, 
			final String tenant);

	/**
	 * Get the node-instance-id.
	 * 
	 * @param deploymentId
	 *				deployment ID
	 * @param nodeId
	 *				node ID
	 *	@param tenant
	 *				tenant name
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
	 * @param deploymentId
	 *				deployment ID
	 * @param tenant
	 *				tenant name
	 *
	 * @return CloudifyNodeInstanceList
	 */
	public CloudifyNodeInstanceIdList getNodeInstanceId(String id, String tenant);

	/**
	 * Query execution information for a deployment ID and execution ID passed as inputs
	 * 
	 * @param executionId
	 * @param deploymentId
	 * @return
	 */
	public CloudifyExecutionList getExecution(String executionId, String deploymentId);
	
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
	 * @param deploymentId
	 *				deployment ID
	 * @param nodeId
	 *				node ID
	 *	@param tenant
	 *				tenant name
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
	 * Start Uninstall execution workflow in cloudify 
	 * @param id
	 * @param ignoreLiveNodes
	 * @return
	 */
	public int deleteDeployment(String id, boolean ignoreLiveNodes);
	
	/**
	 * Start install execution workflow in cloudify
	 * 
	 * @param deployment
	 * @return
	 */
	public CloudifyDeploymentList createDeployment(CloudifyDeploymentRequest deployment);
	
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
	 * Remove blueprint referred by ID from cloudify
	 * 
	 * @param id
	 * @return
	 */
	public int deleteBlueprint(String id);
	
	/**
	 * Upload blueprint into cloudify
	 * 
	 * @param blueprint
	 * @return
	 */
	public CloudifyBlueprintList uploadBlueprint(CloudifyBlueprintUpload blueprint);
	
	/**
	 * View blueprint YAML text
	 * 
	 * @param id
	 * @return
	 */
	public CloudifyBlueprintContent viewBlueprint(String id);
	
	/**
	 * Query a blueprint object matching the blueprint ID in cloudify
	 * 
	 * @param id
	 * @param tenant
	 * @return
	 */
	public CloudifyBlueprintList getBlueprint(String id, String tenant);
	/**
	 * Query all the blueprints in cloudify
	 * @return
	 */
	public CloudifyBlueprintList getBlueprints();
	
	/**
	 * Query deployment inputs for a deployment ID in the cloudify tenant
	 * 
	 * @param id
	 * @param tenant
	 * @return
	 */
	public CloudifyDeploymentList getDeploymentInputs(String id, String tenant);	

	/**
	 * Query a secret object matching the input secret name in the cloudify tenant
	 * 
	 * @param secretName
	 * @param tenant
	 * @return
	 */
	public CloudifySecret getSecret(String secretName, String tenant);
}
