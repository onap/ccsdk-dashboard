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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.domain.ControllerEndpoint;
import org.onap.ccsdk.dashboard.model.ControllerEndpointCredentials;
import org.onap.ccsdk.dashboard.model.ControllerOpsTools;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.CloudifyMockClientImpl;
import org.onap.ccsdk.dashboard.rest.CloudifyRestClientImpl;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.ccsdk.dashboard.rest.ConsulMockClientImpl;
import org.onap.ccsdk.dashboard.rest.ConsulRestClientImpl;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClientImpl;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.ccsdk.dashboard.rest.RestInventoryClientImpl;
import org.onap.ccsdk.dashboard.rest.RestInventoryClientMockImpl;
import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.controller.RestrictedBaseController;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * This base class provides utility methods to child controllers.
 */
public class DashboardRestrictedBaseController extends RestrictedBaseController {

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
	protected DashboardProperties appProperties;
	
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
		// Register Jdk8Module() for Stream and Optional types
		objectMapper.registerModule(new Jdk8Module());
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
		final String[] controllerKeys = DashboardProperties.getCsvListProperty(DashboardProperties.CONTROLLER_KEY_LIST);
		ControllerEndpointCredentials[] controllers = new ControllerEndpointCredentials[controllerKeys.length];
		for (int i = 0; i < controllerKeys.length; ++i) {
			String key = controllerKeys[i];
			final String name = DashboardProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_NAME);
			final String url = DashboardProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_URL);
			final String inventoryUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_INVENTORY_URL);
			final String dhandlerUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_DHANDLER_URL);
			final String consulUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_CONSUL_URL);
			final String user = DashboardProperties.getControllerProperty(key,
					DashboardProperties.CONTROLLER_SUBKEY_USERNAME);
			final String pass = DashboardProperties.getControllerProperty(key,
					DashboardProperties.CONTROLLER_SUBKEY_PASS);
			final boolean encr = Boolean.parseBoolean(
					DashboardProperties.getControllerProperty(key, DashboardProperties.CONTROLLER_SUBKEY_ENCRYPTED));
			controllers[i] = new ControllerEndpointCredentials(false, name, url, inventoryUrl, dhandlerUrl, consulUrl, user, pass, encr);
		}
		return controllers;
	}

	/**
	 * Get the list of configured OPS Tools URLs from dashboard properties
	 * 
	 * @return Array of ControllerOpsTools objects
	 * @throws IllegalStateException
	 *             if a required property is not found
	 */
	protected List<ControllerOpsTools> getControllerOpsTools() {
		List<ControllerOpsTools> opsList = new ArrayList<>();
		final String[] controllerKeys = DashboardProperties.getCsvListProperty(DashboardProperties.CONTROLLER_KEY_LIST);
		String key = controllerKeys[0];
		final String cfyId = DashboardProperties.OPS_CLOUDIFY_URL.split("\\.")[1];
		final String cfyUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.OPS_CLOUDIFY_URL);
		final String k8Id = DashboardProperties.OPS_K8S_URL.split("\\.")[1];
		final String k8Url = DashboardProperties.getControllerProperty(key, DashboardProperties.OPS_K8S_URL);
		final String grfId = DashboardProperties.OPS_GRAFANA_URL.split("\\.")[1];
		final String grfUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.OPS_GRAFANA_URL);
		final String cnslId = DashboardProperties.OPS_CONSUL_URL.split("\\.")[1];
		final String cnslUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.OPS_CONSUL_URL);
		final String promId = DashboardProperties.OPS_PROMETHEUS_URL.split("\\.")[1];
		final String promUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.OPS_PROMETHEUS_URL);
		final String dbclId = DashboardProperties.OPS_DBCL_URL.split("\\.")[1];
		final String dbclUrl = DashboardProperties.getControllerProperty(key, DashboardProperties.OPS_DBCL_URL);
		opsList.add(new ControllerOpsTools(cfyId, cfyUrl));
		opsList.add(new ControllerOpsTools(k8Id, k8Url));
		opsList.add(new ControllerOpsTools(grfId, grfUrl));
		opsList.add(new ControllerOpsTools(cnslId, cnslUrl));
		opsList.add(new ControllerOpsTools(promId, promUrl));
		opsList.add(new ControllerOpsTools(dbclId, dbclUrl));
		
		return opsList;		
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
			dbEntry = new ControllerEndpoint(userId, first.getName(), first.getUrl(), first.getInventoryUrl(), first.getDhandlerUrl());
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
			dbEntry = new ControllerEndpoint(userId, selected.getName(), selected.getUrl(), selected.getInventoryUrl(), selected.getDhandlerUrl());
			controllerEndpointService.updateControllerEndpointSelection(dbEntry);
		}
		return selected;
	}

	protected ControllerEndpointCredentials getOrSetControllerEndpointSelection() {
		ControllerEndpointCredentials[] configured = getControllerEndpoints();
		return configured[0];
	}
	/**
	 * Convenience method that gets the user ID from the session and fetches the
	 * REST client. Factors code out of subclass methods.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return REST client appropriate for the user
	 */
	protected CloudifyClient getCloudifyRestClient(HttpServletRequest request) throws Exception {
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getId() == null )
			throw new Exception("getCloudifyRestClient: Failed to get application user");
		return getCloudifyRestClient(appUser.getId());
	}

	/**
	 * Gets a REST client; either a mock client (returns canned data), or a real
	 * client with appropriate credentials from properties.
	 * 
	 * @return REST client.
	 */
	protected CloudifyClient getCloudifyRestClient(long userId) throws Exception {
		CloudifyClient result = null;
		// Be robust to missing development-only property
		boolean mock = false;
		if (DashboardProperties.containsProperty(DashboardProperties.CONTROLLER_MOCK_DATA))
			mock = DashboardProperties.getBooleanProperty(DashboardProperties.CONTROLLER_MOCK_DATA);
		if (mock) {
			result = new CloudifyMockClientImpl();
		} else {
			ControllerEndpointCredentials details = getOrSetControllerEndpointSelection(userId);
			final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
			result = new CloudifyRestClientImpl(details.getUrl(), details.getUsername(), clearText);
		}
		return result;
	}
	
	/**
	 * Gets a REST client; either a mock client (returns canned data), or a real
	 * client with appropriate credentials from properties.
	 * 
	 * @return REST client.
	 */
	protected CloudifyClient getCloudifyRestClient() throws Exception {
		CloudifyClient result = null;
		// Be robust to missing development-only property
		boolean mock = false;
		if (DashboardProperties.containsProperty(DashboardProperties.CONTROLLER_MOCK_DATA))
			mock = DashboardProperties.getBooleanProperty(DashboardProperties.CONTROLLER_MOCK_DATA);
		if (mock) {
			result = new CloudifyMockClientImpl();
		} else {
			ControllerEndpointCredentials details = getOrSetControllerEndpointSelection();
			final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
			result = new CloudifyRestClientImpl(details.getUrl(), details.getUsername(), clearText);
		}
		return result;
	}
	
	/**
	 * Convenience method that gets the user ID from the session and fetches the
	 * REST client. Factors code out of subclass methods.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return REST client appropriate for the user
	 */
	protected ConsulClient getConsulRestClient(HttpServletRequest request) throws Exception {
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getId() == null )
			throw new Exception("getControllerRestClient: Failed to get application user");
		return getConsulRestClient(appUser.getId());
	}
	
	/**
	 * Gets a REST client; either a mock client (returns canned data), or a real
	 * client with appropriate credentials from properties.
	 * 
	 * @return REST client.
	 */
	protected ConsulClient getConsulRestClient(long userId) throws Exception {
		ConsulClient result = null;
		// Be robust to missing development-only property
		boolean mock = false;
		if (DashboardProperties.containsProperty(DashboardProperties.CONTROLLER_MOCK_DATA))
			mock = DashboardProperties.getBooleanProperty(DashboardProperties.CONTROLLER_MOCK_DATA);
		if (mock) {
			result = new ConsulMockClientImpl();
		} else {
			ControllerEndpointCredentials details = getOrSetControllerEndpointSelection();
			final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
			result = new ConsulRestClientImpl(details.getConsulUrl(), details.getUsername(), clearText);
		}
		return result;
	}
	
	/**
	 * Gets a REST client; either a mock client (returns canned data), or a real
	 * client with appropriate credentials from properties.
	 * 
	 * @return REST client.
	 */
	protected ConsulClient getConsulRestClient() throws Exception {
		ConsulClient result = null;
		// Be robust to missing development-only property
		boolean mock = false;
		if (DashboardProperties.containsProperty(DashboardProperties.CONTROLLER_MOCK_DATA))
			mock = DashboardProperties.getBooleanProperty(DashboardProperties.CONTROLLER_MOCK_DATA);
		if (mock) {
			result = new ConsulMockClientImpl();
		} else {
			ControllerEndpointCredentials details = getOrSetControllerEndpointSelection();
			final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
			result = new ConsulRestClientImpl(details.getConsulUrl(), details.getUsername(), clearText);
		}
		return result;
	}
	
	/**
	 * Convenience method that gets the user ID from the session and fetches the
	 * Inventory client. Factors code out of subclass methods.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Inventory client appropriate for the user
	 */
	protected InventoryClient getInventoryClient(HttpServletRequest request) throws Exception {
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getId() == null)
			throw new Exception("getControllerRestClient: Failed to get application user");
		return getInventoryClient(appUser.getId());
	}

	/**
	 * Gets an Inventory client with appropriate credentials from properties.
	 *
	 * @return Inventory Client.
	 */
	protected InventoryClient getInventoryClient(long userId) throws Exception {
		InventoryClient result = null;
		boolean mock = false;
		if (DashboardProperties.containsProperty(DashboardProperties.CONTROLLER_MOCK_DATA))
			mock = DashboardProperties.getBooleanProperty(DashboardProperties.CONTROLLER_MOCK_DATA);
		if (mock) {
			result = new RestInventoryClientMockImpl();
		} else {
		ControllerEndpointCredentials details = getOrSetControllerEndpointSelection(userId);
		final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
		result = new RestInventoryClientImpl(details.getInventoryUrl(), details.getUsername(), clearText);
		}
		return result;
	}

	protected InventoryClient getInventoryClient() throws Exception {
		InventoryClient result = null;
		ControllerEndpointCredentials details = getOrSetControllerEndpointSelection();
		final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
		result = new RestInventoryClientImpl(details.getInventoryUrl(), details.getUsername(), clearText);
		return result;
	}
	/**
	 * Convenience method that gets the user ID from the session and fetches the
	 * Deployment Handler client. Factors code out of subclass methods.
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return Deployment Handler client appropriate for the user
	 */
	protected DeploymentHandlerClient getDeploymentHandlerClient(HttpServletRequest request) throws Exception {
		User appUser = UserUtils.getUserSession(request);
		if (appUser == null || appUser.getId() == null)
			throw new Exception("getControllerRestClient: Failed to get application user");
		return getDeploymentHandlerClient(appUser.getId());
	}

	/**
	 * Gets a Deployment Handler client with appropriate credentials from properties.
	 *
	 * @return Deployment Handler Client.
	 */
	protected DeploymentHandlerClient getDeploymentHandlerClient(long userId) throws Exception {
		DeploymentHandlerClient result = null;
		ControllerEndpointCredentials details = getOrSetControllerEndpointSelection(userId);
		final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
		result = new DeploymentHandlerClientImpl(details.getDhandlerUrl(), details.getUsername(), clearText);
		return result;
	}
	
	protected DeploymentHandlerClient getDeploymentHandlerClient() throws Exception {
		DeploymentHandlerClient result = null;
		ControllerEndpointCredentials details = getOrSetControllerEndpointSelection();
		final String clearText = details.getEncryptedPassword() ? details.decryptPassword() : details.getPassword();
		result = new DeploymentHandlerClientImpl(details.getDhandlerUrl(), details.getUsername(), clearText);
		return result;
	}
}
