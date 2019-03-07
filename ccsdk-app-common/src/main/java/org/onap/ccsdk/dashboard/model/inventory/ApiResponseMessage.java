package org.onap.ccsdk.dashboard.model.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponseMessage {

	/** Response Code */
	public Integer code;
	/** Response Type */
	public String type;
	/** Response Message */
	public String message;
	
	@JsonCreator
	public ApiResponseMessage (@JsonProperty("code") Integer code, 
			@JsonProperty("type") String type, 
			@JsonProperty("message") String message){
		this.code = code;
		this.type = type;
		this.message = message;
	}
}
