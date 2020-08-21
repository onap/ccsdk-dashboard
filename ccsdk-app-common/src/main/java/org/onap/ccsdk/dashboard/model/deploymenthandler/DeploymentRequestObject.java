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
import java.util.LinkedList;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to create a Deployment via the
 * Deployment Handler API:
 * 
 * <pre>
    { 
        "serviceTypeId" : "serviceTypeId",
        "type" : "install/update",
        "inputs" :
        {
            "input1" : "parameter1"
            "input2" : "parameter2"
                ...
            "inputn" : "parametern"
        }
    }
 * </pre>
 * 
 * THIS OBJECT INCLUDES THE DEPLOYMENTID CREATED BY THE USER!
 */
public class DeploymentRequestObject {

    /** Unique deployment identifier assigned by the API client. */
    private final String deploymentId;

    /** type of deployment request */
    private final String method;

    /**
     * The service type identifier (a unique ID assigned by DCAE inventory) for the
     * service to be deployed.
     */
    private final String serviceTypeId;

    /** The cloudify tenant name for the deployment */
    private final String tenant;
    /**
     * Object containing inputs needed by the service blueprint to create an
     * instance of the service. Content of the object depends on the service being
     * deployed.
     */
    private final Map<String, Object> inputs;

    private final Collection<String> reinstall_list;

    private final boolean skip_install;

    private final boolean skip_uninstall;

    private final boolean skip_reinstall;

    private final boolean force;

    private final boolean ignore_failure;

    private final boolean install_first;

    @JsonCreator
    public DeploymentRequestObject(@JsonProperty("deploymentId") String deploymentId,
        @JsonProperty("serviceTypeId") String serviceTypeId,
        @JsonProperty("inputs") Map<String, Object> inputs, @JsonProperty("tenant") String tenant,
        @JsonProperty("method") String method,
        @JsonProperty("reinstall_list") Collection<String> reinstallList,
        @JsonProperty("skip_install") boolean skipInstall,
        @JsonProperty("skip_uninstall") boolean skipUninstall,
        @JsonProperty("skip_reinstall") boolean skipReinstall, @JsonProperty("force") boolean force,
        @JsonProperty("ignore_failure") boolean ignoreFailure,
        @JsonProperty("install_first") boolean installFirst) {
        this.deploymentId = deploymentId;
        this.serviceTypeId = serviceTypeId;
        this.inputs = inputs;
        this.tenant = tenant;
        this.method = method;
        this.reinstall_list = (reinstallList == null) ? new LinkedList<String>() : reinstallList;
        this.skip_install = skipInstall;
        this.skip_uninstall = skipUninstall;
        this.skip_reinstall = skipReinstall;
        this.force = force;
        this.ignore_failure = ignoreFailure;
        this.install_first = installFirst;
    }

    public DeploymentRequestObject(String deploymentId, String serviceTypeId, String method,
        Map<String, Object> inputs, String tenant) {
        super();
        this.deploymentId = deploymentId;
        this.method = method;
        this.serviceTypeId = serviceTypeId;
        this.tenant = tenant;
        this.inputs = inputs;
        this.reinstall_list = null;
        this.skip_install = true;
        this.skip_uninstall = true;
        this.skip_reinstall = true;
        this.force = false;
        this.ignore_failure = true;
        this.install_first = false;
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

    public String getTenant() {
        return this.tenant;
    }

    public String getMethod() {
        return method;
    }

    public Collection<String> getReinstall_list() {
        return reinstall_list;
    }

    public boolean isSkip_install() {
        return skip_install;
    }

    public boolean isSkip_uninstall() {
        return skip_uninstall;
    }

    public boolean isSkip_reinstall() {
        return skip_reinstall;
    }

    public boolean isForce() {
        return force;
    }

    public boolean isIgnore_failure() {
        return ignore_failure;
    }

    public boolean isInstall_first() {
        return install_first;
    }
}
