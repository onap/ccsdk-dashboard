package org.onap.ccsdk.dashboard.model.deploymenthandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Links that the API client can access
 *
 */
public class DeploymentResponseLinks {
	
	/** Link used to retrieve information about the service being deployed. */
	private final String self;
	
	/** Link used to retrieve information about the status of the installation workflow. */
	private final String status;
	
	@JsonCreator
	public DeploymentResponseLinks(@JsonProperty("self") String self, 
			@JsonProperty("status") String status) {
		this.self = self;
		this.status = status;
	}
	
	public String getSelf() {
		return this.self;
	}
	
	public String getStatus() {
		return this.status;
	}
}
