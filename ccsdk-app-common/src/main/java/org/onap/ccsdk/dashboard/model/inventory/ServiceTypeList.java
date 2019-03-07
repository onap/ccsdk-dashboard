package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceTypeList extends ECTransportModel {

	/** Number of ServiceType objects */
	public final Integer totalCount;
	/** Collection containing all of the returned ServiceType objects */
	public final Collection<ServiceType> items;
	/** Links to the previous and next page of items */
	public final PaginationLinks paginationLinks;
	
	@JsonCreator
	public ServiceTypeList(@JsonProperty("items") Collection<ServiceType> items, 
			@JsonProperty("totalCount") Integer totalCount, 
			@JsonProperty("links") PaginationLinks paginationLinks) {
		this.items = items;
		this.totalCount = totalCount;
		this.paginationLinks = paginationLinks;
	}
	
	/** InlineResponse200Links */
	public static final class PaginationLinks {
		public final Link previousLink;
		public final Link nextLink;
		
		@JsonCreator
		public PaginationLinks (@JsonProperty("previousLink") Link previousLink, 
				@JsonProperty("nextLink") Link nextLink) {
			this.previousLink = previousLink;
			this.nextLink = nextLink;
		}
	}
}
