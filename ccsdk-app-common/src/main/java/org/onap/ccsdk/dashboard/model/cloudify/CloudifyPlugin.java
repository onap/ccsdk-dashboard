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
package org.onap.ccsdk.dashboard.model.cloudify;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudifyPlugin {
/*
 *          "uploaded_at": "2020-04-29T14:46:59.628Z",
            "package_version": "1.7.2",
            "package_name": "k8splugin",
            "distribution": "centos",
            "supported_platform": "linux_x86_64",
 */
    public final String package_name;
    
    public final String package_version;
    
    public final String supported_platform;
    
    public final String distribution;
    
    public final String uploaded_at;
    
    @JsonCreator
    public CloudifyPlugin(
            @JsonProperty("package_name") String package_name,
            @JsonProperty("package_version") String package_version,
            @JsonProperty("supported_platform") String supported_platform,
            @JsonProperty("distribution") String distribution,
            @JsonProperty("uploaded_at") String uploaded_at)  {
        this.package_name = package_name;
        this.package_version = package_version;
        this.supported_platform = supported_platform;
        this.distribution = distribution;
        this.uploaded_at = uploaded_at;
    }
}
