package org.onap.ccsdk.dashboard.model.deploymenthandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response body for a PUT or DELETE to /dcae-deployments/{deploymentId}
 * 
 */
public class DeploymentResponse {

	/** Unique Identifier for the request */
	private String requestId;
	
	/** Links that the API client can access */
	private DeploymentResponseLinks links;
	
	@JsonCreator
	public DeploymentResponse(@JsonProperty("requestId") String requestId, 
			@JsonProperty("links") DeploymentResponseLinks links) {
		this.requestId = requestId;
		this.links = links;
	}
	
	public String getRequestId() {
		return this.requestId;
	}
	
	public DeploymentResponseLinks getLinks() {
		return this.links;
	}
}
