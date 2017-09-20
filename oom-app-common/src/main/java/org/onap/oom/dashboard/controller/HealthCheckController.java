/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
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
package org.onap.oom.dashboard.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.onap.oom.dashboard.model.HealthStatus;
import org.openecomp.portalsdk.core.controller.UnRestrictedBaseController;
import org.openecomp.portalsdk.core.domain.App;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.openecomp.portalsdk.core.service.DataAccessService;
import org.openecomp.portalsdk.core.util.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HealthCheckController extends UnRestrictedBaseController {

    private EELFLoggerDelegate log = EELFLoggerDelegate.getLogger(HealthCheckController.class);

    private static final String HEALTH_CHECK_PATH = "/healthCheck";

    @Autowired
    private DataAccessService dataAccessService;

    /**
     * Checks application health by making a trivial query to (what??).
     *
     * @param request
     *            HttpServletRequest
     * @return 200 if database access succeeds, 500 if it fails.
     */
    @RequestMapping(value = {HEALTH_CHECK_PATH}, method = RequestMethod.GET, produces = "application/json")
    public HealthStatus healthCheck(HttpServletRequest request) {
        log.setRequestBasedDefaultsIntoGlobalLoggingContext(request, DashboardRestrictedBaseController.APP_NAME);
        log.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        HealthStatus healthStatus = null;
        try {
            log.debug(EELFLoggerDelegate.debugLogger, "Performing health check");
            @SuppressWarnings("unchecked")
            // Get the single app.
                List<App> list = dataAccessService.getList(App.class, null);
            if (!list.isEmpty()) {
                healthStatus = new HealthStatus(200,
                    SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) + " health check succeeded");
            } else {
                healthStatus = new HealthStatus(500, SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME)
                    + " health check failed to run db query");
            }
        } catch (Exception ex) {
            log.error(EELFLoggerDelegate.errorLogger, "Failed to perform health check", ex);
            healthStatus = new HealthStatus(500, "health check failed: " + ex.toString());
        }
        return healthStatus;
    }
}
