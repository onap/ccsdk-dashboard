package org.onap.ccsdk.dashboard.model.deploymenthandler;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object providing a list of deployments
 * 
 */
public class DeploymentsListResponse {

	/** Unique identifier for the request */
	private final String requestId;
	
	/** Stream object containing links to all deployments known to the orchestrator. */
	private final Collection<DeploymentLink> deployments;
	
	@JsonCreator
	public DeploymentsListResponse (@JsonProperty("requestId") String requestId, 
			@JsonProperty("deployments") Collection<DeploymentLink> deployments) {
		this.requestId = requestId;
		this.deployments = deployments;
	}
	
	public String getRequestId() {
		return this.requestId;
	}
	
	public Collection<DeploymentLink> getDeployments() {
		return this.deployments;
	}
}
