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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration.EndpointCheck;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.RestResponseSuccess;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Controller for Consul features: health checks of services, nodes, data
 * centers. Methods serve Ajax requests made by Angular scripts on pages that
 * show content.
 */
@Controller
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

    private static Date begin, end;
    private static final String NODES_PATH = "/nodes";
    private static final String SERVICES_PATH = "/services";

    /**
     * Supports sorting results by node name
     */
    private static Comparator<ConsulNodeInfo> nodeHealthComparator = new Comparator<ConsulNodeInfo>() {
        @Override
        public int compare(ConsulNodeInfo o1, ConsulNodeInfo o2) {
            return o1.node.compareTo(o2.node);
        }
    };

    /**
     * Supports sorting results by service name
     */
    private static Comparator<ConsulServiceHealth> serviceHealthComparator = new Comparator<ConsulServiceHealth>() {
        @Override
        public int compare(ConsulServiceHealth o1, ConsulServiceHealth o2) {
            return o1.serviceName.compareTo(o2.serviceName);
        }
    };

    /**
     * Supports sorting results by service name
     */
    private static Comparator<ConsulServiceInfo> serviceInfoComparator = new Comparator<ConsulServiceInfo>() {
        @Override
        public int compare(ConsulServiceInfo o1, ConsulServiceInfo o2) {
            return o1.name.compareTo(o2.name);
        }
    };

    /**
     * Gets one page of objects and supporting information via the REST client. On
     * success, returns a page of objects as String.
     * 
     * @param option   Specifies which item type to get
     * @param pageNum  Page number of results
     * @param pageSize Number of items per browser page
     * @return JSON block as String, see above.
     * @throws Exception On any error; e.g., Network failure.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String getItemListForPage(long userId, ConsulDataItem option, int pageNum, int pageSize,
        String dc) throws Exception {
        List itemList = null;
        switch (option) {
            case NODES:
                itemList = consulClient.getNodes(dc);
                Collections.sort(itemList, nodeHealthComparator);
                break;
            case DATACENTERS:
                itemList = consulClient.getDatacenters();
                break;
            default:
                MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                MDC.put("TargetEntity", "Consul");
                MDC.put("TargetServiceName", "Consul");
                MDC.put("ErrorCode", "300");
                MDC.put("ErrorCategory", "ERROR");
                MDC.put("ErrorDescription", "Getting page of items failed!");
                logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPage caught exception");
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
     * @param option  Item type to get
     * @return JSON with one page of objects; or an error.
     */
    protected String getItemListForPageWrapper(HttpServletRequest request, String dc, ConsulDataItem option) {
        String outboundJson = null;
        try {
            User appUser = UserUtils.getUserSession(request);
            if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
                throw new Exception("getItemListForPageWrapper: Failed to get application user");
            int pageNum = getRequestPageNumber(request);
            int pageSize = getRequestPageSize(request);
            outboundJson = getItemListForPage(appUser.getId(), option, pageNum, pageSize, dc);
        } catch (Exception ex) {
            // Remote service failed; build descriptive error message
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of items failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception");
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
     * @param request   HttpServletRequest
     * @param serviceId Service ID
     * @return List of ConsulServiceHealth objects as JSON
     * @throws Exception if serialization fails
     */
    @RequestMapping(value = {
            SERVICES_PATH + "/{serviceId}" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getServiceHealthDetails(HttpServletRequest request, @RequestParam String dc,
            @PathVariable String serviceId) throws Exception {
        preLogAudit(request);
        Object result = null;
        try {
            result = consulClient.getServiceHealth(dc, serviceId);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting service health details for " + serviceId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getServiceHealthDetails caught exception");
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
     * ECOMP-C does NOT provide an API to get the health of all services in one
     * request.
     * 
     * @param request HttpServletRequest
     * @return List of ConsulServiceHealth objects, as JSON
     * @throws Exception on serialization exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/serviceshealth" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getServicesHealth(HttpServletRequest request, @RequestParam String dc) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            List<ConsulServiceHealth> itemList = new ArrayList<>();
            List<ConsulServiceInfo> svcInfoList = consulClient.getServices(dc);
            for (ConsulServiceInfo csi : svcInfoList) {
                List<ConsulServiceHealth> csh = consulClient.getServiceHealth(dc, csi.name);
                itemList.addAll(csh);
            }
            Collections.sort(itemList, serviceHealthComparator);
            // Paginate
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize)
                itemList = getPageOfList(pageNum, pageSize, itemList);
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting services health failed!");
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
    @RequestMapping(value = { NODES_PATH }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getNodesInfo(HttpServletRequest request, @RequestParam String dc) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, dc, ConsulDataItem.NODES);
        postLogAudit(request);
        return json;
    }

    /**
     * Serves node services health details - not paginated.
     * 
     * @param request  HttpServletRequest
     * @param nodeName Node name
     * @return List of ConsulServiceHealth objects as JSON
     * @throws Exception if serialization fails
     */
    @RequestMapping(value = { NODES_PATH + "/{nodeName}" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getNodeServicesHealth(HttpServletRequest request, @RequestParam String dc,
            @PathVariable String nodeName) throws Exception {
        preLogAudit(request);
        Object result = null;
        try {
            result = consulClient.getNodeServicesHealth(dc, nodeName);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node services health for " + nodeName + " failed!");
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
    @RequestMapping(value = { "/datacenters" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDatacentersHealth(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, null, ConsulDataItem.DATACENTERS);
        postLogAudit(request);
        return json;
    }

    /**
     * Processes request to register a service for health checks.
     * 
     * @param request      HttpServletRequest
     * @param registration Consul service registration
     * @return URI of the newly registered resource
     * @throws Exception on serialization error
     */
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String registerService(HttpServletRequest request, @RequestBody ConsulHealthServiceRegistration registration)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (registration.services == null) {
                throw new Exception("services[] tag is mandatory");
            }

            List<EndpointCheck> checks = registration.services.get(0).checks;
            String service_name = registration.services.get(0).name;
            String service_port = registration.services.get(0).port;
            String service_address = registration.services.get(0).address;

            if (checks == null || service_port.isEmpty() || service_address.isEmpty() || service_name.isEmpty()) {
                throw new Exception("fields : [checks[], port, address, name] are mandatory");
            }
            for (EndpointCheck check : checks) {
                if (check.endpoint.isEmpty() || check.interval.isEmpty()) {
                    throw new Exception("Required fields : [endpoint, interval] in checks");
                }
            }
            result = new RestResponseSuccess(consulClient.registerService(registration));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Registering service failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "registerService caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Registering service failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "registerService caught exception");
            result = new RestResponseError("registerService failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Processes request to deregister a service for health checks.
     * 
     * @param request     HttpServletRequest
     * @param serviceName Consul service name to deregister
     * @return Success or error indicator
     * @throws Exception on serialization error
     */
    @RequestMapping(value = {
            "/deregister" + "/{serviceName}" }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String deregisterService(HttpServletRequest request, @PathVariable String serviceName) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            int code = consulClient.deregisterService(serviceName);
            result =
                new RestResponseSuccess("Deregistration yielded code " + Integer.toString(code));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "De-registering service " + serviceName + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deregisterService caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Consul");
            MDC.put("TargetServiceName", "Consul");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "De-registering service " + serviceName + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deregisterService caught exception");
            result = new RestResponseError("deregisterService failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
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
        MDC.put("TargetEntity", "Consul");
        MDC.put("TargetServiceName", "Consul");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger, request.getMethod() + request.getRequestURI());
    }
}
