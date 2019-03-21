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

package org.onap.ccsdk.dashboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onap.ccsdk.dashboard.model.HealthStatus;
import org.onap.portalsdk.core.util.SystemProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller responds to probes for application health, returning a JSON
 * body to indicate current status.
 */
@RestController
@Configuration
@EnableAspectJAutoProxy
@RequestMapping("/")
public class HealthCheckController extends DashboardRestrictedBaseController {

    /**
     * Application name
     */
    protected static final String APP_NAME = "ecd-app";

    private static final String APP_HEALTH_CHECK_PATH = "/health";

    /**
     * application health by simply responding with a JSON object indicating status
     * 
     * @param request HttpServletRequest
     * @return HealthStatus object always
     */
    @RequestMapping(value = { APP_HEALTH_CHECK_PATH }, method = RequestMethod.GET, produces = "application/json")
    public HealthStatus healthCheck(HttpServletRequest request, HttpServletResponse response) {
        return new HealthStatus(200, SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME)
            + " health check passed ");
    }
}
