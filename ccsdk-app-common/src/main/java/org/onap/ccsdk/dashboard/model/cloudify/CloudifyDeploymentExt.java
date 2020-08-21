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

package org.onap.ccsdk.dashboard.model.cloudify;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

public class CloudifyDeploymentExt extends ECTransportModel {

    /** A unique identifier for the deployment. */
    public String id;
    /** blueprint ID for the deployment */
    public String bp_id;
    public String tenant;
    /** latest execution object */
    public CloudifyExecution lastExecution;
    /** true if helm plugin is used */
    public Boolean isHelm;
    /** true if helm status is enabled */
    public Boolean helmStatus;

    public CloudifyDeploymentExt(String id, String bp_id, String tenant) {
        super();
        this.id = id;
        this.bp_id = bp_id;
        this.tenant = tenant;
    }
}
