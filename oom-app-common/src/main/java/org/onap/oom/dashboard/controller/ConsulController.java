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

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.oom.dashboard.exception.DashboardControllerException;
import org.onap.oom.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.oom.dashboard.model.ConsulNodeInfo;
import org.onap.oom.dashboard.model.ConsulServiceHealth;
import org.onap.oom.dashboard.model.ConsulServiceInfo;
import org.onap.oom.dashboard.model.ECTransportModel;
import org.onap.oom.dashboard.model.RestResponseError;
import org.onap.oom.dashboard.model.RestResponsePage;
import org.onap.oom.dashboard.model.RestResponseSuccess;
import org.onap.oom.dashboard.rest.IControllerRestClient;
import org.openecomp.portalsdk.core.domain.User;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.openecomp.portalsdk.core.util.SystemProperties;
import org.openecomp.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
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

	/**
	 * Enum for selecting an item type.
	 */
	public enum ConsulDataItem {
		SERVICE_INFO, SERVICE_HEALTH, NODES, DATACENTERS;
	}

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
	 * @param option
	 *            Specifies which item type to get
	 * @param pageNum
	 *            Page number of results
	 * @param pageSize
	 *            Number of items per browser page
	 * @return JSON block as String, see above.
	 * @throws DashboardControllerException,
	 *             JsonProcessingException On any error; e.g., Network failure.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getItemListForPage(long userId, ConsulDataItem option, int pageNum, int pageSize)
			throws DashboardControllerException, JsonProcessingException {
		IControllerRestClient restClient = getControllerRestClient(userId);
		List itemList = null;
		switch (option) {
		case NODES:
			itemList = restClient.getNodes();
			Collections.sort(itemList, nodeHealthComparator);
			break;
		case DATACENTERS:
			itemList = restClient.getDatacenters();
			break;
		default:
			throw new DashboardControllerException("getItemListForPage failed: unimplemented case: " + option.name());
		}

		// Shrink if needed
		if (itemList.size() > pageSize)
			itemList = getPageOfList(pageNum, pageSize, itemList);
		int pageCount = (int) Math.ceil((double) itemList.size() / pageSize);
		RestResponsePage<List> model = new RestResponsePage<>(itemList.size(), pageCount, itemList);
		String outboundJson = objectMapper.writeValueAsString(model);
		return outboundJson;
	}

	/**
	 * Gets one page of the specified items. This method traps exceptions and
	 * constructs an appropriate JSON block to report errors.
	 * 
	 * @param request
	 *            Inbound request
	 * @param option
	 *            Item type to get
	 * @return JSON with one page of objects; or an error.
	 */
	protected String getItemListForPageWrapper(HttpServletRequest request, ConsulDataItem option) {
		String outboundJson = null;
		try {
			User appUser = UserUtils.getUserSession(request);
			if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
				throw new DashboardControllerException("getItemListForPageWrapper: Failed to get application user");
			int pageNum = getRequestPageNumber(request);
			int pageSize = getRequestPageSize(request);
			outboundJson = getItemListForPage(appUser.getId(), option, pageNum, pageSize);
		} catch (Exception ex) {
			// Remote service failed; build descriptive error message
			logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception", ex);
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
	 * Serves all service details.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ConsulServiceInfo objects, as JSON
	 * @throws JsonProcessingException
	 *             if serialization fails
	 */
	@RequestMapping(value = { SERVICES_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getServices(HttpServletRequest request) throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		Object result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			List<ConsulServiceInfo> itemList = restClient.getServices();
			Collections.sort(itemList, serviceInfoComparator);
			result = itemList;
		} catch (Throwable t) {
			result = new RestResponseError("getServices failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Serves service health details - not paginated.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param serviceId
	 *            Service ID
	 * @return List of ConsulServiceHealth objects as JSON
	 * @throws JsonProcessingException
	 *             if serialization fails
	 */
	@RequestMapping(value = {
			SERVICES_PATH + "/{serviceId}" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getServiceHealthDetails(HttpServletRequest request, @PathVariable String serviceId)
			throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		Object result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.getServiceHealth(serviceId);
		} catch (Throwable t) {
			result = new RestResponseError("getServiceHealthDetails failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Serves service health historical data - not paginated.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param serviceName
	 *            Service name as path parameter
	 * @param start
	 *            Earliest date-time as an ISO 8061 value, such as
	 *            2007-12-03T10:15:30+01:00
	 * @param end
	 *            Latest date-time as an ISO 8061 value, such as
	 *            2007-12-03T10:15:30+01:00
	 * @return List of ConsulServiceHealth objects as JSON
	 * @throws JsonProcessingException
	 *             if serialization fails
	 */
	@RequestMapping(value = { "/svchist/{serviceName}" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getServiceHealthHistory(HttpServletRequest request, //
			@PathVariable String serviceName, //
			@RequestParam String start, //
			@RequestParam String end) throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		Object result = null;
		try {
			Instant startDateTime = Instant.parse(start);
			Instant endDateTime = Instant.parse(end);
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.getServiceHealthHistory(serviceName, startDateTime, endDateTime);
		} catch (HttpStatusCodeException e) {
			// Rare, but can happen
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			// Work around the hack to report no-match.
			result = new RestResponseError("getServiceHealthHistory failed: " + t.getMessage());
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Serves one page of service health information by getting all service names,
	 * then iterating over them to get the health of each service.
	 * 
	 * ECOMP-C does NOT provide an API to get the health of all services in one
	 * request.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ConsulServiceHealth objects, as JSON
	 * @throws JsonProcessingException
	 *             on serialization exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/serviceshealth" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getServicesHealth(HttpServletRequest request) throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			List<ConsulServiceHealth> itemList = new ArrayList<>();
			IControllerRestClient restClient = getControllerRestClient(request);
			List<ConsulServiceInfo> svcInfoList = restClient.getServices();
			for (ConsulServiceInfo csi : svcInfoList) {
				List<ConsulServiceHealth> csh = restClient.getServiceHealth(csi.name);
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
		} catch (Throwable t) {
			result = new RestResponseError("getServicesHealth failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Serves one page of node information.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ConsulNodeInfo objects, as JSON
	 */
	@RequestMapping(value = { NODES_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getNodesInfo(HttpServletRequest request) {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		String json = getItemListForPageWrapper(request, ConsulDataItem.NODES);
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return json;
	}

	/**
	 * Serves node services health details - not paginated.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param nodeName
	 *            Node name
	 * @return List of ConsulServiceHealth objects as JSON
	 * @throws JsonProcessingException
	 *             if serialization fails
	 */
	@RequestMapping(value = { NODES_PATH + "/{nodeName}" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getNodeServicesHealth(HttpServletRequest request, @PathVariable String nodeName)
			throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		Object result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.getNodeServicesHealth(nodeName);
		} catch (Throwable t) {
			result = new RestResponseError("getNodeServicesHealth failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Serves one page of datacenters health.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ConsulHealthStatus objects
	 */
	@RequestMapping(value = { "/datacenters" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getDatacentersHealth(HttpServletRequest request) {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		String json = getItemListForPageWrapper(request, ConsulDataItem.DATACENTERS);
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return json;
	}

	/**
	 * Processes request to register a service for health checks.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param registration
	 *            Consul service registration
	 * @return URI of the newly registered resource
	 * @throws JsonProcessingException
	 *             on serialization error
	 */
	@RequestMapping(value = { "/register" }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String registerService(HttpServletRequest request, @RequestBody ConsulHealthServiceRegistration registration)
			throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			URI uri = restClient.registerService(registration);
			result = new RestResponseSuccess(uri.toString());
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("registerService failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Processes request to deregister a service for health checks.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param serviceName
	 *            Consul service name to deregister
	 * @return Success or error indicator
	 * @throws JsonProcessingException
	 *             on serialization error
	 */
	@RequestMapping(value = {
			"/deregister" + "/{serviceName}" }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String deregisterService(HttpServletRequest request, @PathVariable String serviceName)
			throws JsonProcessingException {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			int code = restClient.deregisterService(serviceName);
			result = new RestResponseSuccess("Deregistration yielded code " + Integer.toString(code));
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("deregisterService failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}
}
