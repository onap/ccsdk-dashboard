package org.onap.ccsdk.dashboard.model.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceComponentRequest {

	/** Component ID of the Service Component */
	public String componentId;
	/** Component Type of the Service Component */
	public String componentType;
	/** Specifies the name of the underlying source service responsible for this component */
	public String componentSource;
	/** Used to determine if this component can be shared amongst different Services */
	public Integer shareable;
	
	@JsonCreator
	public ServiceComponentRequest (@JsonProperty("componentId") String componentId, 
			@JsonProperty("componentType") String componentType, 
			@JsonProperty("componentSource") String componentSource,
			@JsonProperty("shareable") Integer shareable) {
		this.componentId = componentId;
		this.componentType = componentType;
		this.componentSource = componentSource;
		this.shareable = shareable;
	}

	public static ServiceComponentRequest from(ServiceComponent sc) {
		return new ServiceComponentRequest(sc.componentId, sc.componentType, sc.componentSource, sc.shareable);
	}
}
