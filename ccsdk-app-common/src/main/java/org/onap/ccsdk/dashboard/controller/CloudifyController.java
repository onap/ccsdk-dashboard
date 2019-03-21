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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateRequest;
import org.onap.ccsdk.dashboard.model.CloudifyEvent;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyTenant;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(CloudifyController.class);

    @Autowired
    CloudifyClient cloudifyClient;

    /**
     * Enum for selecting an item type.
     */
    public enum CloudifyDataItem {
        BLUEPRINT, DEPLOYMENT, EXECUTION, TENANT;
    }

    private static Date begin;
    private static Date end;
    private static final String BLUEPRINTS_PATH = "blueprints";
    private static final String DEPLOYMENTS_PATH = "deployments";
    private static final String EXECUTIONS_PATH = "executions";
    private static final String TENANTS_PATH = "tenants";
    private static final String NODE_INSTANCES_PATH = "node-instances";
    private static final String UPDATE_DEPLOYMENT_PATH = "update-deployment";
    private static final String EVENTS_PATH = "events";
    private static final String DEP_TENANT_STATUS = "deployment-status";

    /**
     * Supports sorting events by timestamp
     */
    private static Comparator<CloudifyEvent> eventComparator = new Comparator<CloudifyEvent>() {
        @Override
        public int compare(CloudifyEvent o1, CloudifyEvent o2) {
            return o1.reported_timestamp.compareTo(o2.reported_timestamp);
        }
    };

    /**
     * Supports sorting executions by timestamp
     */
    private static Comparator<CloudifyExecution> executionComparator = new Comparator<CloudifyExecution>() {
        @Override
        public int compare(CloudifyExecution o1, CloudifyExecution o2) {
            return o1.created_at.compareTo(o2.created_at);
        }
    };

    /**
     * Gets one page of objects and supporting information via the REST client. On
     * success, returns a PaginatedRestResponse object as String.
     * 
     * @param option Specifies which item list type to get
     * @param pageNum Page number of results
     * @param pageSize Number of items per browser page
     * @return JSON block as String, see above.
     * @throws Exception On any error; e.g., Network failure.
     */
    @SuppressWarnings({"rawtypes"})
    private String getItemListForPage(long userId, CloudifyDataItem option, int pageNum,
        int pageSize) throws Exception {
        /*
         * if (this.restClient == null) { this.restClient =
         * getCloudifyRestClient(userId); }
         */
        List itemList = null;
        switch (option) {
            /*
             * case BLUEPRINT: itemList = restClient.getBlueprints().items;
             * Collections.sort(itemList, blueprintComparator); break; case DEPLOYMENT:
             * itemList = restClient.getDeployments().items; Collections.sort(itemList,
             * deploymentComparator); break;
             */
            case TENANT:
                itemList = cloudifyClient.getTenants().items;
                break;
            default:
                MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                MDC.put("TargetEntity", "Cloudify Manager");
                MDC.put("TargetServiceName", "Cloudify Manager");
                MDC.put("ErrorCode", "300");
                MDC.put("ErrorCategory", "ERROR");
                MDC.put("ErrorDescription", "Getting page of items failed!");
                logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPage caught exception");
                throw new Exception(
                    "getItemListForPage failed: unimplemented case: " + option.name());
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
     * @param request Inbound request
     * @param option  Item type to get
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
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of items failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception");
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
     * @param request HttpServletRequest
     * @return List of CloudifyBlueprint objects
     */
    /*
     * @RequestMapping(value = { BLUEPRINTS_PATH }, method = RequestMethod.GET,
     * produces = "application/json")
     * 
     * @ResponseBody public String getBlueprintsByPage(HttpServletRequest request) {
     * preLogAudit(request); String json = getItemListForPageWrapper(request,
     * CloudifyDataItem.BLUEPRINT); postLogAudit(request); return json; }
     */
    /**
     * Serves one page of deployments
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */

    /*
     * @RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.GET,
     * produces = "application/json")
     * 
     * @ResponseBody public String getDeploymentsByPage(HttpServletRequest request)
     * { preLogAudit(request); String json = getItemListForPageWrapper(request,
     * CloudifyDataItem.DEPLOYMENT); postLogAudit(request); return json; }
     */
    /**
     * gets the tenants list
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
    @RequestMapping(value = { TENANTS_PATH }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getTenants(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, CloudifyDataItem.TENANT);
        postLogAudit(request);
        return json;
    }

    /**
     * Gets the specified blueprint metadata.
     * 
     * @param id      Blueprint ID
     * @param request HttpServletRequest
     * @return Blueprint as JSON; or error.
     * @throws Exception on serialization error
     * 
     */
    @RequestMapping(value = { BLUEPRINTS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getBlueprintById(@PathVariable("id") String id,
            @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            result = cloudifyClient.getBlueprint(id, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting blueprint " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getBlueprintById caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting blueprint " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getBlueprintById caught exception");
            result = new RestResponseError("getBlueprintById failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the specified blueprint content for viewing.
     * 
     * @param id      Blueprint ID
     * @param request HttpServletRequest
     * @return Blueprint as YAML; or error.
     * @throws Exception on serialization error
     * 
     */
    /*
     * @RequestMapping(value = { VIEW_BLUEPRINTS_PATH + "/{id}" }, method =
     * RequestMethod.GET, produces = "application/yaml")
     * 
     * @ResponseBody public String viewBlueprintContentById(@PathVariable("id")
     * String id, HttpServletRequest request) throws Exception {
     * preLogAudit(request); ECTransportModel result = null; try { result =
     * cloudifyClient.viewBlueprint(id); } catch (HttpStatusCodeException e) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Viewing blueprint " + id + " failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "viewBlueprintContentById caught exception"); result = new
     * RestResponseError(e.getResponseBodyAsString()); } catch (Throwable t) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Viewing blueprint " + id + " failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "viewBlueprintContentById caught exception"); result = new
     * RestResponseError("getBlueprintContentById failed", t); } finally {
     * postLogAudit(request); } return objectMapper.writeValueAsString(result); }
     */
    /**
     * Processes request to upload a blueprint from a remote server.
     * 
     * @param request   HttpServletRequest
     * @param blueprint Cloudify blueprint
     * @return Blueprint as uploaded; or error.
     * @throws Exception on serialization error
     */
    /*
     * @RequestMapping(value = { BLUEPRINTS_PATH }, method = RequestMethod.POST,
     * produces = "application/json")
     * 
     * @ResponseBody public String uploadBlueprint(HttpServletRequest
     * request, @RequestBody CloudifyBlueprintUpload blueprint) throws Exception {
     * preLogAudit(request); ECTransportModel result = null; try { result =
     * cloudifyClient.uploadBlueprint(blueprint); } catch (HttpStatusCodeException
     * e) { MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Uploading blueprint failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "uploadBlueprint caught exception"); result = new
     * RestResponseError(e.getResponseBodyAsString()); } catch (Throwable t) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Uploading blueprint failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "uploadBlueprint caught exception"); result = new
     * RestResponseError("uploadBlueprint failed", t); } finally {
     * postLogAudit(request); } return objectMapper.writeValueAsString(result); }
     */
    /**
     * Deletes the specified blueprint.
     * 
     * @param id       Blueprint ID
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return No content on success; error on failure.
     * @throws Exception On serialization failure
     */

    /*
     * @RequestMapping(value = { BLUEPRINTS_PATH + "/{id}" }, method =
     * RequestMethod.DELETE, produces = "application/json")
     * 
     * @ResponseBody public String deleteBlueprint(@PathVariable("id") String id,
     * HttpServletRequest request, HttpServletResponse response) throws Exception {
     * preLogAudit(request); ECTransportModel result = null; try { int code =
     * cloudifyClient.deleteBlueprint(id); response.setStatus(code); } catch
     * (HttpStatusCodeException e) { MDC.put(SystemProperties.STATUS_CODE, "ERROR");
     * MDC.put("TargetEntity", "Cloudify Manager"); MDC.put("TargetServiceName",
     * "Cloudify Manager"); MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory",
     * "ERROR"); MDC.put("ErrorDescription", "Deleting blueprint " + id +
     * " failed!"); logger.error(EELFLoggerDelegate.errorLogger,
     * "deleteBlueprint caught exception"); result = new
     * RestResponseError(e.getResponseBodyAsString()); } catch (Throwable t) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Deleting blueprint " + id + " failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "deleteBlueprint caught exception"); result = new
     * RestResponseError("deleteBlueprint failed on ID " + id, t); } finally {
     * postLogAudit(request); } if (result == null) return null; else return
     * objectMapper.writeValueAsString(result); }
     */
    /**
     * Gets the specified deployment.
     * 
     * @param id      Deployment ID
     * @param request HttpServletRequest
     * @return Deployment for the specified ID; error on failure.
     * @throws Exception On serialization failure
     * 
     */
    @RequestMapping(value = { DEPLOYMENTS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDeploymentById(@PathVariable("id") String id,
            @RequestParam(value = "tenant", required = false) String tenant, HttpServletRequest request)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant != null && tenant.length() > 0) {
                result = cloudifyClient.getDeployment(id, tenant);
            } else {
                result = cloudifyClient.getDeployment(id);
            }
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting deployment " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getDeploymentById caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting deployment " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getDeploymentById caught exception");
            result = new RestResponseError("getDeploymentById failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Query status and tenant info for deployments
     * 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { DEP_TENANT_STATUS }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String getTenantStatusForService(HttpServletRequest request, @RequestBody String[] serviceList)
            throws Exception {
        preLogAudit(request);
        /*
         * 1) Get all the tenant names 2) Get the deployment IDs per tenant for all the
         * tenants, aggregate the deployments list 3) Get the input deployments list
         * (screen input), filter the deployments list from step#2 4) For each item in
         * the list from step#3, get the execution status info and generate the final
         * response
         */
        String outboundJson = "";
        CloudifyDeployedTenantList cfyTenantDeployMapList = null;
        new HashMap<String, Object>();
        List<CloudifyDeployedTenant> tenantList = new ArrayList<CloudifyDeployedTenant>();
        List<CloudifyExecution> cfyExecList = new ArrayList<CloudifyExecution>();
        try {
            List<CloudifyTenant> cldfyTen = cloudifyClient.getTenants().items;
            for (CloudifyTenant ct : (List<CloudifyTenant>) cldfyTen) {
                cfyTenantDeployMapList = cloudifyClient.getTenantInfoFromDeploy(ct.name);
                tenantList.addAll(((CloudifyDeployedTenantList) cfyTenantDeployMapList).items);
            }
            List<CloudifyDeployedTenant> currSrvcTenants = new ArrayList<CloudifyDeployedTenant>();

            for (String serviceId : serviceList) {
                for (CloudifyDeployedTenant deplTen : tenantList) {
                    if (serviceId.equals(deplTen.id)) {
                        currSrvcTenants.add(deplTen);
                        break;
                    }
                }
            }
            // Get concise execution status for each of the tenant deployment items
            boolean isHelmType = false;
            boolean helmStatus = false;
            for (CloudifyDeployedTenant deplItem : currSrvcTenants) {
                CloudifyExecutionList execResults =
                    cloudifyClient.getExecutionsSummary(deplItem.id, deplItem.tenant_name);
                isHelmType = false;
                helmStatus = false;
                CloudifyBlueprintList bpList =
                    cloudifyClient.getBlueprint(deplItem.id, deplItem.tenant_name);
                Map<String, Object> bpPlan = bpList.items.get(0).plan;
                Map<String, String> workflows = (Map<String, String>) bpPlan.get("workflows");
                Map<String, String> pluginInfo =
                    ((List<Map<String, String>>) bpPlan.get("deployment_plugins_to_install"))
                        .get(0);
                if (pluginInfo.get("name").equals("helm-plugin")) {
                    isHelmType = true;
                }
                if (workflows.containsKey("status")) {
                    helmStatus = true;
                }
                for (CloudifyExecution cfyExec : execResults.items) {
                    if (cfyExec.workflow_id.equalsIgnoreCase("install")) {
                        cfyExec.is_helm = isHelmType;
                        cfyExec.helm_status = helmStatus;
                        cfyExecList.add(cfyExec);
                    }
                }
            }
            outboundJson = objectMapper.writeValueAsString(cfyExecList);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting deployments failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getTenantStatusForService caught exception");
            RestResponseError result = null;
            result = new RestResponseError(
                "getTenantStatusForService failed" + e.getResponseBodyAsString());
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting deployments failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getTenantStatusForService caught exception");
            RestResponseError result = null;
            result = new RestResponseError("getTenantStatusForService failed");
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        } finally {
            postLogAudit(request);
        }

        return outboundJson;
    }

    /**
     * Processes request to create a deployment based on a blueprint.
     * 
     * @param request    HttpServletRequest
     * @param deployment Deployment to upload
     * @return Body of deployment; error on failure
     * @throws Exception On serialization failure
     */
    /*
     * @RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.POST,
     * produces = "application/json")
     * 
     * @ResponseBody public String createDeployment(HttpServletRequest
     * request, @RequestBody CloudifyDeploymentRequest deployment) throws Exception
     * { preLogAudit(request); ECTransportModel result = null; try { result =
     * cloudifyClient.createDeployment(deployment); } catch (HttpStatusCodeException
     * e) { MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Creating deployment failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "createDeployment caught exception"); result = new
     * RestResponseError(e.getResponseBodyAsString()); } catch (Throwable t) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory", "ERROR");
     * MDC.put("ErrorDescription", "Creating deployment failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "createDeployment caught exception"); result = new
     * RestResponseError("createDeployment failed", t); } finally {
     * postLogAudit(request); } return objectMapper.writeValueAsString(result); }
     */
    /**
     * Deletes the specified deployment.
     * 
     * @param id              Deployment ID
     * @param ignoreLiveNodes Boolean indicator whether to force a delete in case of
     *                        live nodes
     * @param request         HttpServletRequest
     * @param response        HttpServletResponse
     * @return Passes thru HTTP status code from remote endpoint; no body on success
     * @throws Exception on serialization failure
     */

    /*
     * @RequestMapping(value = { DEPLOYMENTS_PATH + "/{id}" }, method =
     * RequestMethod.DELETE, produces = "application/json")
     * 
     * @ResponseBody public String deleteDeployment(@PathVariable("id") String id,
     * 
     * @RequestParam(value = "ignore_live_nodes", required = false) Boolean
     * ignoreLiveNodes, HttpServletRequest request, HttpServletResponse response)
     * throws Exception { preLogAudit(request); ECTransportModel result = null; try
     * { int code = cloudifyClient.deleteDeployment(id, ignoreLiveNodes == null ?
     * false : ignoreLiveNodes); response.setStatus(code); } catch
     * (HttpStatusCodeException e) { MDC.put(SystemProperties.STATUS_CODE, "ERROR");
     * MDC.put("TargetEntity", "Cloudify Manager"); MDC.put("TargetServiceName",
     * "Cloudify Manager"); MDC.put("ErrorCode", "300"); MDC.put("ErrorCategory",
     * "ERROR"); MDC.put("ErrorDescription", "Deleting deployment " + id +
     * " failed!"); logger.error(EELFLoggerDelegate.errorLogger,
     * "deleteDeployment caught exception"); result = new
     * RestResponseError(e.getResponseBodyAsString()); } catch (Throwable t) {
     * MDC.put(SystemProperties.STATUS_CODE, "ERROR"); MDC.put("TargetEntity",
     * "Cloudify Manager"); MDC.put("TargetServiceName", "Cloudify Manager");
     * MDC.put("ErrorCategory", "ERROR"); MDC.put("ErrorDescription",
     * "Deleting deployment " + id + " failed!");
     * logger.error(EELFLoggerDelegate.errorLogger,
     * "deleteDeployment caught exception"); result = new
     * RestResponseError("deleteDeployment failed on ID " + id, t); } finally {
     * postLogAudit(request); } if (result == null) return null; else return
     * objectMapper.writeValueAsString(result); }
     */
    /**
     * Gets and serves one page of executions:
     * <OL>
     * <LI>Gets all deployments; OR uses the specified deployment ID if the query
     * parameter is present
     * <LI>Gets executions for each deployment ID
     * <LI>Sorts by execution ID
     * <LI>Reduces the list to the page size (if needed)
     * <LI>If the optional request parameter "status" is present, reduces the list
     * to the executions with that status.
     * </OL>
     * 
     * @param request       HttpServletRequest
     * @param deployment_id Optional request parameter; if found, only executions
     *                      for that deployment ID are returned.
     * @param status        Optional request parameter; if found, only executions
     *                      with that status are returned.
     * @return List of CloudifyExecution objects
     * @throws Exception on serialization failure
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { EXECUTIONS_PATH }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getExecutionsByPage(HttpServletRequest request,
        @RequestParam(value = "deployment_id", required = false) String deployment_id,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "tenant", required = true) String tenant) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            List<CloudifyExecution> itemList = new ArrayList<CloudifyExecution>();
            List<String> depIds = new ArrayList<>();
            if (deployment_id == null) {
                CloudifyDeploymentList depList = cloudifyClient.getDeployments();
                for (CloudifyDeployment cd : depList.items)
                    depIds.add(cd.id);
            } else {
                depIds.add(deployment_id);
            }
            for (String depId : depIds) {
                CloudifyExecutionList exeList = cloudifyClient.getExecutions(depId, tenant);
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
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionsByPage caught exception");
            result = new RestResponseError("getExecutionsByPage failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the specified execution for one deployment.
     * 
     * It's not clear why the deployment ID is needed.
     * 
     * @param execution_id  Execution ID (path variable)
     * @param deployment_id Deployment ID (query parameter)
     * @param request       HttpServletRequest
     * @return CloudifyExecutionList
     * @throws Exception on serialization failure
     */
    @RequestMapping(value = { EXECUTIONS_PATH + "/{id}" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getExecutionByIdAndDeploymentId(@PathVariable("id") String execution_id,
            @RequestParam("deployment_id") String deployment_id,
            @RequestParam(value = "tenant", required = false) String tenant, HttpServletRequest request)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            result = cloudifyClient.getExecutions(deployment_id, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                    "Getting executions " + execution_id + " for deployment " + deployment_id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                    "Getting executions " + execution_id + " for deployment " + deployment_id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError("getExecutionByIdAndDeploymentId failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the execution events for specified execution ID.
     * 
     * 
     * @param execution_id Execution ID (request parameter)
     * @param tenant       tenant name (query parameter)
     * @param request      HttpServletRequest
     * @return CloudifyExecutionList
     * @throws Exception on serialization failure
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { EVENTS_PATH }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getExecutionEventsById(
        @RequestParam(value = "execution_id", required = false) String execution_id,
        @RequestParam(value = "logType", required = false) String isLogEvent,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        CloudifyEventList eventsList = null;
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            eventsList = cloudifyClient.getEventlogs(execution_id, tenant);
            // Filter down to specified event type as needed
            List<CloudifyEvent> itemList = eventsList.items;
            if (!isLogEvent.isEmpty() && isLogEvent.equals("false")) {
                Iterator<CloudifyEvent> exeIter = itemList.iterator();
                while (exeIter.hasNext()) {
                    CloudifyEvent ce = exeIter.next();
                    if (ce.type.equals("cloudify_log")) {
                        exeIter.remove();
                    }
                }
            }
            Collections.sort(itemList, eventComparator);
            Collections.reverse(itemList);
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize)
                itemList = getPageOfList(pageNum, pageSize, itemList);
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions " + execution_id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionEventsById caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions " + execution_id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError("getExecutionEventsById failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Processes request to create an execution based on a deployment.
     * 
     * @param request   HttpServletRequest
     * @param execution Execution model
     * @return Information about the execution
     * @throws Exception on serialization failure
     */
    @RequestMapping(value = { EXECUTIONS_PATH }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String startExecution(HttpServletRequest request, @RequestBody CloudifyExecutionRequest execution)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (!execution.workflow_id.equals("status")
                && !execution.getParameters().containsKey("node_instance_id")) {
                // get the node instance ID for the deployment
                String nodeInstId = "";
                CloudifyNodeInstanceIdList nodeInstList = cloudifyClient
                    .getNodeInstanceId(execution.getDeployment_id(), execution.getTenant());
                if (nodeInstList != null) {
                    nodeInstId = nodeInstList.items.get(0).id;
                }
                Map<String, Object> inParms = execution.getParameters();
                inParms.put("node_instance_id", nodeInstId);
                execution.setParameters(inParms);
            }
            result = cloudifyClient.startExecution(execution);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Starting execution failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "startExecution caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Starting execution failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "startExecution caught exception");
            result = new RestResponseError("startExecution failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Processes request to create an execution based on a deployment.
     * 
     * @param request   HttpServletRequest
     * @param execution Execution model
     * @return Information about the execution
     * @throws Exception on serialization failure
     */
    @RequestMapping(value = { UPDATE_DEPLOYMENT_PATH }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String updateDeployment(HttpServletRequest request, @RequestBody CloudifyDeploymentUpdateRequest execution)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            result = cloudifyClient.updateDeployment(execution);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateDeployment caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateDeployment caught exception");
            result = new RestResponseError("updateDeployment failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Cancels an execution.
     * 
     * @param id           Execution ID
     * @param deploymentId Deployment ID (not clear why this is needed)
     * @param action       Action to perform (not clear why this is needed)
     * @param request      HttpServletRequest
     * @param response     HttpServletRequest
     * @return Passes thru HTTP status code from remote endpoint; no body on success
     * @throws Exception on serialization failure
     */
    @RequestMapping(value = { EXECUTIONS_PATH + "/{id}" }, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String cancelExecution(@RequestHeader HttpHeaders headers, @PathVariable("id") String id,
            @RequestBody Map<String, String> parameters, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        List<String> tenant = null;
        try {
            tenant = headers.get("tenant");
            result = cloudifyClient.cancelExecution(id, parameters, tenant.get(0));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Cancelling execution " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "cancelExecution caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Cancelling execution " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "cancelExecution caught exception");
            result = new RestResponseError("cancelExecution failed on ID " + id, t);
        } finally {
            postLogAudit(request);
        }
        if (result == null)
            return null;
        else
            return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the specified node-instance-id content for viewing.
     * 
     * @param id      deployment ID
     * @param id      node ID
     * @param request HttpServletRequest
     * @return Blueprint as YAML; or error.
     * @throws Exception on serialization error
     * 
     */
    @RequestMapping(value = { NODE_INSTANCES_PATH
            + "/{deploymentId}/{nodeId}" }, method = RequestMethod.GET, produces = "application/yaml")
    @ResponseBody
    public String getNodeInstanceId(@PathVariable("deploymentId") String deploymentId,
            @RequestParam(value = "tenant", required = true) String tenant, @PathVariable("nodeId") String nodeId,
            HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            result = cloudifyClient.getNodeInstanceId(deploymentId, nodeId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance-id with deploymentId " + deploymentId + " and nodeId "
                    + nodeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstanceId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance-id with deploymentId " + deploymentId + " and nodeId "
                    + nodeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstanceId caught exception");
            result = new RestResponseError("getNodeInstanceId failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId}/revisions" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDeploymentRevisions(@PathVariable("deploymentId") String deploymentId,
            @RequestParam(value = "tenant") String tenant, HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            result = cloudifyClient.getNodeInstanceVersion(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError("getExecutionByIdAndDeploymentId failed", t);
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
        MDC.put("TargetEntity", "Cloudify Manager");
        MDC.put("TargetServiceName", "Cloudify Manager");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger, request.getMethod() + request.getRequestURI());
    }
}
