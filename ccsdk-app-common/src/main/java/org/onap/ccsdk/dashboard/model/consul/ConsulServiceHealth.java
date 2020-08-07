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

import org.onap.ccsdk.dashboard.model.ECTransportModel;

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
    "Output": "HTTP GET http:\/\/135.91.224.136:8000\/healthcheck\/status: 200 OK Output: { \"output\": \"Thu Apr 20 19:53:01 UTC 2017|INFO|masters=1 pgaas1.rdm1.cci.att.com|secondaries=0 |maintenance= |down=1 pgaas2.rdm1.cci.att.com| \" }\n",
    "ServiceID": "pgaas1_Service_ID",
    "ServiceName": "pgaasServer1",
    "CreateIndex": 190199,
    "ModifyIndex": 199395
  }
 * </pre>
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
    public final String[] serviceTags;
    public String tenant;
    public final int createIndex;
    public final int modifyIndex;

    @JsonCreator
    public ConsulServiceHealth(@JsonProperty("Node") String node, @JsonProperty("CheckID") String checkID,
            @JsonProperty("Name") String name, @JsonProperty("Status") String status,
            @JsonProperty("Notes") String notes, @JsonProperty("Output") String output,
            @JsonProperty("ServiceID") String serviceID, @JsonProperty("ServiceName") String serviceName,
            @JsonProperty("ServiceTags") String[] serviceTags, @JsonProperty("CreateIndex") int createIndex, @JsonProperty("ModifyIndex") int modifyIndex) {
        this.node = node;
        this.checkID = checkID;
        this.name = name;
        this.status = status;
        this.notes = notes;
        this.output = output;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceTags = serviceTags;
        this.createIndex = createIndex;
        this.modifyIndex = modifyIndex;
        ConstructTenant();
    }
    
    /*
     * Search the service tags to find and
     * construct cfy tenant
     */
    private void ConstructTenant() {
    	String tenantString = "";
    	for (String tag : serviceTags) {
    		if (tag.contains("cfytenantname=")) {
    			tenantString = tag;
    			break;
    		}
    	}
    	if (!tenantString.equals(""))
    		tenant = tenantString.substring(tenantString.indexOf("=") + 1);
    	else
    		tenant = "";
    }

}
