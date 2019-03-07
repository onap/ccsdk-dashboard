package org.onap.ccsdk.dashboard.model.deploymenthandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentResource {
	/** Unique Identifier for the resource */
	private String deploymentId;
	
	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	/** Links that the API client can access */
	private DeploymentResourceLinks links;
	
	@JsonCreator
	public DeploymentResource(@JsonProperty("deployment_id") String deploymentId, 
			@JsonProperty("links") DeploymentResourceLinks links) {
		this.deploymentId = deploymentId;
		this.links = links;
	}
	
	public DeploymentResourceLinks getLinks() {
		return this.links;
	}

}
