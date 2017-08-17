/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *  ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.oom.dashboard.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Trivial model for carrying error responses.
 */
public class RestResponseError extends ECTransportModel {

	private String error;

	public RestResponseError() {
	}

	/**
	 * Convenience constructor
	 * 
	 * @param message
	 *            Message body
	 */
	public RestResponseError(final String message) {
		this.error = message;
	}

	/**
	 * Convenience constructor that limits the size of the throwable message.
	 * 
	 * @param message
	 *            Message body
	 * @param t
	 *            Throwable used to construct body
	 */
	public RestResponseError(final String message, final Throwable t) {
		final int enough = 512;
		String exString = t.toString();
		String exceptionMsg = exString.length() > enough ? exString.substring(0, enough) : exString;
		this.error = message + ": " + exceptionMsg;
	}

	public String getError() {
		return error;
	}

	public void setError(String message) {
		this.error = message;
	}

	/**
	 * Convenience method that serializes content as JSON and catches any
	 * exception so this can be easily used in a catch clause.
	 * 
	 * @return A REST error response object as well-formed JSON
	 */
	public String toJson() {
		String json = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException jpe) {
			// Should never, ever happen
			json = "{ \"error\" : \"" + jpe.toString() + "\"}";
		}
		return json;
	}

	@Override
	public String toString() {
		return "RestErrorResponse[message=" + error + "]";
	}

}
