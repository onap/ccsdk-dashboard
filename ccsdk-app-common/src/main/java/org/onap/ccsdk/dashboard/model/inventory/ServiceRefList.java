package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceRefList {
	/** Number of Service objects */
	public final Integer totalCount;
	/** Collection containing all of the returned Service objects */
	public final Collection<ServiceRef> items;

	
	@JsonCreator
	public ServiceRefList(@JsonProperty("items") Collection<ServiceRef> items, 
			@JsonProperty("totalCount") Integer totalCount) {
		this.items = items;
		this.totalCount = totalCount;
	}
	
}
