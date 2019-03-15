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
package org.onap.ccsdk.dashboard.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for message POST-ed to controller to execute upgrade workflow on a
 * Cloudify Deployment:
 * 
 * NOTE: THIS IS NOT HOW THE REQUEST TO CLOUDIFY'S ENDPOINT LOOKS. THE REQUEST
 * IS CONSTRUCTED IN PROPER FORMAT IN THE API HANDLER
 * 
 * <pre>
*		{
				"config_url": config_url,
				"config_format": config_format,
				"chartRepo": chartRepo,
				"chartVersion": chartVersion
		};
 * </pre>
 */
public final class CloudifyDeploymentUpgradeRequest extends ECTransportModel {

    public final String config_url;
    public final String config_format;
    public final String chartRepo;
    public final String chartVersion;

    @JsonCreator
    public CloudifyDeploymentUpgradeRequest(@JsonProperty("config_url") String config_url,
            @JsonProperty("config_format") String config_format, @JsonProperty("chartRepo") String chartRepo,
            @JsonProperty("chartVersion") String chartVersion) {

        this.config_url = config_url;
        this.config_format = config_format;
        this.chartRepo = chartRepo;
        this.chartVersion = chartVersion;
    }

    public String getConfig_url() {
        return config_url;
    }

    public String getConfig_format() {
        return config_format;
    }

    public String getChartRepo() {
        return chartRepo;
    }

    public String getChartVersion() {
        return chartVersion;
    }

}
