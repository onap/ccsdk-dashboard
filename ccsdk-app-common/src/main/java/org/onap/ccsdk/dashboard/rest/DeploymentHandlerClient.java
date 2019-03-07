package org.onap.ccsdk.dashboard.rest;

import java.util.stream.Stream;

import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentLink;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;

public interface DeploymentHandlerClient {


	
	/**
	 * Gets a list of all service deployments known to the orchestrator.
	 * 
	 * @return Stream<String>
	 */
	public Stream<DeploymentLink> getDeployments();
	
	/**
	 * Gets a list of all service deployments known to the orchestrator, 
	 * restricted to a single service type.
	 * 
	 * @param serviceTypeId
	 * 		Service type identifier for the type whose deployments are to be listed.
	 * 
	 * @return Stream<String>
	 */
	public Stream<DeploymentLink> getDeployments(String serviceTypeId);
	
	/**
	 * Request deployment of a DCAE Service.
	 * 
	 * @param deploymentId
	 * 		Unique deployment identifier assigned by the API client.
	 * 
	 * @param deploymentRequest
	 * 		Deployment request object that contains the necessary fields for service deployment.
	 * 
	 * @return DeploymentResponse
	 * 		Response body for a PUT or DELETE to /dcae-deployments/{deploymentId}
	 *
	 */
	public DeploymentResponse putDeployment(String deploymentId, String tenant, 
	DeploymentRequest deploymentRequest) throws 
		BadRequestException,
		ServiceAlreadyExistsException,
		ServerErrorException,
		DownstreamException;
	/**
	 * Initiate update for a deployment 
	 * 
	 * @param deploymentId
	 * 		Unique deployment identifier assigned by the API client.
	 * 
	 * @param tenant
	 * 		Cloudify tenant where the deployment should be done
	 * 
	 * @param deploymentRequest
	 * 		Deployment request object that contains the necessary fields for service deployment.
	 * 
	 * @return DeploymentResponse
	 * 		Response body for a PUT or DELETE to /dcae-deployments/{deploymentId}
	 *
	 */
	public DeploymentResponse updateDeployment(String deploymentId, String tenant, 
	DeploymentRequest deploymentRequest) throws BadRequestException,
	ServiceAlreadyExistsException,
	ServerErrorException,
	DownstreamException;

	/**
	 * Uninstall the DCAE service and remove all associated data from the orchestrator.
	 * 
	 * @param deploymentId
	 * 		Unique deployment identifier assigned by the API client.
	 * 
	 */
	public void deleteDeployment(String deploymentId, String tenant) throws BadRequestException, 
															ServerErrorException,
															DownstreamException, 
															DeploymentNotFoundException;
}
