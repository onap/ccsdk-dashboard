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

package org.onap.ccsdk.dashboard.model.deploymenthandler;

import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentErrorResponse {

    /** HTTP status code for the response */
    private final int status;

    /** Human-readable description of the reason for the error */
    private final String message;

    /** exception stack trace */
    private final Optional<Collection<String>> stack;

    @JsonCreator
    public DeploymentErrorResponse(@JsonProperty("status") int status,
        @JsonProperty("message") String message,
        @JsonProperty("stack") Optional<Collection<String>> stack) {
        this.status = status;
        this.message = message;
        this.stack = stack;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Optional<Collection<String>> getStack() {
        return stack;
    }
}
