package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceList extends ECTransportModel {

	/** Number of Service objects */
	public final Integer totalCount;
	/** Collection containing all of the returned Service objects */
	public final Collection<Service> items;
	/** Links to the previous and next page of items */
	public final PaginationLinks paginationLinks;
	
	@JsonCreator
	public ServiceList(@JsonProperty("items") Collection<Service> items, 
			@JsonProperty("totalCount") Integer totalCount, 
			@JsonProperty("links") PaginationLinks paginationLinks) {
		this.items = items;
		this.totalCount = totalCount;
		this.paginationLinks = paginationLinks;
	}
	
	/** Inline200ResponseLinks */
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