package org.onap.ccsdk.dashboard.model.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypeServiceMap {

	private final String serviceTypeId;
	
	private final ServiceRefList serviceRefList;
	
	@JsonCreator
	public ServiceTypeServiceMap (@JsonProperty("serviceTypeId") String serviceTypeId, 
			@JsonProperty("created") ServiceRefList serviceRefList) {
		this.serviceTypeId = serviceTypeId;
		this.serviceRefList = serviceRefList;
	}

	public String getServiceTypeId() {
		return serviceTypeId;
	}

	public ServiceRefList getServiceRefList() {
		return serviceRefList;
	}
}
