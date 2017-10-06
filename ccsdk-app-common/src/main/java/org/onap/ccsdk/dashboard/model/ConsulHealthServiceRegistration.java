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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to (de)register a node or service
 * with the Consul health service. The ID is optional; the name is required.
 * 
 * <pre>
{
  "services": [
    {
      "id": "<service_id>",
      "name": "<service_name>",
      "address": "<service_address>",
      "port": "<service_port>",
      "tags": [        
      ],
      "checks": [
        {
          "endpoint": "<http url for status>",
          "interval": "<frequency to check health> e.g., 10s|10m",
          "description": "<human readable description of the check",
          "name":"<name of the check>
        },
        {
          "endpoint": "<http url for status>",
          "interval": "<frequency to check health> e.g. 10s|10m",
          "description": "<human readable description of the check",
          "name":"<name of the check>
        },
      ]
    }
  ]
}*
 * </pre>
 */
public final class ConsulHealthServiceRegistration extends ECTransportModel {

	public final List<ConsulServiceRegistration> services;

	@JsonCreator
	public ConsulHealthServiceRegistration(@JsonProperty("services") List<ConsulServiceRegistration> services) {
		this.services = services;
	}

	public static final class ConsulServiceRegistration {

		public final String id;
		public final String name;
		public final String address;
		public final String port;
		public final List<String> tags;
		public final List<EndpointCheck> checks;

		@JsonCreator
		public ConsulServiceRegistration(@JsonProperty("id") String id, //
				@JsonProperty("name") String name, //
				@JsonProperty("address") String address, //
				@JsonProperty("port") String port, //
				@JsonProperty("tags") List<String> tags,//
				@JsonProperty("checks") List<EndpointCheck> checks) {
			this.id = id;
			this.name = name;
			this.address = address;
			this.port = port;
			this.tags = tags;
			this.checks = checks;
		}

	}

	public static final class EndpointCheck {

		public final String endpoint;
		public final String interval;
		public final String description;
		public final String name;

		@JsonCreator
		public EndpointCheck(@JsonProperty("endpoint") String endpoint, //
				@JsonProperty("interval") String interval, //
				@JsonProperty("description") String description, //
				@JsonProperty("name") String name) {
			this.endpoint = endpoint;
			this.interval = interval;
			this.description = description;
			this.name = name;
		}
	}

}
