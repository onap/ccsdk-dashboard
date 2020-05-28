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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response body for a PUT or DELETE to /dcae-deployments/{deploymentId}
 * 
 */
public class DeploymentResponse {

    /** Unique Identifier for the request */
    private String requestId;

    /** Links that the API client can access */
    private DeploymentResponseLinks links;

    @JsonCreator
    public DeploymentResponse(@JsonProperty("requestId") String requestId,
            @JsonProperty("links") DeploymentResponseLinks links) {
        this.requestId = requestId;
        this.links = links;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public DeploymentResponseLinks getLinks() {
        return this.links;
    }
}