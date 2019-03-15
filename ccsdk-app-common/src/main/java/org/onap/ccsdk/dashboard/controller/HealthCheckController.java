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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.onap.ccsdk.dashboard.model.ControllerEndpointCredentials;
import org.onap.ccsdk.dashboard.model.HealthStatus;
import org.onap.portalsdk.core.domain.App;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.onap.portalsdk.core.service.DataAccessService;
import org.onap.portalsdk.core.util.SystemProperties;
import org.slf4j.MDC;
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
public class HealthCheckController extends DashboardRestrictedBaseController {

    private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(HealthCheckController.class);

    /**
     * Application name
     */
    protected static final String APP_NAME = "ecd-app";

    private static Date begin, end;
    private static final String APP_HEALTH_CHECK_PATH = "/health";
    private static final String APP_SRVC_HEALTH_CHECK_PATH = "/health-info";

    private static final String APP_DB_QRY = "from App where id = 1";
    public static final String APP_METADATA = "APP.METADATA";

    @Autowired
    private DataAccessService dataAccessService;

    private AbstractCacheManager cacheManager;

    /**
     * application health by simply responding with a JSON object indicating status
     * 
     * @param request HttpServletRequest
     * @return HealthStatus object always
     */
    @RequestMapping(value = { APP_HEALTH_CHECK_PATH }, method = RequestMethod.GET, produces = "application/json")
    public HealthStatus healthCheck(HttpServletRequest request, HttpServletResponse response) {
        return new HealthStatus(200,
                SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) + " health check passed ");
    }

    /**
     * Checks application health by executing a sample query with local DB
     * 
     * @param request HttpServletRequest
     * @return 200 if database access succeeds, 500 if it fails.
     */
    /*
     * public HealthStatus healthCheck(HttpServletRequest request,
     * HttpServletResponse response) { //preLogAudit(request); HealthStatus
     * healthStatus = new HealthStatus(200,
     * SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) +
     * " health check passed "); try { logger.debug(EELFLoggerDelegate.debugLogger,
     * "Performing health check"); App app; Object appObj =
     * getCacheManager().getObject(APP_METADATA); if (appObj == null) { app =
     * findApp(); if (app != null) { getCacheManager().putObject(APP_METADATA, app);
     * } else { healthStatus = new HealthStatus(503,
     * SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) +
     * " health check failed to query App from database"); } }
     * 
     * if (isDbConnUp()) { healthStatus = new HealthStatus(200,
     * SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) +
     * " health check passed "); } else { healthStatus = new HealthStatus(503,
     * SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) +
     * " health check failed to run db query"); } } catch (Exception ex) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Health Check"); MDC.put("TargetServiceName", "Health Check");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Health check failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "Failed to perform health check"); healthStatus = new HealthStatus(503,
     * "health check failed: " + ex.toString()); } finally { postLogAudit(request);
     * } if (healthStatus.getStatusCode() != 200) {
     * response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);
     * response.sendError(HttpStatus.SC_SERVICE_UNAVAILABLE,
     * objectMapper.writeValueAsString(healthStatus)); }
     * 
     * return healthStatus; }
     */
    /**
     * Checks application health and availability of dependent services
     * 
     * @param request HttpServletRequest
     * @return 200 if database access succeeds, 500 if it fails.
     */
    @RequestMapping(value = { APP_SRVC_HEALTH_CHECK_PATH }, method = RequestMethod.GET, produces = "application/json")
    public HealthStatus srvcHealthCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        preLogAudit(request);
        HealthStatus healthStatus = null;
        StringBuffer sb = new StringBuffer();
        try {
            logger.debug(EELFLoggerDelegate.debugLogger, "Performing health check");
            if (isDbConnUp()) {
                sb.append(SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME))
                        .append(" health check passed; ");
                healthStatus = new HealthStatus(200,
                        SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) + sb.toString());
            } else {
                sb.append(SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME))
                        .append(" health check failed to run db query; ");
                healthStatus = new HealthStatus(503,
                        SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME) + sb.toString());
            }
            ControllerEndpointCredentials[] cec = getControllerEndpoints();

            for (int i = 0; i < cec.length; ++i) {
                // Check if API Handler is reachable
                if (!isServiceReachable(cec[i].getUrl())) {
                    MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                    MDC.put("TargetEntity", "API Handler");
                    MDC.put("TargetServiceName", "API Handler");
                    MDC.put("ErrorCode", "300");
                    MDC.put("ErrorCategory", "ERROR");
                    MDC.put("ErrorDescription", "API Handler unreachable!");
                    sb.append(" API Handler unreachable; ");
                    logger.error(EELFLoggerDelegate.errorLogger, "Failed to ping API Handler");
                }
                // Check if Inventory is reachable
                if (!isServiceReachable(cec[i].getInventoryUrl() + "/dcae-services")) {
                    MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                    MDC.put("TargetEntity", "DCAE Inventory");
                    MDC.put("TargetServiceName", "DCAE Inventory");
                    MDC.put("ErrorCode", "300");
                    MDC.put("ErrorCategory", "ERROR");
                    MDC.put("ErrorDescription", "DCAE Inventory unreachable!");
                    sb.append(" DCAE Inventory unreachable; ");
                    logger.error(EELFLoggerDelegate.errorLogger, "Failed to ping DCAE Inventory");
                }
                // Check if Deployment Handler is reachable
                if (!isServiceReachable(cec[i].getDhandlerUrl())) {
                    MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                    MDC.put("TargetEntity", "Deployment Handler");
                    MDC.put("TargetServiceName", "Deployment Handler");
                    MDC.put("ErrorCode", "300");
                    MDC.put("ErrorCategory", "ERROR");
                    MDC.put("ErrorDescription", "Deployment Handler unreachable!");
                    sb.append(" Deployment Handler unreachable; ");
                    logger.error(EELFLoggerDelegate.errorLogger, "Failed to ping Deployment Handler");
                }
            }
        } catch (Exception ex) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Health Check");
            MDC.put("TargetServiceName", "Health Check");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Health check failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "Failed to perform health check");
            sb.append(" ");
            sb.append(ex.toString());
            healthStatus = new HealthStatus(503, SystemProperties.getProperty(SystemProperties.APP_DISPLAY_NAME)
                    + " health check failed: " + sb.toString());
        } finally {
            postLogAudit(request);
        }
        if (healthStatus.getStatusCode() != 200) {
            response.setStatus(HttpStatus.SC_SERVICE_UNAVAILABLE);
            response.sendError(HttpStatus.SC_SERVICE_UNAVAILABLE, objectMapper.writeValueAsString(healthStatus));
        }
        return healthStatus;
    }

    private boolean isDbConnUp() {
        @SuppressWarnings("unchecked")
        List<App> list = dataAccessService.executeQuery(APP_DB_QRY, null);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private App findApp() {
        @SuppressWarnings("unchecked")
        List<App> list = dataAccessService.executeQuery(APP_DB_QRY, null);
        return (list == null || list.isEmpty()) ? null : (App) list.get(0);
    }

    public static boolean isServiceReachable(String targetUrl) throws IOException {
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(targetUrl).openConnection();
        httpUrlConnection.setRequestMethod("HEAD");

        try {
            int responseCode = httpUrlConnection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (UnknownHostException noInternetConnection) {
            return false;
        }
    }

    @Autowired
    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public AbstractCacheManager getCacheManager() {
        return cacheManager;
    }

    public void preLogAudit(HttpServletRequest request) {
        begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP,
                DashboardRestrictedBaseController.logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP,
                DashboardRestrictedBaseController.logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
        // logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
    }

    public void postLogAudit(HttpServletRequest request) {
        end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put("TargetEntity", "Health Check");
        MDC.put("TargetServiceName", "Health Check");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, DashboardRestrictedBaseController.logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, DashboardRestrictedBaseController.logDateFormat.format(end));
        MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger, request.getMethod() + request.getRequestURI());
    }
}
