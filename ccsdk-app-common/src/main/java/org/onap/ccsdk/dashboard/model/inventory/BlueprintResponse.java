package org.onap.ccsdk.dashboard.model.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlueprintResponse {

	public BlueprintResponse() {
	}

	/** Name of the ServiceType */
	private String typeName;

	/** Version number for this ServiceType */
	private Integer typeVersion;

	/** Unique identifier for this ServiceType */
	private String typeId;

	@JsonCreator
	public BlueprintResponse(@JsonProperty("typeName") String typeName, 
			@JsonProperty("typeVersion") Integer typeVersion, 
			@JsonProperty("typeId") String typeId) {
		
		this.typeName = typeName;
		this.typeVersion = typeVersion;
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public Integer getTypeVersion() {
		return typeVersion;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setTypeVersion(Integer typeVersion) {
		this.typeVersion = typeVersion;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "BlueprintResponse [typeName=" + typeName + ", typeVersion=" + typeVersion + ", typeId=" + typeId + "]";
	}
}
