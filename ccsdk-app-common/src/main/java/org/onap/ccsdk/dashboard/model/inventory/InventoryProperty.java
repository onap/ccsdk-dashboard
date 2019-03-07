package org.onap.ccsdk.dashboard.model.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryProperty {

	/** Number of Service objects */
	public Integer count;
	/** Service property value */
	public String propertyValue;
	/** Link to a list of Services that all have this property value */
	public Link dcaeServiceQueryLink;
	
	@JsonCreator
	public InventoryProperty (@JsonProperty("count") Integer count, 
		@JsonProperty("propertyValue") String propertyValue, 
		@JsonProperty("dcaeServiceQueryLink") Link dcaeServiceQueryLink) {
		this.count = count;
		this.propertyValue = propertyValue;
		this.dcaeServiceQueryLink = dcaeServiceQueryLink;
	}
}
