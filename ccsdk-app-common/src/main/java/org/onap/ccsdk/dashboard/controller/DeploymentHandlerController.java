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

package org.onap.ccsdk.dashboard.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequestObject;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResource;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResourceLinks;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Deployment Handler features: get/put/delete deployments
 * Methods serve Ajax requests made by Angular scripts on pages that show
 * content.
 */
@RestController
@RequestMapping("/deploymenthandler")
public class DeploymentHandlerController extends DashboardRestrictedBaseController {

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(DeploymentHandlerController.class);

    @Autowired
    DeploymentHandlerClient deploymentHandlerClient;

    private static final String DEPLOYMENTS_PATH = "dcae-deployments";
    private static final String TARGET_ENTITY_KEY = "TargetEntity";
    private static final String TARGET_SERVICE_KEY = "TargetServiceName";
    private static final String DPLH_DEP_ENTITY = "DCAE Deployment";
    private static final String DPLH_TARGET_SERVICE = "DCAE Deployment Handler";
    private static final String ERROR_RESPONSE = "ERROR";
    private static final String ERROR_CODE_KEY = "ErrorCode";
    private static final String ERROR_CODE = "300";
    private static final String ERROR_CATEGORY_KEY = "ErrorCategory";
    private static final String ERROR_CATEGORY = "ERROR";
    private static final String ERROR_DESCRIPTION_KEY = "ErrorDescription";

    @PutMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId:.+}"},
        produces = "application/json")
    public String putDeployment(HttpServletRequest request,
        @RequestBody DeploymentRequestObject deploymentRequestObject) throws Exception {
        preLogAudit(request);
        String json = null;
        final String errStr = "Deployment failed!";
        final String errLogStr = "putDeployment caught exception: ";
        try {
            if (deploymentRequestObject.getMethod().equals("create")) {
                json = objectMapper.writeValueAsString(deploymentHandlerClient.putDeployment(
                    deploymentRequestObject.getDeploymentId(), deploymentRequestObject.getTenant(),
                    new DeploymentRequest(deploymentRequestObject.getServiceTypeId(),
                        deploymentRequestObject.getInputs()),
                    request));
            }
        } catch (BadRequestException|ServiceAlreadyExistsException|ServerErrorException|DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, DPLH_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, DPLH_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr + e.getMessage());
            json = objectMapper
                .writeValueAsString(new RestResponseError("Error:  " + e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, DPLH_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, DPLH_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json =
                objectMapper.writeValueAsString(new RestResponseError("putDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @DeleteMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId:.+}"},
        produces = "application/json")
    public String deleteDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request, @RequestParam("tenant") String tenant,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = null;
        StringBuffer status = new StringBuffer();
        final String errStr = "Deleting deployment " + deploymentId + " failed!";
        final String errLogStr = "deleteDeployment caught exception";
        try {
            deploymentHandlerClient.deleteDeployment(deploymentId, tenant, request);
            String self = request.getRequestURL().toString().split("\\?")[0];
            status.append(self).append("/executions?tenant=").append(tenant);
            DeploymentResource deplRsrc = new DeploymentResource(deploymentId,
                new DeploymentResourceLinks(self, "", status.toString()));
            JSONObject statObj = new JSONObject(deplRsrc);
            json = statObj.toString();
        } catch (BadRequestException|ServerErrorException|DownstreamException|DeploymentNotFoundException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, DPLH_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, DPLH_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, DPLH_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, DPLH_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper
                .writeValueAsString(new RestResponseError("deleteDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    public void preLogAudit(HttpServletRequest request) {
        Date begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
        // logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
    }

    public void postLogAudit(HttpServletRequest request) {
        Date end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
        MDC.put(TARGET_ENTITY_KEY, DPLH_DEP_ENTITY);
        MDC.put(TARGET_SERVICE_KEY, DPLH_TARGET_SERVICE);
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + request.getRequestURI());
    }
}
