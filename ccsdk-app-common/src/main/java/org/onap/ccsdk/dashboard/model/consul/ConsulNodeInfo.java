/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *******************************************************************************/

package org.onap.ccsdk.dashboard.model.consul;

import java.util.Map;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message returned by Consul about a node registered for health
 * monitoring.
 * 
 * <pre>
  {
    "ID":"a2788806-6e2e-423e-8ee7-6cad6f3d3de6",
    "Node":"cjlvmcnsl00",
    "Address":"10.170.8.13",
    "TaggedAddresses":{"lan":"10.170.8.13","wan":"10.170.8.13"},
    "Meta":{},
    "CreateIndex":6,
    "ModifyIndex":179808
   }
 * </pre>
 */
public final class ConsulNodeInfo extends ECTransportModel {

    public final String id;
    public final String node;
    public final String address;
    public final Map<String, Object> taggedAddresses;
    public final Map<String, Object> meta;
    public final int createIndex;
    public final int modifyIndex;

    @JsonCreator
    public ConsulNodeInfo(@JsonProperty("ID") String id, @JsonProperty("Node") String node,
        @JsonProperty("Address") String address,
        @JsonProperty("TaggedAddresses") Map<String, Object> taggedAddresses,
        @JsonProperty("Meta") Map<String, Object> meta,
        @JsonProperty("CreateIndex") int createIndex,
        @JsonProperty("ModifyIndex") int modifyIndex) {
        this.id = id;
        this.node = node;
        this.address = address;
        this.taggedAddresses = taggedAddresses;
        this.meta = meta;
        this.createIndex = createIndex;
        this.modifyIndex = modifyIndex;
    }

}
