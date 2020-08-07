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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsulServiceCatalogItem {

    public final String node;
    public final String address;
    public final String datacenter;
    public final String serviceName;
    public final String serviceAddress;
    public final String servicePort;

    @JsonCreator
    public ConsulServiceCatalogItem(@JsonProperty("Node") String node, @JsonProperty("Address") String address,
            @JsonProperty("Datacenter") String datacenter, @JsonProperty("ServiceName") String serviceName,
            @JsonProperty("ServiceAddress") String serviceAddress, @JsonProperty("ServicePort") String servicePort ) {
        this.node = node;
        this.address = address;
        this.datacenter = datacenter;
        this.serviceName = serviceName;
        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    public String getNode() {
        return node;
    }

    public String getAddress() {
        return address;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public String getServicePort() {
        return servicePort;
    }
    
}
