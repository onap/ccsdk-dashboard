package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceGroupByResults {

	/** Property name of the service that the group by operation was performed on */
	public String propertyName;
	/** Set of Service objects that have the aforementioned propertyName */
	public Set<InventoryProperty> propertyValues;
	
	@JsonCreator
	public ServiceGroupByResults (@JsonProperty("propertyName") String propertyName, 
			@JsonProperty("propertyValues") Set<InventoryProperty> propertyValues) {
		this.propertyName = propertyName;
		this.propertyValues = propertyValues;
	}
}
