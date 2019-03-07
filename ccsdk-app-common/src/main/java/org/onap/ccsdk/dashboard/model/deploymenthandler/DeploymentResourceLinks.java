package org.onap.ccsdk.dashboard.model.deploymenthandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentResourceLinks {
	/** Link used to retrieve information about the service being deployed. */
	private final String self;
	
	/** Link used to retrieve information about deployment outcome */
	private final String outcome;
	
	/** Link used to retrieve information about the status of the installation workflow. */
	private final String status;
	
	@JsonCreator
	public DeploymentResourceLinks(
			@JsonProperty("self") String self,
			@JsonProperty("outcome") String outcome,
			@JsonProperty("status") String status) {
		this.self = self;
		this.outcome = outcome;
		this.status = status;
	}
	
	public String getSelf() {
		return this.self;
	}
	
	public String getStatus() {
		return this.status;
	}

	public String getOutcome() {
		return outcome;
	}
}
