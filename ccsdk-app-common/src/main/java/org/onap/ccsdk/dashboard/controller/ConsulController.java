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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.consul.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Controller for Consul features: health checks of services, nodes, data
 * centers. Methods serve Ajax requests made by Angular scripts on pages that
 * show content.
 */
@RestController
@RequestMapping("/healthservices")
public class ConsulController extends DashboardRestrictedBaseController {

    private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ConsulController.class);

    @Autowired
    ConsulClient consulClient;

    /**
     * Enum for selecting an item type.
     */
    public enum ConsulDataItem {
        SERVICE_INFO, SERVICE_HEALTH, NODES, DATACENTERS;
    }

    private static final String NODES_PATH = "/nodes";
    private static final String SERVICES_PATH = "/services";
    private static final String TARGET_ENTITY_KEY = "TargetEntity";
    private static final String TARGET_SERVICE_KEY = "TargetServiceName";
    private static final String CNSL_SVC_ENTITY = "Consul Service";
    private static final String CNSL_TARGET_SERVICE = "Consul API";
    private static final String ERROR_RESPONSE = "ERROR";
    private static final String ERROR_CODE_KEY = "ErrorCode";
    private static final String ERROR_CODE = "300";
    private static final String ERROR_CATEGORY_KEY = "ErrorCategory";
    private static final String ERROR_DESCRIPTION_KEY = "ErrorDescription";

    /**
     * Gets one page of objects and supporting information via the REST client. On
     * success, returns a page of objects as String.
     * 
     * @param option Specifies which item type to get
     * @param pageNum Page number of results
     * @param pageSize Number of items per browser page
     * @return JSON block as String, see above.
     * @throws Exception On any error; e.g., Network failure.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String getItemListForPage(long userId, ConsulDataItem option, int pageNum, int pageSize,
        String dc) throws Exception {
        List itemList = null;
        final String errStr = "Getting page of items failed!";
        final String errLogStr = "getItemListForPage caught exception";
        switch (option) {
            case NODES:
                itemList = consulClient.getNodes(dc);
                ((List<ConsulNodeInfo>) itemList)
                    .sort((ConsulNodeInfo o1, ConsulNodeInfo o2) -> o1.node.compareTo(o2.node));
                break;
            case DATACENTERS:
                itemList = consulClient.getDatacenters();
                break;
            default:
                MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
                MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
                MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
                MDC.put(ERROR_CODE_KEY, ERROR_CODE);
                MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
                MDC.put(ERROR_DESCRIPTION_KEY, errStr);
                logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
                throw new Exception(
                    "getItemListForPage failed: unimplemented case: " + option.name());
        }
        final int totalItems = itemList.size();
        // Shrink if needed
        if (itemList.size() > pageSize) {
            itemList = getPageOfList(pageNum, pageSize, itemList);
        }
        int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
        return objectMapper.writeValueAsString(model);
    }

    /**
     * Gets one page of the specified items. This method traps exceptions and
     * constructs an appropriate JSON block to report errors.
     * 
     * @param request Inbound request
     * @param option Item type to get
     * @return JSON with one page of objects; or an error.
     */
    protected String getItemListForPageWrapper(HttpServletRequest request, String dc,
        ConsulDataItem option) {
        String outboundJson = null;
        final String errStr = "Getting page of items failed!";
        final String errLogStr = "getItemListForPageWrapper caught exception";
        try {
            User appUser = UserUtils.getUserSession(request);
            if (appUser == null || appUser.getLoginId() == null
                || appUser.getLoginId().length() == 0) {
                throw new Exception("getItemListForPageWrapper: Failed to get application user");
            }
            int pageNum = getRequestPageNumber(request);
            int pageSize = getRequestPageSize(request);
            outboundJson = getItemListForPage(appUser.getId(), option, pageNum, pageSize, dc);
        } catch (Exception ex) {
            // Remote service failed; build descriptive error message
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            RestResponseError result = new RestResponseError("Failed to get " + option.name(), ex);
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        }
        return outboundJson;
    }

    /**
     * Serves service health details - not paginated.
     * 
     * @param request HttpServletRequest
     * @param serviceId Service ID
     * @return List of ConsulServiceHealth objects as JSON
     * @throws Exception if serialization fails
     */
    @GetMapping(value = {SERVICES_PATH + "/{serviceId}"}, produces = "application/json")
    public String getServiceHealthDetails(HttpServletRequest request, @RequestParam String dc,
        @PathVariable String serviceId) throws Exception {
        preLogAudit(request);
        Object result = null;
        final String errStr = "Getting service health details failed for: " + serviceId;
        final String errLogStr = "getServiceHealthDetails caught exception";
        try {
            result = consulClient.getServiceHealth(dc, serviceId);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getServiceHealthDetails failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Serves one page of service health information by getting all service names,
     * then iterating over them to get the health of each service.
     * 
     * API to get the health of all services in one request is not available.
     * 
     * @param request HttpServletRequest
     * @return List of ConsulServiceHealth objects, as JSON
     * @throws Exception on serialization exception
     */
    @SuppressWarnings("unchecked")
    @GetMapping(value = {"/serviceshealth"}, produces = "application/json")
    public String getServicesHealth(HttpServletRequest request, @RequestParam String dc)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            List<ConsulServiceHealth> itemList = new ArrayList<>();
            List<ConsulServiceInfo> svcInfoList = consulClient.getServices(dc);
            for (ConsulServiceInfo csi : svcInfoList) {
                List<ConsulServiceHealth> csh = consulClient.getServiceHealth(dc, csi.name);
                itemList.addAll(csh);
            }
            itemList.sort((ConsulServiceHealth o1, ConsulServiceHealth o2) -> o1.serviceName
                .compareTo(o2.serviceName));
            // Paginate
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize) {
                itemList = getPageOfList(pageNum, pageSize, itemList);
            }
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, "Getting services health failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getServicesHealth caught exception");
            result = new RestResponseError("getServicesHealth failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Serves one page of node information.
     * 
     * @param request HttpServletRequest
     * @return List of ConsulNodeInfo objects, as JSON
     */
    @GetMapping(value = {NODES_PATH}, produces = "application/json")
    public String getNodesInfo(HttpServletRequest request, @RequestParam String dc) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, dc, ConsulDataItem.NODES);
        postLogAudit(request);
        return json;
    }

    /**
     * Serves node services health details - not paginated.
     * 
     * @param request HttpServletRequest
     * @param nodeName Node name
     * @return List of ConsulServiceHealth objects as JSON
     * @throws Exception if serialization fails
     */
    @GetMapping(value = {NODES_PATH + "/{nodeName}"}, produces = "application/json")
    public String getNodeServicesHealth(HttpServletRequest request, @RequestParam String dc,
        @PathVariable String nodeName) throws Exception {
        preLogAudit(request);
        Object result = null;
        try {
            result = consulClient.getNodeServicesHealth(dc, nodeName);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY,
                "Getting node services health for " + nodeName + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeServicesHealth caught exception");
            result = new RestResponseError("getNodeServicesHealth failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Serves one page of datacenters health.
     * 
     * @param request HttpServletRequest
     * @return List of ConsulHealthStatus objects
     */
    @GetMapping(
        value = {"/datacenters"},
        produces = "application/json")
    public String getDatacentersHealth(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, null, ConsulDataItem.DATACENTERS);
        postLogAudit(request);
        return json;
    }

    public void preLogAudit(HttpServletRequest request) {
        Date begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
    }

    public void postLogAudit(HttpServletRequest request) {
        Date end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
        MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + request.getRequestURI());
    }
}
