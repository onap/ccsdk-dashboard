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
package org.onap.ccsdk.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Base class for POJOs that transport data in the ECOMP Controller webapp.
 */
public abstract class ECTransportModel {

	protected final ObjectMapper mapper = new ObjectMapper();

	public ECTransportModel() {
		// Do not serialize null values
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// toString should yield pretty version
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		// tolerate empty strings where object is expected
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	}

	/**
	 * Convenience method that serializes content as JSON and catches any
	 * exception so this method can be easily used in a catch clause.
	 * 
	 * @return A REST error response object as well-formed JSON
	 */
	public String toString() {
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException jpe) {
			// Should never, ever happen
			json = "{ \"error\" : \"" + jpe.toString() + "\"}";
		}
		return json;
	}

}
