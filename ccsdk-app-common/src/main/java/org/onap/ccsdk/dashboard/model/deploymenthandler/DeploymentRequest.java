package org.onap.ccsdk.dashboard.model.deploymenthandler;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to create a Deployment via the Deployment Handler API:
 * 
 * <pre>
	{ 
		"serviceTypeId" : "serviceTypeId",
		"inputs" :
			{
				"input1" : "parameter1"
				"input2" : "parameter2"
						...
				"inputn" : "parametern"
			}	
	}
 * </pre>
 */
public class DeploymentRequest {
	
	/** The service type identifier (a unique ID assigned by DCAE inventory) for the service to be deployed. */
	private final String serviceTypeId;
	
	/** 
	 * Object containing inputs needed by the service blueprint to create an instance of the service.
	 * Content of the object depends on the service being deployed.
	 */
	private final Map<String, Object> inputs;
	
	@JsonCreator
	public DeploymentRequest(@JsonProperty("serviceTypeId") String serviceTypeId,
			@JsonProperty("inputs") Map<String, Object> inputs) {
		this.serviceTypeId = serviceTypeId;
		this.inputs = inputs;
	}
	
	public String getServiceTypeId() {
		return this.serviceTypeId;
	}
	
	public Map<String, Object> getInputs() {
		return this.inputs;
	}
}
