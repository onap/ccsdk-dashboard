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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Controller for Deployment Handler features: get/put/delete deployments
 * Methods serve Ajax requests made by Angular scripts on pages that show
 * content.
 */
@Controller
@RequestMapping("/deploymenthandler")
public class DeploymentHandlerController extends DashboardRestrictedBaseController {

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(DeploymentHandlerController.class);

    @Autowired
    DeploymentHandlerClient deploymentHandlerClient;

    private static final String DEPLOYMENTS_PATH = "dcae-deployments";

    private static Date begin, end;

    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId:.+}" }, method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public String putDeployment(HttpServletRequest request,
            @RequestBody DeploymentRequestObject deploymentRequestObject) throws Exception {
        preLogAudit(request);
        String json = null;
        try {
            if (deploymentRequestObject.getMethod().equals("create")) {
                json = objectMapper.writeValueAsString(deploymentHandlerClient.putDeployment(
                        deploymentRequestObject.getDeploymentId(), deploymentRequestObject.getTenant(),
                        new DeploymentRequest(deploymentRequestObject.getServiceTypeId(),
                                deploymentRequestObject.getInputs())));
            } else {
                json = objectMapper.writeValueAsString(deploymentHandlerClient.updateDeployment(
                        deploymentRequestObject.getDeploymentId(), deploymentRequestObject.getTenant(),
                        new DeploymentRequest(deploymentRequestObject.getServiceTypeId(),
                                deploymentRequestObject.getInputs())));
            }
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed! Bad Request");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("Bad Request " + e.getMessage()));
        } catch (ServiceAlreadyExistsException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed! Service already exists");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("Service already exists " + e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed! Server Error");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("Server Error " + e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed! Downstream Exception");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(
                new RestResponseError("Downstream Exception " + e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("putDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId:.+}" }, method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public String deleteDeployment(@PathVariable("deploymentId") String deploymentId, HttpServletRequest request,
            @RequestParam("tenant") String tenant, HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = null;
        StringBuffer status = new StringBuffer();
        try {
            // DeploymentHandlerClient deploymentHandlerClient =
            // getDeploymentHandlerClient(request);
            deploymentHandlerClient.deleteDeployment(deploymentId, tenant);
            String self = request.getRequestURL().toString().split("\\?")[0];
            status.append(self).append("/executions?tenant=").append(tenant);
            DeploymentResource deplRsrc = new DeploymentResource(deploymentId,
                    new DeploymentResourceLinks(self, "", status.toString()));
            JSONObject statObj = new JSONObject(deplRsrc);
            json = statObj.toString();
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DeploymentNotFoundException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("deleteDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    public void preLogAudit(HttpServletRequest request) {
        begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
        // logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
    }

    public void postLogAudit(HttpServletRequest request) {
        end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put("TargetEntity", "Deployment Handler");
        MDC.put("TargetServiceName", "Deployment Handler");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger, request.getMethod() + request.getRequestURI());
    }
}
