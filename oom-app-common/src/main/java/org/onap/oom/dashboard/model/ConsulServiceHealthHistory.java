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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message returned by Consul about health of a service observed at
 * some point in time.
 * 
 * <pre>
  {
    "Status": "critical",
    "Output": "\"Get http://1.2.3.4:8080: dial tcp 2.3.4.5:8080: getsockopt: connection refused\"",
    "Date": "2017-06-01 15:31:58.00-0000"
  }
 * </pre>
 *
 */
public final class ConsulServiceHealthHistory extends ECTransportModel {

	public final String status;
	public final String output;
	public final String date;

	@JsonCreator
	public ConsulServiceHealthHistory(@JsonProperty("Status") String status, @JsonProperty("Output") String output,
			@JsonProperty("Date") String date) {
		this.status = status;
		this.output = output;
		this.date = date;
	}

}