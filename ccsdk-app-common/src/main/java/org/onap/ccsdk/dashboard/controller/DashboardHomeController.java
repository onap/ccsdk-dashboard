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
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.onap.ccsdk.dashboard.domain.ControllerEndpoint;
import org.onap.ccsdk.dashboard.domain.EcdComponent;
import org.onap.ccsdk.dashboard.model.ControllerEndpointCredentials;
import org.onap.ccsdk.dashboard.model.ControllerEndpointTransport;
import org.onap.ccsdk.dashboard.model.ControllerOpsTools;
import org.onap.ccsdk.dashboard.model.EcdAppComponent;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponseSuccess;
import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This controller maps requests for the application's landing page, which is
 * an Angular single-page application.
 */
@Controller
@RequestMapping("/")
public class DashboardHomeController extends DashboardRestrictedBaseController {

	private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(DashboardHomeController.class);

	@Autowired
	private ControllerEndpointService controllerEndpointService;

	/**
	 * For general use in these methods
	 */
	private final ObjectMapper mapper;

	private static Date begin, end;
	private static final String CONTROLLERS_PATH = "controllers";
	private static final String COMPONENTS_PATH = "components";
	private static final String USER_APPS_PATH = "user-apps";
	private static final String OPS_PATH = "ops";
	private static final String APP_LABEL = "app-label";
	/**
	 * Never forget that Spring autowires fields AFTER the constructor is
	 * called.
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
	@RequestMapping(value = { "/ecd" }, method = RequestMethod.GET)
	public ModelAndView dbcDefaultController() {
		// a model is only useful for JSP; this app is angular.
		return new ModelAndView("ecd_home_tdkey");
	}

	/** 
	 * Gets the available blueprint component names
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of component name strings, or an error on
	 *         failure
	 */
	@RequestMapping(value = { COMPONENTS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getComponents(HttpServletRequest request) {
		preLogAudit(request);
		String outboundJson = ""; // "['MSO','CLAMP','APPC','ECOMPSCHEDULER','POLICY']";
		try {	
			HttpSession session = request.getSession(true);
			Set<String> userApps = (Set<String>)session.getAttribute("authComponents");
			if (userApps == null) {
				userApps = new TreeSet<String>();
			}
			List<EcdComponent> filterList = new ArrayList<EcdComponent>();
			List<EcdAppComponent> ecdApps = new ArrayList<EcdAppComponent>();
			
			List<EcdComponent> dbResult =
					controllerEndpointService.getComponents();
			
			List dcaeCompList =
					(List) dbResult.stream().filter(s -> ((EcdComponent) s).contains("dcae")).collect(Collectors.toList());
			
			if (!userApps.isEmpty()) { // non-admin role level
				for(String userRole : userApps) {
					if (userRole.equalsIgnoreCase("dcae")) {
						if (dcaeCompList != null && !dcaeCompList.isEmpty()) {
							EcdAppComponent dcaeAppComponent = new EcdAppComponent("DCAE", dcaeCompList);
							ecdApps.add(dcaeAppComponent);
						}
					} else {
						List tmpItemList =
							(List) dbResult.stream().filter(s -> ((EcdComponent) s).contains(userRole)).collect(Collectors.toList());
						if (tmpItemList != null) {
							logger.debug(">>>> adding filtered items");
							filterList.addAll(tmpItemList);
						}
					}
				}
				if (!filterList.isEmpty()) {
					EcdAppComponent ecdAppComponent = new EcdAppComponent("ECOMP", filterList);
					ecdApps.add(ecdAppComponent);
				}
			} else {
				// lookup "dcae" in the db component list
				if (dcaeCompList != null && !dcaeCompList.isEmpty()) {
					EcdAppComponent dcaeAppComponent = new EcdAppComponent("DCAE", dcaeCompList);
					ecdApps.add(dcaeAppComponent);
				}
				if (dbResult != null && !dbResult.isEmpty()) {
					EcdAppComponent ecdAppComponent = new EcdAppComponent("ECOMP", dbResult);
					ecdApps.add(ecdAppComponent);
				}
			}
			outboundJson = mapper.writeValueAsString(ecdApps);
		} catch (Exception ex) {
			MDC.put(SystemProperties.STATUS_CODE, "ERROR");
			MDC.put("TargetEntity", "DashboardHomeController");
			MDC.put("TargetServiceName", "DashboardHomeController");
			MDC.put("ErrorCode", "300");
			MDC.put("ErrorCategory", "ERROR");
			MDC.put("ErrorDescription", "Get components failed!");
			logger.error(EELFLoggerDelegate.errorLogger, "Failed to get components list");
			RestResponseError response = new RestResponseError("Failed to get components list", ex);
			outboundJson = response.toJson();
		} finally {
			postLogAudit(request);
		}
		return outboundJson;
	}

	/**
	 * Get the application label - name + environment 
	 * 
	 */
	@RequestMapping(value = {APP_LABEL}, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getAppLabel(HttpServletRequest request) throws Exception {	
		return mapper.writeValueAsString(appProperties.getPropertyDef(appProperties.CONTROLLER_IN_ENV, "NA"));
		//return mapper.writeValueAsString(systemProperties.getAppDisplayName());
	}
	/** 
	 * Gets the application name(s) for the authenticated user
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of component name strings, or an error on
	 *         failure
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { USER_APPS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getUserApps(HttpServletRequest request) {
		preLogAudit(request);
		String outboundJson = ""; // "['MSO','CLAMP','APPC','ECOMPSCHEDULER','POLICY']";
		try {	
			HttpSession session = request.getSession(true);
			Set<String> userApps = (Set<String>)session.getAttribute("authComponents");
			if (userApps == null) {
				userApps = new TreeSet<String>();
			}
			outboundJson = mapper.writeValueAsString(userApps);
		} catch (Exception ex) {
			MDC.put(SystemProperties.STATUS_CODE, "ERROR");
			MDC.put("TargetEntity", "DashboardHomeController");
			MDC.put("TargetServiceName", "DashboardHomeController");
			MDC.put("ErrorCode", "300");
			MDC.put("ErrorCategory", "ERROR");
			MDC.put("ErrorDescription", "Get User Apps failed!");
			logger.error(EELFLoggerDelegate.errorLogger, "Failed to get apps list");
			RestResponseError response = new RestResponseError("Failed to get apps list", ex);
			outboundJson = response.toJson();
		} finally {
			postLogAudit(request);
		}
		return outboundJson;
	}
    /**
     * Sets the controller endpoint selection for the user.
     *
     * @param request
     *            HttpServletRequest
     * @param endpoint
     *            Body with endpoint details
     * @return Result indicating success or failure
     * @throws Exception
     *             if application user is not found
     */
    @RequestMapping(value = { COMPONENTS_PATH }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String insertComponent(HttpServletRequest request, @RequestBody EcdComponent newComponent)
            throws Exception {
        preLogAudit(request);
        String outboundJson = null;
        controllerEndpointService.insertComponent(newComponent);
        RestResponseSuccess success = new RestResponseSuccess("Inserted new component with name " + newComponent.getCname());
        outboundJson = mapper.writeValueAsString(success);
        postLogAudit(request);
        return outboundJson;
    }

    /**
     * Gets the OPS Tools URLs from dashboard properties
     * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ControllerOpsTools objects, or an error on
	 *         failure
	 * @throws Exception
     */
	@RequestMapping(value = { OPS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getOpsToolUrls(HttpServletRequest request) {
		preLogAudit(request);
		String outboundJson = null;
		try {
			List<ControllerOpsTools> opsList = getControllerOpsTools();		
			outboundJson = mapper.writeValueAsString(opsList);
		} catch (Exception ex) {
			MDC.put(SystemProperties.STATUS_CODE, "ERROR");
			MDC.put("TargetEntity", "DashboardHomeController");
			MDC.put("TargetServiceName", "DashboardHomeController");
			MDC.put("ErrorCode", "300");
			MDC.put("ErrorCategory", "ERROR");
			MDC.put("ErrorDescription", "Get Ops Tools URLs failed!");
			logger.error(EELFLoggerDelegate.errorLogger, "Failed to get Ops Tools URL list");
			RestResponseError response = new RestResponseError("Failed to get Ops Tools URL list", ex);
			outboundJson = response.toJson();
		} finally {
			postLogAudit(request);
		}
		return outboundJson;		
	}
	
	// get sites
	// get cfy, cnsl URLs
	// get cfy tenants
	// get cfy secret value for k8s ip per tenant
	// construct models TenantOpsCluster, SiteOpsToolLinks
	// return final model
	/**
	 * Gets the available controller endpoints.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ControllerEndpointTransport objects, or an error on
	 *         failure
	 * @throws Exception
	 *             if application user is not found
	 */
	@RequestMapping(value = { CONTROLLERS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getControllers(HttpServletRequest request) {
		preLogAudit(request);
		String outboundJson = null;
		// Static data
		ControllerEndpointCredentials[] configured = getControllerEndpoints();
		try {
			User appUser = UserUtils.getUserSession(request);
			if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0) {
				MDC.put(SystemProperties.STATUS_CODE, "ERROR");
				MDC.put("TargetEntity", "DashboardHomeController");
				MDC.put("TargetServiceName", "DashboardHomeController");
				MDC.put("ErrorCode", "300");
				MDC.put("ErrorCategory", "ERROR");
				MDC.put("ErrorDescription", "Get controllers failed!");
				logger.error(EELFLoggerDelegate.errorLogger, "Failed to get application user");
				throw new Exception("getControllers: Failed to get application user");
			}
			ControllerEndpointCredentials selectedInDb = getOrSetControllerEndpointSelection(appUser.getId());
			// Built result from properties
			ArrayList<ControllerEndpointTransport> list = new ArrayList<>();
			for (ControllerEndpointCredentials ctrl : configured) {
				// Check if this is the selected endpoint in DB
				boolean selected = (selectedInDb != null && selectedInDb.getUrl() != null
						&& selectedInDb.getUrl().equals(ctrl.getUrl()));
				// Result has no privileged information
				ControllerEndpointTransport transport = new ControllerEndpointTransport(selected, ctrl.getName(),
						ctrl.getUrl(), ctrl.getInventoryUrl(), ctrl.getDhandlerUrl(), ctrl.getConsulUrl());
				list.add(transport);
			}
			outboundJson = mapper.writeValueAsString(list);
		} catch (Exception ex) {
			MDC.put(SystemProperties.STATUS_CODE, "ERROR");
			MDC.put("TargetEntity", "DashboardHomeController");
			MDC.put("TargetServiceName", "DashboardHomeController");
			MDC.put("ErrorCode", "300");
			MDC.put("ErrorCategory", "ERROR");
			MDC.put("ErrorDescription", "Get controllers failed!");
			logger.error(EELFLoggerDelegate.errorLogger, "Failed to get controller endpoint list");
			RestResponseError response = new RestResponseError("Failed to get controller endpoint list", ex);
			outboundJson = response.toJson();
		} finally {
			postLogAudit(request);
		}
		return outboundJson;
	}

	/**
	 * Sets the controller endpoint selection for the user.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param endpoint
	 *            Body with endpoint details
	 * @return Result indicating success or failure
	 * @throws Exception
	 *             if application user is not found
	 */
	@RequestMapping(value = { CONTROLLERS_PATH }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String setControllerSelection(HttpServletRequest request, @RequestBody ControllerEndpointTransport endpoint)
			throws Exception {
		preLogAudit(request);
		String outboundJson = null;
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0) {
			MDC.put(SystemProperties.STATUS_CODE, "ERROR");
			MDC.put("TargetEntity", "DashboardHomeController");
			MDC.put("TargetServiceName", "DashboardHomeController");
			MDC.put("ErrorCode", "300");
			MDC.put("ErrorCategory", "ERROR");
			MDC.put("ErrorDescription", "Set controllers failed!");
			logger.error(EELFLoggerDelegate.errorLogger, "Failed to get application user");
			postLogAudit(request);
			throw new Exception("setControllerSelection: Failed to get application user");
		}
		ControllerEndpoint dbEntry = new ControllerEndpoint(appUser.getId(), endpoint.getName(), endpoint.getUrl(), endpoint.getInventoryUrl(), endpoint.getDhandlerUrl());
		controllerEndpointService.updateControllerEndpointSelection(dbEntry);
		RestResponseSuccess success = new RestResponseSuccess("Updated selection to " + endpoint.getName());
		outboundJson = mapper.writeValueAsString(success);
		postLogAudit(request);
		return outboundJson;
	}

	public void preLogAudit(HttpServletRequest request) {
		begin = new Date();
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
		MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
		MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
		//logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
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
		logger.info(EELFLoggerDelegate.metricsLogger, request.getMethod() + request.getRequestURI());
	}
}
