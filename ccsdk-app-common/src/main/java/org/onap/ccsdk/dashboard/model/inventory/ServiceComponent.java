package org.onap.ccsdk.dashboard.model.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceComponent {

	/** Component ID of the Service Component */
	public String componentId;
	/** Link to the Service Component */
	public Link componentLink;
	/** Creation date of the Service Component */
	public String created;
	/** Last modified date of the Service Component */
	public String modified;
	/** Component Type of the Service Component */
	public String componentType;
	/** Specifies the name of the underlying source service responsible for this component */
	public String componentSource;
	/** Status of the Service Component */
	public String status;
	/** Location of the Service Component */
	public String location;
	/** Used to determine of this component can be shared amongst different Services */
	public Integer shareable;
	
	@JsonCreator
	public ServiceComponent(@JsonProperty("componentId") String componentId, 
			@JsonProperty("componentLink") Link componentLink, 
			@JsonProperty("created") String created, 
			@JsonProperty("modified") String modified, 
			@JsonProperty("componentType") String componentType, 
			@JsonProperty("componentSource") String componentSource, 
			@JsonProperty("status") String status, 
			@JsonProperty("location") String location, 
			@JsonProperty("shareable") Integer shareable) {
		this.componentId = componentId;
		this.componentLink = componentLink;
		this.created = created;
		this.modified = modified;
		this.componentType = componentType;
		this.componentSource = componentSource;
		this.status = status;
		this.location = location;
		this.shareable = shareable;
	}
}
