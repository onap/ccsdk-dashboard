package org.onap.ccsdk.dashboard.model.deploymenthandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentLink {

	/** URL for the service Deployment */
	private String href;
	
	@JsonCreator 
	public DeploymentLink (@JsonProperty("href") String href) {
		this.href = href;
	}
	
	public String getHref() {
		return this.href;
	}
}
