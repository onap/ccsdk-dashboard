package org.onap.ccsdk.dashboard.model.inventory;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceRef {

	/** Service ID of the Service */
	private final String serviceId;
	/** Creation date of the Service */
	private final String created;
	/** Last modified date of the Service */
	private final String modified;
	

	@JsonCreator
	public ServiceRef (@JsonProperty("serviceId") String serviceId, 
			@JsonProperty("created") String created, 
			@JsonProperty("modified") String modified) {
		this.serviceId = serviceId;
		this.created = created;
		this.modified = modified;

	}

	public String getServiceId() {
		return serviceId;
	}

	public String getCreated() {
		return created;
	}

	public String getModified() {
		return modified;
	}
	
	/*
	private ServiceRef (
			String serviceId, 
			String created, 
			String modified) {
		this.serviceId = serviceId;
		this.created = created;
		this.modified = modified;
	}
	*/

}
