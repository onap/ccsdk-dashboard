package org.onap.ccsdk.dashboard.model.deploymenthandler;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to create a Deployment via the Deployment Handler API:
 * 
 * <pre>
	{ 
		"component" : "comp",
		"deploymentTag" : "tag",
		"blueprintName" : "name",
		"blueprintVersion" : "version",
		"blueprintId" : "bp_id",
		"inputs" :
			{
				"input1" : "parameter1",
				"input2" : "parameter2",
						...
				"inputn" : "parametern"
			},
		"tenant" : "tenant_name"	
	}
 * </pre>
 * 
 * THIS OBJECT INCLUDES THE DEPLOYMENTID CREATED BY THE USER!
 */
public class DeploymentInput {
	
	/** component or namespace for the service */
	private final String component;
	
	/** tag to identify the deployment */
	private final String tag;
	
	/** The blueprint name for the service to be deployed. */
	private final String blueprintName;
	
	/** blueprint version for the service to be deployed */
	private final Optional<Integer> blueprintVersion;
	
	/** blueprint typeId from inventory */
	private final Optional<String> blueprintId;
	
	/** The cloudify tenant name for the deployment */
	private final String tenant;
	/** 
	 * Object containing inputs needed by the service blueprint to create an instance of the service.
	 * Content of the object depends on the service being deployed.
	 */
	private final Map<String, Object> inputs;
	
	@JsonCreator
	public DeploymentInput(
			@JsonProperty("component") String component,
			@JsonProperty("tag") String tag,
			@JsonProperty("blueprintName") String blueprintName,
			@JsonProperty("blueprintVersion") Integer blueprintVersion,
			@JsonProperty("blueprintId") String blueprintId,
			@JsonProperty("inputs") Map<String, Object> inputs,
			@JsonProperty("tenant") String tenant) {
		this.component = component;
		this.tag = tag;
		this.blueprintName = blueprintName;
		this.blueprintVersion = Optional.ofNullable(blueprintVersion);
		this.blueprintId = Optional.ofNullable(blueprintId);
		this.inputs = inputs;
		this.tenant = tenant;
	}
	
	public String getBlueprintName() {
		return this.blueprintName;
	}
	
	public Map<String, Object> getInputs() {
		return this.inputs;
	}
	
	public String getTenant() {
		return this.tenant;
	}

	public Optional<Integer> getBlueprintVersion() {
		return blueprintVersion;
	}

	public String getTag() {
		return tag;
	}

	public String getComponent() {
		return component;
	}

	public Optional<String> getBlueprintId() {
		return blueprintId;
	}
}
