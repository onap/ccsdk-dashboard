package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypeUploadRequest {

	/** Owner of the Service Type */
	public String owner;
	/** Name of the Service Type */
	public String typeName;
	/** Version number of the Service Type */
	public Integer typeVersion;
	/** String representation of a Cloudify blueprint with unbound variables */
	public String blueprintTemplate;
	/** Application controller name */
	public String application;
	/** onboarding component name */
	public String component;

	@JsonCreator
	public ServiceTypeUploadRequest(@JsonProperty("owner") String owner, 
			@JsonProperty("typeName") String typeName,
			@JsonProperty("typeVersion") Integer typeVersion,
			@JsonProperty("blueprintTemplate") String blueprintTemplate,
			@JsonProperty("application") String application,
			@JsonProperty("component") String component ) {
			this.owner = owner;
			this.typeName = typeName;
			this.typeVersion = typeVersion;
			this.blueprintTemplate = blueprintTemplate;
			this.application = application;
			this.component = component;
	}

	public static ServiceTypeUploadRequest from(ServiceType serviceType) {
		return new ServiceTypeUploadRequest(serviceType.getOwner(), serviceType.getTypeName(),
				serviceType.getTypeVersion(), serviceType.getBlueprintTemplate(),
				serviceType.getApplication(), serviceType.getComponent());
	}
	public String getBlueprintTemplate() {
		return this.blueprintTemplate;

	}
}
