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
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/
package org.onap.ccsdk.dashboard.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.slf4j.MDC;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This controller maps requests for the application's landing page, which is an
 * Angular single-page application.
 */
@Controller
@RequestMapping("/")
public class DashboardHomeController extends DashboardRestrictedBaseController {

    private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(DashboardHomeController.class);

    /**
     * For general use in these methods
     */
    private final ObjectMapper mapper;

    private static Date begin, end;
    private static final String APP_LABEL = "app-label";
    
    /**
     * Never forget that Spring autowires fields AFTER the constructor is called.
     */
    public DashboardHomeController() {
        mapper = new ObjectMapper();
        // Do not serialize null values
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * @return View name key, which is resolved to a file using an Apache tiles
     *         "definitions.xml" file.
     */
    @RequestMapping(value = {"/ecd"}, method = RequestMethod.GET)
    public ModelAndView dbcDefaultController() {
        // a model is only useful for JSP; this app is angular.
        return new ModelAndView("ecd_home_tdkey");
    }

    @RequestMapping(value = "/api-docs", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Resource apiDocs() {
        return new ClassPathResource("swagger.json");
    }

    /**
     * Get the application label - name + environment
     * 
     */
    @RequestMapping(value = {APP_LABEL}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAppLabel(HttpServletRequest request) throws Exception {
        return mapper.writeValueAsString(
            DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_IN_ENV, "NA"));
    }
    
    public void preLogAudit(HttpServletRequest request) {
        begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
    }

    public void postLogAudit(HttpServletRequest request) {
        end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put("TargetEntity", "DashboardHomeController");
        MDC.put("TargetServiceName", "DashboardHomeController");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + request.getRequestURI());
    }
}
