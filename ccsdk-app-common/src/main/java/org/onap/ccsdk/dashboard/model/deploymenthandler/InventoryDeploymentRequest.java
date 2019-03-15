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
package org.onap.ccsdk.dashboard.model.deploymenthandler;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message used by the controller to create a DeploymentRequest for
 * the Deployment Handler API.
 * 
 * <pre>
	{
		"deploymentId" : "deploymentId",
		"body" :
			{ 
				"serviceTypeId" : "serviceTypeId",
				"inputs" :
					{
						"input1" : "parameter1"
						"input2" : "parameter2"
								...
						"inputn" : "parametern"
					}	
			}
  	}
 * </pre>
 */
public final class InventoryDeploymentRequest {

    /** Unique deployment identifier assigned by the API client. */
    private final String deploymentId;

    /**
     * The service type identifier (a unique ID assigned by DCAE inventory) for the
     * service to be deployed.
     */
    private final String serviceTypeId;

    /**
     * Object containing inputs needed by the service blueprint to create an
     * instance of the service. Content of the object depends on the service being
     * deployed.
     */
    private final Map<String, Object> inputs;

    @JsonCreator
    public InventoryDeploymentRequest(@JsonProperty("deploymentId") String deploymentId,
            @JsonProperty("serviceTypeId") String serviceTypeId, @JsonProperty("inputs") Map<String, Object> inputs) {
        this.deploymentId = deploymentId;
        this.serviceTypeId = serviceTypeId;
        this.inputs = inputs;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public String getServiceTypeId() {
        return this.serviceTypeId;
    }

    public Map<String, Object> getInputs() {
        return this.inputs;
    }
}
