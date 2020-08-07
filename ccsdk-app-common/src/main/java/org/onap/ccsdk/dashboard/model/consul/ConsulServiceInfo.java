/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
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

package org.onap.ccsdk.dashboard.model.consul;

import java.util.List;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Dashboard-specific model for message about a service registered for health
 * monitoring. This is NOT a model of message returned by Controller.
 * 
 * The controller API answers a message with a map of String (name) to List of
 * String (addresses).
 * 
 * <pre>
  {
	"pgaasServer1":["135.91.224.136"]
  }
 * </pre>
 */
public final class ConsulServiceInfo extends ECTransportModel {

    public final String name;
    public final List<String> addresses;

    @JsonCreator
    public ConsulServiceInfo(@JsonProperty("name") String name, @JsonProperty("addresses") List<String> addresses) {
        this.name = name;
        this.addresses = addresses;
    }

}
