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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onap.oom.dashboard.model.CloudifyBlueprint;
import org.onap.oom.dashboard.model.CloudifyBlueprintUpload;
import org.onap.oom.dashboard.model.CloudifyDeployment;
import org.onap.oom.dashboard.model.CloudifyDeploymentList;
import org.onap.oom.dashboard.model.CloudifyDeploymentRequest;
import org.onap.oom.dashboard.model.CloudifyExecution;
import org.onap.oom.dashboard.model.CloudifyExecutionList;
import org.onap.oom.dashboard.model.CloudifyExecutionRequest;
import org.onap.oom.dashboard.model.ECTransportModel;
import org.onap.oom.dashboard.model.RestResponseError;
import org.onap.oom.dashboard.model.RestResponsePage;
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
 * Controller for Cloudify features: blueprints, deployments, executions.
 * Methods serve Ajax requests made by Angular scripts on pages that show
 * content.
 */
@Controller
@RequestMapping("/")
public class CloudifyController extends DashboardRestrictedBaseController {

	private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(CloudifyController.class);

	/**
	 * Enum for selecting an item type.
	 */
	public enum CloudifyDataItem {
		BLUEPRINT, DEPLOYMENT, EXECUTION;
	}

	private static final String BLUEPRINTS_PATH = "blueprints";
	private static final String VIEW_BLUEPRINTS_PATH = "viewblueprints";
	private static final String DEPLOYMENTS_PATH = "deployments";
	private static final String EXECUTIONS_PATH = "executions";

	/**
	 * Supports sorting blueprints by ID
	 */
	private static Comparator<CloudifyBlueprint> blueprintComparator = new Comparator<CloudifyBlueprint>() {
		@Override
		public int compare(CloudifyBlueprint o1, CloudifyBlueprint o2) {
			return o1.id.compareTo(o2.id);
		}
	};

	/**
	 * Supports sorting deployments by ID
	 */
	private static Comparator<CloudifyDeployment> deploymentComparator = new Comparator<CloudifyDeployment>() {
		@Override
		public int compare(CloudifyDeployment o1, CloudifyDeployment o2) {
			return o1.id.compareTo(o2.id);
		}
	};

	/**
	 * Supports sorting executions by ID
	 */
	private static Comparator<CloudifyExecution> executionComparator = new Comparator<CloudifyExecution>() {
		@Override
		public int compare(CloudifyExecution o1, CloudifyExecution o2) {
			return o1.id.compareTo(o2.id);
		}
	};

	/**
	 * Gets one page of objects and supporting information via the REST client.
	 * On success, returns a PaginatedRestResponse object as String.
	 * 
	 * @param option
	 *            Specifies which item list type to get
	 * @param pageNum
	 *            Page number of results
	 * @param pageSize
	 *            Number of items per browser page
	 * @return JSON block as String, see above.
	 * @throws Exception
	 *             On any error; e.g., Network failure.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getItemListForPage(long userId, CloudifyDataItem option, int pageNum, int pageSize)
			throws Exception {
		IControllerRestClient restClient = getControllerRestClient(userId);
		List itemList = null;
		switch (option) {
		case BLUEPRINT:
			itemList = restClient.getBlueprints().items;
			Collections.sort(itemList, blueprintComparator);
			break;
		case DEPLOYMENT:
			itemList = restClient.getDeployments().items;
			Collections.sort(itemList, deploymentComparator);
			break;
		default:
			throw new Exception("getItemListForPage failed: unimplemented case: " + option.name());
		}

		// Shrink if needed
		final int totalItems = itemList.size();
		final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
		if (totalItems > pageSize)
			itemList = getPageOfList(pageNum, pageSize, itemList);
		RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
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
	protected String getItemListForPageWrapper(HttpServletRequest request, CloudifyDataItem option) {
		String outboundJson = null;
		try {
			User appUser = UserUtils.getUserSession(request);
			if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
				throw new Exception("getItemListForPageWrapper: Failed to get application user");
			int pageNum = getRequestPageNumber(request);
			int pageSize = getRequestPageSize(request);
			outboundJson = getItemListForPage(appUser.getId(), option, pageNum, pageSize);
		} catch (Exception ex) {
			logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception", ex);
			RestResponseError result = null;
			if (ex instanceof HttpStatusCodeException)
				result = new RestResponseError(((HttpStatusCodeException) ex).getResponseBodyAsString());
			else
				result = new RestResponseError("Failed to get " + option.name(), ex);
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
	 * Serves one page of blueprints
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of CloudifyBlueprint objects
	 */
	@RequestMapping(value = { BLUEPRINTS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getBlueprintsByPage(HttpServletRequest request) {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		String json = getItemListForPageWrapper(request, CloudifyDataItem.BLUEPRINT);
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return json;
	}

	/**
	 * Serves one page of deployments
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return List of CloudifyDeployment objects
	 */
	@RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getDeploymentsByPage(HttpServletRequest request) {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		String json = getItemListForPageWrapper(request, CloudifyDataItem.DEPLOYMENT);
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return json;
	}

	/**
	 * Gets the specified blueprint metadata.
	 * 
	 * @param id
	 *            Blueprint ID
	 * @param request
	 *            HttpServletRequest
	 * @return Blueprint as JSON; or error.
	 * @throws Exception
	 *             on serialization error
	 * 
	 */
	@RequestMapping(value = { BLUEPRINTS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getBlueprintById(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.getBlueprint(id);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("getBlueprintById failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Gets the specified blueprint content for viewing.
	 * 
	 * @param id
	 *            Blueprint ID
	 * @param request
	 *            HttpServletRequest
	 * @return Blueprint as YAML; or error.
	 * @throws Exception
	 *             on serialization error
	 * 
	 */
	@RequestMapping(value = {
			VIEW_BLUEPRINTS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/yaml")
	@ResponseBody
	public String viewBlueprintContentById(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.viewBlueprint(id);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("getBlueprintContentById failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Processes request to upload a blueprint from a remote server.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param blueprint
	 *            Cloudify blueprint
	 * @return Blueprint as uploaded; or error.
	 * @throws Exception
	 *             on serialization error
	 */
	@RequestMapping(value = { BLUEPRINTS_PATH }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String uploadBlueprint(HttpServletRequest request, @RequestBody CloudifyBlueprintUpload blueprint)
			throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.uploadBlueprint(blueprint);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("uploadBlueprint failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Deletes the specified blueprint.
	 * 
	 * @param id
	 *            Blueprint ID
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return No content on success; error on failure.
	 * @throws Exception
	 *             On serialization failure
	 */
	@RequestMapping(value = { BLUEPRINTS_PATH + "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public String deleteBlueprint(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			int code = restClient.deleteBlueprint(id);
			response.setStatus(code);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("deleteBlueprint failed on ID " + id, t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		if (result == null)
			return null;
		else
			return objectMapper.writeValueAsString(result);
	}

	/**
	 * Gets the specified deployment.
	 * 
	 * @param id
	 *            Deployment ID
	 * @param request
	 *            HttpServletRequest
	 * @return Deployment for the specified ID; error on failure.
	 * @throws Exception
	 *             On serialization failure
	 * 
	 */
	@RequestMapping(value = { DEPLOYMENTS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getDeploymentById(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.getDeployment(id);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("getDeploymentById failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Processes request to create a deployment based on a blueprint.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param deployment
	 *            Deployment to upload
	 * @return Body of deployment; error on failure
	 * @throws Exception
	 *             On serialization failure
	 */
	@RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String createDeployment(HttpServletRequest request, @RequestBody CloudifyDeploymentRequest deployment)
			throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.createDeployment(deployment);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("createDeployment failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Deletes the specified deployment.
	 * 
	 * @param id
	 *            Deployment ID
	 * @param ignoreLiveNodes
	 *            Boolean indicator whether to force a delete in case of live
	 *            nodes
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return Passes thru HTTP status code from remote endpoint; no body on
	 *         success
	 * @throws Exception
	 *             on serialization failure
	 */
	@RequestMapping(value = {
			DEPLOYMENTS_PATH + "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public String deleteDeployment(@PathVariable("id") String id,
			@RequestParam(value = "ignore_live_nodes", required = false) Boolean ignoreLiveNodes,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			int code = restClient.deleteDeployment(id, ignoreLiveNodes == null ? false : ignoreLiveNodes);
			response.setStatus(code);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("deleteDeployment failed on ID " + id, t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		if (result == null)
			return null;
		else
			return objectMapper.writeValueAsString(result);
	}

	/**
	 * Gets and serves one page of executions:
	 * <OL>
	 * <LI>Gets all deployments; OR uses the specified deployment ID if the
	 * query parameter is present
	 * <LI>Gets executions for each deployment ID
	 * <LI>Sorts by execution ID
	 * <LI>Reduces the list to the page size (if needed)
	 * <LI>If the optional request parameter "status" is present, reduces the
	 * list to the executions with that status.
	 * </OL>
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param deployment_id
	 *            Optional request parameter; if found, only executions for that
	 *            deployment ID are returned.
	 * @param status
	 *            Optional request parameter; if found, only executions with
	 *            that status are returned.
	 * @return List of CloudifyExecution objects
	 * @throws Exception
	 *             on serialization failure
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { EXECUTIONS_PATH }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getExecutionsByPage(HttpServletRequest request,
			@RequestParam(value = "deployment_id", required = false) String deployment_id,
			@RequestParam(value = "status", required = false) String status) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			List<CloudifyExecution> itemList = new ArrayList<CloudifyExecution>();
			IControllerRestClient restClient = getControllerRestClient(request);
			List<String> depIds = new ArrayList<>();
			if (deployment_id == null) {
				CloudifyDeploymentList depList = restClient.getDeployments();
				for (CloudifyDeployment cd : depList.items)
					depIds.add(cd.id);
			} else {
				depIds.add(deployment_id);
			}
			for (String depId : depIds) {
				CloudifyExecutionList exeList = restClient.getExecutions(depId);
				itemList.addAll(exeList.items);
			}
			// Filter down to specified status as needed
			if (status != null) {
				Iterator<CloudifyExecution> exeIter = itemList.iterator();
				while (exeIter.hasNext()) {
					CloudifyExecution ce = exeIter.next();
					if (!status.equals(ce.status))
						exeIter.remove();
				}
			}
			Collections.sort(itemList, executionComparator);

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
			result = new RestResponseError("getExecutionsByPage failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Gets the specified execution for one deployment.
	 * 
	 * It's not clear why the deployment ID is needed.
	 * 
	 * @param execution_id
	 *            Execution ID (path variable)
	 * @param deployment_id
	 *            Deployment ID (query parameter)
	 * @param request
	 *            HttpServletRequest
	 * @return CloudifyExecutionList
	 * @throws Exception
	 *             on serialization failure
	 */
	@RequestMapping(value = { EXECUTIONS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getExecutionByIdAndDeploymentId(@PathVariable("id") String execution_id,
			@RequestParam("deployment_id") String deployment_id, HttpServletRequest request) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.getExecutions(deployment_id);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("getExecutionByIdAndDeploymentId failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Processes request to create an execution based on a deployment.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param execution
	 *            Execution model
	 * @return Information about the execution
	 * @throws Exception
	 *             on serialization failure
	 */
	@RequestMapping(value = { EXECUTIONS_PATH }, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String startExecution(HttpServletRequest request, @RequestBody CloudifyExecutionRequest execution)
			throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			result = restClient.startExecution(execution);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("startExecution failed", t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		return objectMapper.writeValueAsString(result);
	}

	/**
	 * Cancels an execution.
	 * 
	 * @param id
	 *            Execution ID
	 * @param deploymentId
	 *            Deployment ID (not clear why this is needed)
	 * @param action
	 *            Action to perform (not clear why this is needed)
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletRequest
	 * @return Passes thru HTTP status code from remote endpoint; no body on
	 *         success
	 * @throws Exception
	 *             on serialization failure
	 */
	@RequestMapping(value = { EXECUTIONS_PATH + "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public String cancelExecution(@PathVariable("id") String id,
			@RequestParam(value = "deployment_id") String deploymentId, @RequestParam(value = "action") String action,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(new Date()));
		logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME);
		ECTransportModel result = null;
		try {
			IControllerRestClient restClient = getControllerRestClient(request);
			int code = restClient.cancelExecution(id, deploymentId, action);
			response.setStatus(code);
		} catch (HttpStatusCodeException e) {
			result = new RestResponseError(e.getResponseBodyAsString());
		} catch (Throwable t) {
			result = new RestResponseError("cancelExecution failed on ID " + id, t);
		}
		MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(new Date()));
		logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
		if (result == null)
			return null;
		else
			return objectMapper.writeValueAsString(result);
	}

}
