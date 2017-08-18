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
 * Model for message returned by Consul about health of a service.
 * 
 * <pre>
  {
    "Node": "cjlvmcnsl00",
    "CheckID": "service:pgaas1_Service_ID",
    "Name": "Service 'pgaasServer1' check",
    "Status": "passing",
    "Notes": "This is a pgaas1_Service_ID health check",
    "Output": "HTTP GET http:\/\/1.2.3.4:8000\/healthcheck\/status: 200 OK ..",
    "ServiceID": "pgaas1_Service_ID",
    "ServiceName": "pgaasServer1",
    "CreateIndex": 190199,
    "ModifyIndex": 199395
  } </pre>
  *
  */
public final class ConsulServiceHealth extends ECTransportModel {

	public final String node;
	public final String checkID;
	public final String name;
	public final String status;
	public final String notes;
	public final String output;
	public final String serviceID;
	public final String serviceName;
	public final int createIndex;
	public final int modifyIndex;

	@JsonCreator
	public ConsulServiceHealth(
			@JsonProperty("Node") String node,
			@JsonProperty("CheckID") String checkID,
			@JsonProperty("Name") String name,
			@JsonProperty("Status") String status,
			@JsonProperty("Notes") String notes,
			@JsonProperty("Output") String output,
			@JsonProperty("ServiceID") String serviceID,
			@JsonProperty("ServiceName") String serviceName,
			@JsonProperty("CreateIndex") int createIndex,
			@JsonProperty("ModifyIndex") int modifyIndex) {
		this.node = node;
		this.checkID = checkID;
		this.name = name;
		this.status = status;
		this.notes = notes;
		this.output = output;
		this.serviceID = serviceID;
		this.serviceName = serviceName;
		this.createIndex = createIndex;
		this.modifyIndex = modifyIndex;
	}

}
