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
package org.onap.ccsdk.dashboard.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.domain.ControllerEndpoint;
import org.onap.ccsdk.dashboard.exception.DashboardControllerException;
import org.onap.ccsdk.dashboard.model.ControllerEndpointCredentials;
import org.onap.ccsdk.dashboard.model.ControllerEndpointTransport;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponseSuccess;
import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.openecomp.portalsdk.core.domain.User;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.openecomp.portalsdk.core.web.support.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This controller maps requests for the application's landing page, which is an
 * Angular single-page application.
 */
@Controller
@RequestMapping("/")
public class DashboardHomeController extends DashboardRestrictedBaseController {

	/**
	 * This path is embedded in the database, so it's nontrivial to change.
	 */
	public static final String APP_CONTEXT_PATH = "/ecd";

	private EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(DashboardHomeController.class);

	@Autowired
	private ControllerEndpointService controllerEndpointService;

	/**
	 * For general use in these methods
	 */
	private final ObjectMapper mapper;

	private static final String CONTROLLERS_PATH = "controllers";

	/**
	 * Spring autowires fields AFTER the constructor is called.
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
	@RequestMapping(value = { APP_CONTEXT_PATH }, method = RequestMethod.GET)
	public ModelAndView dbcDefaultController() {
		// a model is only useful for JSP; this app is angular.
		return new ModelAndView("oom_home_tdkey");
	}

	/**
	 * Gets the available controller endpoints.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of ControllerEndpointTransport objects, or an error on failure
	 */
	@RequestMapping(value = { CONTROLLERS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getControllers(HttpServletRequest request) {
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		String outboundJson = null;
		// Static data
		ControllerEndpointCredentials[] configured = getControllerEndpoints();
		try {
			User appUser = UserUtils.getUserSession(request);
			if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
				throw new DashboardControllerException("getControllers: Failed to get application user");
			ControllerEndpointCredentials selectedInDb = getOrSetControllerEndpointSelection(appUser.getId());
			// Built result from properties
			ArrayList<ControllerEndpointTransport> list = new ArrayList<>();
			for (ControllerEndpointCredentials ctrl : configured) {
				// Check if this is the selected endpoint in DB
				boolean selected = (selectedInDb != null && selectedInDb.getUrl() != null
						&& selectedInDb.getUrl().equals(ctrl.getUrl()));
				// Result has no privileged information
				ControllerEndpointTransport transport = new ControllerEndpointTransport(selected, ctrl.getName(),
						ctrl.getUrl());
				list.add(transport);
			}
			outboundJson = mapper.writeValueAsString(list);
		} catch (Exception ex) {
			RestResponseError response = new RestResponseError("Failed to get controller endpoint list", ex);
			outboundJson = response.toJson();
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
	 * @throws DashboardControllerException,
	 *             if application user is not found
	 * @throws JsonProcessingException
	 *             If serialization fails
	 */
	@RequestMapping(value = { CONTROLLERS_PATH }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String setControllerSelection(HttpServletRequest request, @RequestBody ControllerEndpointTransport endpoint)
			throws DashboardControllerException, JsonProcessingException {
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		String outboundJson = null;
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
			throw new DashboardControllerException("setControllerSelection: Failed to get application user");
		ControllerEndpoint dbEntry = new ControllerEndpoint(appUser.getId(), endpoint.getName(), endpoint.getUrl());
		controllerEndpointService.updateControllerEndpointSelection(dbEntry);
		RestResponseSuccess success = new RestResponseSuccess("Updated selection to " + endpoint.getName());
		outboundJson = mapper.writeValueAsString(success);
		return outboundJson;
	}
}
