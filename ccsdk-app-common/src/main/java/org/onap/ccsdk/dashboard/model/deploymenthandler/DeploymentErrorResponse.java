package org.onap.ccsdk.dashboard.model.deploymenthandler;

import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentErrorResponse {

	/** HTTP status code for the response */
	private final int status;

	/** Human-readable description of the reason for the error */ 
	private final String message;
	
	/** exception stack trace */
	private final Optional<Collection<String>> stack;
	
	@JsonCreator
	public DeploymentErrorResponse(@JsonProperty("status") int status, 
			@JsonProperty("message") String message,
			@JsonProperty("stack") Optional<Collection<String>> stack) {
		this.status = status;
		this.message = message;
		this.stack = stack;
	}
	
	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Optional<Collection<String>> getStack() {
		return stack;
	}
}
