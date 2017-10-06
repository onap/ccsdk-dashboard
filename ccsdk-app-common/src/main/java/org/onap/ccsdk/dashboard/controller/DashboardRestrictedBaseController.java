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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.exception.DashboardControllerException;
import org.onap.ccsdk.dashboard.rest.ControllerRestClientMockImpl;
import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.onap.ccsdk.dashboard.domain.ControllerEndpoint;
import org.onap.ccsdk.dashboard.rest.IControllerRestClient;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.ccsdk.dashboard.model.ControllerEndpointCredentials;
import org.onap.ccsdk.dashboard.rest.ControllerRestClientImpl;
import org.openecomp.portalsdk.core.controller.RestrictedBaseController;
import org.openecomp.portalsdk.core.domain.User;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.openecomp.portalsdk.core.web.support.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This base class provides utility methods to child controllers.
 */
public class DashboardRestrictedBaseController extends RestrictedBaseController {

	/**
	 * Logger that conforms with ECOMP guidelines
	 */
	private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(DashboardRestrictedBaseController.class);

	/**
	 * Application name
	 */
	protected static final String APP_NAME = "ecd-app";

	/**
	 * EELF-approved format
	 */
	protected static final DateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	/**
	 * Query parameter for desired page number
	 */
	protected static final String PAGE_NUM_QUERY_PARAM = "pageNum";

	/**
	 * Query parameter for desired items per page
	 */
	protected static final String PAGE_SIZE_QUERY_PARAM = "viewPerPage";

	/**
	 * For general use in these methods and subclasses
	 */
	protected final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Application properties - NOT available to constructor.
	 */
	@Autowired
	private DashboardProperties appProperties;

	/**
	 * For getting selected controller
	 */
	@Autowired
	private ControllerEndpointService controllerEndpointService;

	/**
	 * Hello Spring, here's your no-arg constructor.
	 */
	public DashboardRestrictedBaseController() {
		// Do not serialize null values
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	/**
	 * Access method for subclasses.
	 * 
	 * @return DbcappProperties object that was autowired by Spring.
	 */
	protected DashboardProperties getAppProperties() {
		return appProperties;
	}

	/**
	 * Gets the requested page number from a query parameter in the
	 * HttpServletRequest. Defaults to 1, which is useful to allow manual
	 * testing of endpoints without supplying those pesky parameters.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Value of query parameter {@link #PAGE_NUM_QUERY_PARAM}; 1 if not
	 *         found.
	 */
	protected int getRequestPageNumber(HttpServletRequest request) {
		int pageNum = 1;
		String param = request.getParameter(PAGE_NUM_QUERY_PARAM);
		if (param != null)
			pageNum = Integer.parseInt(param);
		return pageNum;
	}

	/**
	 * Gets the requested page size from a query parameter in the
	 * HttpServletRequest. Defaults to 50, which is useful to allow manual
	 * testing of endpoints without supplying those pesky parameters.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Value of query parameter {@link #PAGE_SIZE_QUERY_PARAM}; 50 if
	 *         not found.
	 */
	protected int getRequestPageSize(HttpServletRequest request) {
		int pageSize = 50;
		String param = request.getParameter(PAGE_SIZE_QUERY_PARAM);
		if (param != null)
			pageSize = Integer.parseInt(param);
		return pageSize;
	}

	/**
	 * Gets the items for the specified page from the specified list.
	 * 
	 * @param pageNum
	 *            Page number requested by user, indexed from 1
	 * @param pageSize
	 *            Number of items per page
	 * @param itemList
	 *            List of items to adjust
	 * @return List of items; empty list if from==to
	 */
	@SuppressWarnings("rawtypes")
	protected static List getPageOfList(final int pageNum, final int pageSize, final List itemList) {
		int firstIndexOnThisPage = pageSize * (pageNum - 1);
		int firstIndexOnNextPage = pageSize * pageNum;
		int fromIndex = firstIndexOnThisPage < itemList.size() ? firstIndexOnThisPage : itemList.size();
		int toIndex = firstIndexOnNextPage < itemList.size() ? firstIndexOnNextPage : itemList.size();
		return itemList.subList(fromIndex, toIndex);
	}

	/**
	 * Gets all configured controllers from properties.
	 * 
	 * @return Array of ControllerEndpointRestricted objects
	 * @throws IllegalStateException
	 *             if a required property is not found
	 */
	protected ControllerEndpointCredentials[] getControllerEndpoints() {
		final String[] controllerKeys = appProperties.getCsvListProperty(DashboardProperties.CONTROLLER_KEY_LIST);
		ControllerEndpointCredentials[] controllers = new ControllerEndpointCredentials[controllerKeys.length];
		for (int i = 0; i < controllerKeys.length; ++i) {
			String key = controllerKeys[i];
			final String name = appProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_NAME);
			final String url = appProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_URL);
			final String user = appProperties.getControllerProperty(key,
					DashboardProperties.CONTROLLER_SUBKEY_USERNAME);
			final String pass = appProperties.getControllerProperty(key,
					DashboardProperties.CONTROLLER_SUBKEY_PASSWORD);
			final boolean encr = Boolean.parseBoolean (
					appProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_ENCRYPTED));
			logger.debug(EELFLoggerDelegate.debugLogger, "getConfiguredControllers: key {} yields url {}", key, url);
			controllers[i] = new ControllerEndpointCredentials(false, name, url, user, pass, encr);
		}
		return controllers;
	}

	/**
	 * Gets the controller endpoint for the specified user ID. Chooses the first
	 * one from properties if the user has not selected one previously.
	 * 
	 * @param userId
	 *            Database User ID
	 * @return ControllerEndpointCredentials for the specified user
	 */
	protected ControllerEndpointCredentials getOrSetControllerEndpointSelection(long userId) {
		// Always need the complete list from properties
		ControllerEndpointCredentials[] configured = getControllerEndpoints();
		// See if the database has an entry for this user
		ControllerEndpoint dbEntry = controllerEndpointService.getControllerEndpointSelection(userId);
		// If no row found DAO returns an object with null entries.
		if (dbEntry == null || dbEntry.getName() == null) {
			// Arbitrarily choose the first one
			ControllerEndpointCredentials first = configured[0];
			dbEntry = new ControllerEndpoint(userId, first.getName(), first.getUrl());
			controllerEndpointService.updateControllerEndpointSelection(dbEntry);
		}
		// Fetch complete details for the selected item
		ControllerEndpointCredentials selected = null;
		for (ControllerEndpointCredentials cec : configured) {
			if (dbEntry.getUrl().equals(cec.getUrl())) {
				selected = cec;
				break;
			}
		}
		// Defend against a stale database entry.
		if (selected == null) {
			selected = configured[0];
			dbEntry = new ControllerEndpoint(userId, selected.getName(), selected.getUrl());
			controllerEndpointService.updateControllerEndpointSelection(dbEntry);
		}
		return selected;
	}

	/**
	 * Convenience method that gets the user ID from the session and fetches the
	 * REST client. Factors code out of subclass methods.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return REST client appropriate for the user
	 * @throws DashboardControllerException
	 */
	protected IControllerRestClient getControllerRestClient(HttpServletRequest request) throws DashboardControllerException {
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
			throw new DashboardControllerException("getControllerRestClient: Failed to get application user");
		return getControllerRestClient(appUser.getId());
	}

	/**
	 * Gets a REST client; either a mock client (returns canned data), or a real
	 * client with appropriate credentials from properties.
	 * 
	 * @return REST client.
	 * @throws DashboardControllerException on any failure; e.g., if the password cannot be decrypted.
	 */
	protected IControllerRestClient getControllerRestClient(long userId) throws DashboardControllerException {
		IControllerRestClient result = null;
		// Be robust to missing development-only property
		boolean mock = false;
		if (appProperties.containsProperty(DashboardProperties.CONTROLLER_MOCK_DATA))
			mock = appProperties.getBooleanProperty(DashboardProperties.CONTROLLER_MOCK_DATA);
		if (mock) {
			result = new ControllerRestClientMockImpl();
		} else {
			try {
			ControllerEndpointCredentials details = getOrSetControllerEndpointSelection(userId);
			final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
			result = new ControllerRestClientImpl(details.getUrl(), details.getUsername(), clearText);
			}
			catch (Exception ex) {
				logger.error("getControllerRestClient failed", ex);
				throw new DashboardControllerException(ex);
			}
		}
		return result;
	}

}
