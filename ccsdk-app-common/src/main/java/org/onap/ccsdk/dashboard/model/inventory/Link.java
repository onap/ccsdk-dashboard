package org.onap.ccsdk.dashboard.model.inventory;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {

	public String title;
	public String href;
	public String rel;
	public String uri;
	public UriBuilder uriBuilder;
	public Collection<String> rels;
	public Map<String, String> params;
	public String type;
	
	@JsonCreator
	public Link (@JsonProperty("title") String title, 
			@JsonProperty("href") String href, 
			@JsonProperty("rel") String rel, 
			@JsonProperty("uri") String uri, 
			@JsonProperty("uriBuilder") UriBuilder uriBuilder, 
			@JsonProperty("rels") Collection<String> rels, 
			@JsonProperty("params") Map<String, String> params, 
			@JsonProperty("type") String type) {
		this.title = title;
		this.href = href;
		this.rel = rel;
		this.uri = uri;
		this.uriBuilder = uriBuilder;
		this.rels = rels;
		this.params = params;
		this.type = type;
	}
}
