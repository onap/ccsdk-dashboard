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

package org.onap.ccsdk.api.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.onap.aaf.cadi.CadiWrap;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentInput;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResource;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResourceLinks;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponseLinks;
import org.onap.ccsdk.dashboard.model.inventory.Blueprint;
import org.onap.ccsdk.dashboard.model.inventory.BlueprintInput;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeUploadRequest;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.onap.portalsdk.core.util.SystemProperties;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/nb-api/v2")
public class NbApiController extends ApiBaseController {
    private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(NbApiController.class);

    private static final String DEPLOYMENTS_PATH = "deployments";
    private static final String SERVICE_TYPES_PATH = "blueprints";
    private static final String EXECUTIONS_PATH = "executions";
    private static final String TENANTS_PATH = "tenants";
    private static final String SERVICE_HEALTH_PATH = "health";

    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    DeploymentHandlerClient deploymentHandlerClient;

    @Autowired
    CloudifyClient cloudifyClient;

    @Autowired
    ConsulClient consulClient;

    @Autowired
    ApplicationEventPublisher eventPub;

    private AbstractCacheManager cacheManager;

    @Autowired
    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public AbstractCacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * Enum for selecting an item type.
     */
    public enum InventoryDataItem {
        DEPLOYMENTS, BLUEPRINTS, SERVICES_GROUPBY;
    }

    private static Date begin, end;

    /**
     * get the tenants list
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(
        value = {TENANTS_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    @ResponseBody
    public String getTenants(HttpServletRequest request) throws Exception {
        preLogAudit(request);
        List itemList = cloudifyClient.getTenants().items;
        final int totalItems = itemList.size();
        final int pageSize = 20;
        final int pageNum = 1;
        final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        if (totalItems > pageSize)
            itemList = getPageOfList(pageNum, pageSize, itemList);
        RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
        String outboundJson = objectMapper.writeValueAsString(model);
        return outboundJson;
    }

    private boolean isAuthorized(HttpServletRequest request, String component) {
        boolean auth = true;
        return auth;
    }

    @RequestMapping(
        value = {SERVICE_TYPES_PATH},
        method = RequestMethod.POST,
        produces = "application/json")
    public String createBlueprint(HttpServletRequest request, HttpServletResponse response,
        @RequestBody ServiceTypeUploadRequest serviceTypeUplReq,
        ServletUriComponentsBuilder uriBuilder) throws Exception {
        preLogAudit(request);
        String json = null;
        try {
            Blueprint.parse(serviceTypeUplReq.getBlueprintTemplate());

            if (serviceTypeUplReq.component == null || serviceTypeUplReq.component.isEmpty()) {
                json = objectMapper.writeValueAsString(
                    new RestResponseError("Component name missing in blueprint request body"));
                logger.error(EELFLoggerDelegate.errorLogger,
                    "Component name missing in blueprint request body");
                return json;
            }

            if (!isAuthorized(request, serviceTypeUplReq.component)) {
                response.setStatus(HttpStatus.SC_FORBIDDEN);
                json = objectMapper.writeValueAsString(
                    new RestResponseError("Un-authorized to perform this operation"));
                return json;
            }

            Collection<String> serviceIds = new ArrayList<String>();
            Collection<String> vnfTypes = new ArrayList<String>();
            Collection<String> serviceLocations = new ArrayList<String>();
            Optional<String> asdcServiceId = null;
            Optional<String> asdcResourceId = null;
            Optional<String> asdcServiceURL = null;

            ServiceTypeRequest invSrvcTypeReq =
                new ServiceTypeRequest(serviceTypeUplReq.owner, serviceTypeUplReq.typeName,
                    serviceTypeUplReq.typeVersion, serviceTypeUplReq.blueprintTemplate,
                    serviceTypeUplReq.application, serviceTypeUplReq.component, serviceIds,
                    vnfTypes, serviceLocations, asdcServiceId, asdcResourceId, asdcServiceURL);
            ServiceType apiResponse = inventoryClient.addServiceType(invSrvcTypeReq);
            json = objectMapper.writeValueAsString(apiResponse);
            String uri = request.getRequestURI();
            if (uri != null) {
            String uri_all = uriBuilder.replacePath(uri).build().toUriString();
            String uri_self =
                uriBuilder.path("/" + apiResponse.getTypeId().get()).build().toUriString();
            StringBuffer linkHeader = new StringBuffer();
            String linkStr_all = "<" + uri_all + ">; rel=\"" + "current" + "\"";
            String linkStr_self = "<" + uri_self + ">; rel=\"" + "self" + "\"";
            linkHeader.append(linkStr_self);
            if (linkHeader.length() > 0) {
                linkHeader.append(", ");
            }
            linkHeader.append(linkStr_all);
            response.addHeader("Link", linkHeader.toString());
            }
        } catch (BlueprintParseException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating service type failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "updateServiceTypeBlueprint caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("Invalid blueprint format.", e));
        } catch (HttpStatusCodeException e) {
            response.setStatus(HttpStatus.SC_BAD_GATEWAY);
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating service type failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "updateServiceTypeBlueprint caught exception");
            json =
                objectMapper.writeValueAsString(new RestResponseError(e.getResponseBodyAsString()));
        } catch (Throwable t) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating service type failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "updateServiceTypeBlueprint caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("updateServiceTypeBlueprint failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(
        value = {SERVICE_TYPES_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    public String getBlueprintsByPage(HttpServletRequest request,
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size,
        ServletUriComponentsBuilder uriBuilder, HttpServletResponse response) {
        preLogAudit(request);
        String json = null;
        if (page == null || page == 0) {
            page = 1;
        }
        if (size == null) {
            size = 25;
        }
        int pageNum = page;
        int totalItems = 0;
        List itemList = null;
        try {
            itemList = getItemListForPageWrapper(request, InventoryDataItem.BLUEPRINTS);
            // Shrink if needed
            if (itemList != null) {
                totalItems = itemList.size();
            }
            final int pageCount = (int) Math.ceil((double) totalItems / size);
            if (totalItems > size)
                itemList = getPageOfList(pageNum, size, itemList);

            RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
            json = objectMapper.writeValueAsString(model);

            try {
            uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
            eventPub.publishEvent(new PaginatedResultsRetrievedEvent<String>(String.class,
                uriBuilder, response, page, pageCount, size));
            } catch (Exception e) {
                // skip exception
            }
        } catch (Exception e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of blueprints items failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getBlueprintsByPage caught exception");
            RestResponseError result = null;
            if (e instanceof HttpStatusCodeException)
                result =
                new RestResponseError(((HttpStatusCodeException) e).getResponseBodyAsString());
            else
                result = new RestResponseError("Failed to get blueprints", e);
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                json = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
            return json;
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    private List getItemListForPageWrapper(HttpServletRequest request, InventoryDataItem option) {
        Set<String> userRoleSet = (Set<String>) request.getAttribute("userRoles");
        Set<String> userApps = (Set<String>) request.getAttribute("userApps");
        String sort = request.getParameter("sort");

        Predicate<String> adminPred =
            p -> p.contains("System_Administrator") || p.contains("Write_Access");

            Predicate<String> ecompSuperPred =
                p -> p.contains("ECOMPC_WRITE") || p.contains("ECOMPC_READ");

                ReadWriteLock lock = new ReentrantReadWriteLock();
                List itemList = null;
                try {
                    lock.readLock().lock();
                    itemList = (List<ServiceTypeSummary>) getCacheManager().getObject("dcae-service-types");
                    lock.readLock().unlock();
                    if (itemList == null) {
                        ServiceTypeQueryParams serviceQueryParams = null;
                        itemList = inventoryClient.getServiceTypes().collect(Collectors.toList());
                    }
                    if (userRoleSet != null) {
                        if (userRoleSet.stream().noneMatch(adminPred)) {
                            if (userRoleSet.stream().noneMatch(ecompSuperPred)) {
                                itemList = (List<ServiceTypeSummary>) itemList.stream()
                                    .filter(s -> userApps.stream()
                                        .anyMatch(appFilter -> (((ServiceTypeSummary) s).getComponent() != null
                                        && ((ServiceTypeSummary) s).getComponent()
                                        .equalsIgnoreCase(appFilter))))
                                    .collect(Collectors.<ServiceTypeSummary>toList());
                            } else {
                                Predicate<ServiceTypeSummary> appFilter =
                                    p -> p.getComponent() != null && !p.getComponent().equalsIgnoreCase("dcae");
                                    itemList = (List<ServiceTypeSummary>) itemList.stream().filter(appFilter)
                                        .collect(Collectors.toList());
                            }
                        }
                    }
                    // Handle request filter object
                    String filters = request.getParameter("filters");
                    if (filters != null) {
                        JSONObject filterJson = new JSONObject(filters);

                        if (filterJson.has("owner")) {
                            String ownFilter = filterJson.getString("owner");
                            Predicate<ServiceTypeSummary> ownPred =
                                p -> p.getOwner() != null && p.getOwner().contains(ownFilter);
                                itemList = (List<ServiceTypeSummary>) itemList.stream().filter(ownPred)
                                    .collect(Collectors.toList());
                        }

                        if (filterJson.has("name")) {
                            String bpNameFilter = filterJson.getString("name");
                            Predicate<ServiceTypeSummary> bpNamePred =
                                p -> p.getTypeName() != null && p.getTypeName().contains(bpNameFilter);
                                itemList = (List<ServiceTypeSummary>) itemList.stream().filter(bpNamePred)
                                    .collect(Collectors.toList());
                        }

                        if (filterJson.has("id")) {
                            String bpIdFilter = filterJson.getString("id");
                            Predicate<ServiceTypeSummary> bpIdPred =
                                p -> p.getTypeId().get().contains(bpIdFilter);
                                itemList = (List<ServiceTypeSummary>) itemList.stream().filter(bpIdPred)
                                    .collect(Collectors.toList());
                        }
                    }
                    if (sort != null) {
                        if (sort.equals("owner")) {
                            ((List<ServiceTypeSummary>) itemList).sort((ServiceTypeSummary o1,
                                ServiceTypeSummary o2) -> o1.getOwner().compareTo(o2.getOwner()));
                        } else if (sort.equals("typeId")) {
                            ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getTypeId().get()
                                .compareTo(o2.getTypeId().get()));
                        } else if (sort.equals("typeName")) {
                            ((List<ServiceTypeSummary>) itemList).sort((ServiceTypeSummary o1,
                                ServiceTypeSummary o2) -> o1.getTypeName().compareTo(o2.getTypeName()));
                        } else if (sort.equals("typeVersion")) {
                            ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getTypeVersion()
                                .compareTo(o2.getTypeVersion()));
                        } else if (sort.equals("created")) {
                            ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getCreated()
                                .get().compareTo(o2.getCreated().get()));
                        }
                    }
                } catch (Exception e) {
                    throw e;
                } finally {
                    postLogAudit(request);
                }
                return itemList;
    }

    @RequestMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}"},
        method = RequestMethod.GET,
        produces = "application/json")
    public String getDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        try {
            List<CloudifyDeployment> items = cloudifyClient.getDeployment(deploymentId).items;
            json = objectMapper.writeValueAsString(items);
        } catch (Exception gen) {
            logger.error(EELFLoggerDelegate.errorLogger, "getDeployment caught exception");
            RestResponseError result = null;
            if (gen instanceof HttpStatusCodeException)
                result = new RestResponseError(
                    ((HttpStatusCodeException) gen).getResponseBodyAsString());
            else
                result = new RestResponseError("Failed to get deployment", gen);
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                json = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(
        value = {DEPLOYMENTS_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    public String getDeploymentsByPage(HttpServletRequest request,
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size,
        ServletUriComponentsBuilder uriBuilder, HttpServletResponse response) {
        preLogAudit(request);
        String outboundJson = null;
        if (page == null || page == 0) {
            page = 1;
        }
        if (size == null) {
            size = 25;
        }
        int pageNum = page;
        int totalItems = 0;
        List<CloudifyDeployment> itemList = null;
        try {
            itemList = cloudifyClient.getDeploymentsWithFilter(request);

            // Shrink if needed
            if (itemList != null) {
                totalItems = itemList.size();
            }
            final int pageCount = (int) Math.ceil((double) totalItems / size);
            if (totalItems > size) {
                itemList = getPageOfList(pageNum, size, itemList);
            }
            RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
            outboundJson = objectMapper.writeValueAsString(model);
            try {
                uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
                eventPub.publishEvent(new PaginatedResultsRetrievedEvent<String>(String.class,
                    uriBuilder, response, page, pageCount, size));
            } catch (Exception e) {
                // skip the pagination links logic
            }
            return outboundJson;
        } catch (Exception e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of deployments items failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getAllDeploymentsByPage caught exception");
            RestResponseError result = null;
            if (e instanceof HttpStatusCodeException)
                result =
                new RestResponseError(((HttpStatusCodeException) e).getResponseBodyAsString());
            else
                result = new RestResponseError("Failed to get deployments", e);
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
            return outboundJson;
        } finally {
            postLogAudit(request);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private ServiceTypeSummary getBlueprintItem(String searchBy, Optional<Integer> version,
        String typeId) throws Exception {
        ServiceTypeQueryParams serviceQueryParams = null;
        ServiceTypeSummary item = null;
        if (version.isPresent()) {
            serviceQueryParams =
                new ServiceTypeQueryParams.Builder().typeName(searchBy).onlyLatest(false).build();
        } else {
            serviceQueryParams = new ServiceTypeQueryParams.Builder().typeName(searchBy).build();
        }

        List itemList =
            inventoryClient.getServiceTypes(serviceQueryParams).collect(Collectors.toList());

        if (version.isPresent()) {
            itemList = (List) itemList.stream()
                .filter(s -> ((ServiceTypeSummary) s).contains(version.get().toString()))
                .collect(Collectors.toList());
        }
        if (typeId != null && typeId.equals("typeId")) {
            item = (ServiceTypeSummary) ((List) itemList).get(0);
        }
        return item;
    }

    /**
     * Query inputs used to create a deployment
     * 
     * @param deploymentId
     * @param tenant
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}/inputs"},
        method = RequestMethod.GET,
        produces = "application/json")
    public String getDeploymentInputs(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        String json = "";
        try {
            if (tenant == null) {
                throw new Exception("tenant name is missing");
            }
            List<CloudifyDeployment> response =
                cloudifyClient.getDeploymentInputs(deploymentId, tenant).items;
            json = objectMapper.writeValueAsString(response);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
            json = objectMapper.writeValueAsString(result);
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError("getExecutionByIdAndDeploymentId failed", t);
            json = objectMapper.writeValueAsString(result);
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Create an upgrade/rollback workflow execution for a deployment.
     * 
     * @param request HttpServletRequest
     * @param execution Execution model
     * @return Information about the execution
     * @throws Exception on serialization failure
     */
    @RequestMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}"},
        method = RequestMethod.PUT,
        produces = "application/json")
    public String modifyDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request, InputStream upgParams) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            String nodeInstId = "";
            Map<String, Object> parameters =
                objectMapper.readValue(upgParams, new TypeReference<Map<String, Object>>() {});
            String tenant = (String) parameters.get("tenant");
            String workflow = (String) parameters.get("workflow");
            parameters.remove("tenant");
            parameters.remove("workflow");
            // get the node instance ID for the deployment
            CloudifyNodeInstanceIdList nodeInstList =
                cloudifyClient.getNodeInstanceId(deploymentId, tenant);
            if (nodeInstList != null) {
                nodeInstId = nodeInstList.items.get(0).id;
            }
            parameters.put("node_instance_id", nodeInstId);
            if (workflow.equals("upgrade")) {
                String repo_user = cloudifyClient.getSecret("controller_helm_user", tenant).value;
                String repo_user_password =
                    cloudifyClient.getSecret("controller_helm_password", tenant).value;
                parameters.put("repo_user", repo_user);
                parameters.put("repo_user_password", repo_user_password);
            }
            CloudifyExecutionRequest execution = new CloudifyExecutionRequest(deploymentId,
                workflow, false, false, tenant, parameters);
            result = cloudifyClient.startExecution(execution);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateDeployment caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
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

    @RequestMapping(
        value = {SERVICE_TYPES_PATH + "/{typeid}" + "/deployments"},
        method = RequestMethod.GET,
        produces = "application/json")
    public String getServicesForType(HttpServletRequest request,
        @PathVariable("typeid") String typeId) throws Exception {
        preLogAudit(request);
        List<ServiceTypeServiceMap> result = new ArrayList<ServiceTypeServiceMap>();
        ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
        ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
        ServiceTypeServiceMap srvcMap = new ServiceTypeServiceMap(typeId, srvcRefs);
        result.add(srvcMap);
        return objectMapper.writeValueAsString(result);
    }

    private static void mergeInputData(Map<String, Object> bpInputs, Map<String, Object> dataSet,
        String key, Object val) {
        Object keyVal = dataSet.get(key);
        if (keyVal != null) {
            bpInputs.put(key, keyVal);
        } else {
            Object bpInput = ((BlueprintInput) val).getDefaultValue();
            if (bpInput != null) {
                bpInputs.put(key, bpInput);
            } else {
                bpInputs.put(key, "");
            }
        }
    }

    private static void translateInputData(Map<String, Object> dataSet, String key, Object val) {
        try {
            if (val instanceof java.lang.String) {
                int numericVal = Integer.parseInt((String) val);
                dataSet.put(key, numericVal);
            } else {
                dataSet.put(key, val);
            }
        } catch (NumberFormatException ne) {
            dataSet.put(key, val);
        } catch (Exception e) {
            dataSet.put(key, val);
        }
    }

    @RequestMapping(
        value = {DEPLOYMENTS_PATH},
        method = RequestMethod.POST,
        produces = "application/json")
    public String createDeployment(HttpServletRequest request, HttpServletResponse response,
        @RequestBody DeploymentInput deploymentRequestObject) throws Exception {
        preLogAudit(request);
        String json = null;
        StringBuffer status = new StringBuffer();
        Optional<Integer> bpVersion = null;
        String srvcTypeId = null;
        ServiceTypeSummary bpSummItem = null;
        ServiceType bpItem = null;
        String bpName = deploymentRequestObject.getBlueprintName();
        String cName = deploymentRequestObject.getComponent();
        String tag = deploymentRequestObject.getTag();
        String depName = cName + "_" + tag;
        Map<String, BlueprintInput> bpInputDefs = null;

        if (cName == null || cName.isEmpty()) {
            json = objectMapper.writeValueAsString(
                new RestResponseError("Component name missing in deployment request body"));
            logger.error(EELFLoggerDelegate.errorLogger,
                "Component name missing in deployment request body");
            return json;
        }

        if (!isAuthorized(request, cName)) {
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            json = objectMapper.writeValueAsString(
                new RestResponseError("Un-authorized to perform this operation"));
            return json;
        }

        if (deploymentRequestObject.getBlueprintVersion().isPresent()) {
            bpVersion = deploymentRequestObject.getBlueprintVersion();
        }
        if (deploymentRequestObject.getBlueprintId().isPresent()) {
            srvcTypeId = deploymentRequestObject.getBlueprintId().get();
        }
        if (srvcTypeId == null) {
            // get the serviceTypeId from inventory using the blueprint name
            try {
                bpSummItem = getBlueprintItem(bpName, bpVersion, "typeId");
                srvcTypeId = bpSummItem.getTypeId().get();
                bpItem = inventoryClient.getServiceType(srvcTypeId).get();
            } catch (Exception ex) {
                MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                MDC.put("TargetEntity", "API controller");
                MDC.put("TargetServiceName", "API controller");
                MDC.put("ErrorCode", "300");
                MDC.put("ErrorCategory", "ERROR");
                MDC.put("ErrorDescription", "Getting blueprint ID failed!");
                logger.error(EELFLoggerDelegate.errorLogger,
                    "getItemListForPageWrapper caught exception");
                RestResponseError result = null;
                if (ex instanceof HttpStatusCodeException)
                    result = new RestResponseError(
                        ((HttpStatusCodeException) ex).getResponseBodyAsString());
                else
                    result = new RestResponseError("Failed to get blueprint", ex);
                try {
                    json = objectMapper.writeValueAsString(result);
                } catch (JsonProcessingException jpe) {
                    // Should never, ever happen
                    json = "{ \"error\" : \"" + jpe.toString() + "\"}";
                }
                return json;
            } finally {
                postLogAudit(request);
            }
        } else {
            try {
                bpItem = inventoryClient.getServiceType(srvcTypeId).get();
            } catch (Exception ex) {
                MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                MDC.put("TargetEntity", "API controller");
                MDC.put("TargetServiceName", "API controller");
                MDC.put("ErrorCode", "300");
                MDC.put("ErrorCategory", "ERROR");
                MDC.put("ErrorDescription", "Getting blueprint failed!");
                logger.error(EELFLoggerDelegate.errorLogger, "createDeployment caught exception");
                RestResponseError result = null;
                if (ex instanceof HttpStatusCodeException)
                    result = new RestResponseError(
                        ((HttpStatusCodeException) ex).getResponseBodyAsString());
                else
                    result = new RestResponseError("Failed to get blueprint", ex);
                try {
                    json = objectMapper.writeValueAsString(result);
                } catch (JsonProcessingException jpe) {
                    // Should never, ever happen
                    json = "{ \"error\" : \"" + jpe.toString() + "\"}";
                }
                return json;
            } finally {
                postLogAudit(request);
            }
        }
        try {
            // process the JSON inputs
            Map<String, Object> processedInputs = new HashMap<String, Object>();
            Map<String, Object> mergedInputs = new HashMap<String, Object>();
            deploymentRequestObject.getInputs()
            .forEach((k, v) -> translateInputData(processedInputs, k, v));
            // merge the user inputs with BP input list
            bpInputDefs = bpItem.getBlueprintInputs();
            bpInputDefs.forEach((k, v) -> mergeInputData(mergedInputs, processedInputs, k, v));
            DeploymentResponse resp =
                deploymentHandlerClient.putDeployment(depName, deploymentRequestObject.getTenant(),
                    new DeploymentRequest(srvcTypeId, mergedInputs));
            DeploymentResponseLinks deplLinks = resp.getLinks();
            String deplStatus = deplLinks.getStatus();
            if (!deplStatus.contains("cfy_tenant")) {
                deplStatus = deplStatus + "?cfy_tenant_name=" + deploymentRequestObject.getTenant();
            }
            String self = request.getRequestURL().append("/").append(depName).toString();
            status.append(self).append("/executions?tenant=")
            .append(deploymentRequestObject.getTenant());
            DeploymentResource deplRsrc = new DeploymentResource(depName,
                new DeploymentResourceLinks(self, deplStatus, status.toString()));
            JSONObject statObj = new JSONObject(deplRsrc);
            json = statObj.toString();
            response.setStatus(HttpStatus.SC_ACCEPTED);
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "createDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServiceAlreadyExistsException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "createDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "createDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "createDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API controller");
            MDC.put("TargetServiceName", "API controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "createDeployment caught exception");
            json =
                objectMapper.writeValueAsString(new RestResponseError("putDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Get the executions for one deployment.
     * 
     * 
     * @param deployment_id Deployment ID (query parameter)
     * @param request HttpServletRequest
     * @return CloudifyExecutionList
     * @throws Exception on serialization failure
     */
    @RequestMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}" + "/" + EXECUTIONS_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    @ResponseBody
    public String getExecutionByDeploymentId(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
            throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("tenant name is missing");
            }
            result = cloudifyClient.getExecutionsSummary(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError("getExecutionByIdAndDeploymentId failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the service health for a deployment
     * 
     * 
     * @param deployment_id Deployment ID (query parameter)
     * @param request HttpServletRequest
     * @return String
     * @throws Exception on serialization failure
     */
    @RequestMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}" + "/" + SERVICE_HEALTH_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    @ResponseBody
    public String getServiceHealthByDeploymentId(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request) throws Exception {
        preLogAudit(request);
        Object result = null;
        try {
            result = consulClient.getServiceHealthByDeploymentId(deploymentId);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError("getExecutionByIdAndDeploymentId failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Query the specified blueprint.
     *
     * @param id Blueprint ID
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return status code on success; error on failure.
     * @throws Exception On serialization failure
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(
        value = {SERVICE_TYPES_PATH + "/{typeid}"},
        method = RequestMethod.GET,
        produces = "application/json")
    @ResponseBody
    public String queryBlueprint(@PathVariable("typeid") String typeId, HttpServletRequest request,
        HttpServletResponse response, ServletUriComponentsBuilder uriBuilder) throws Exception {
        String json = "";
        Optional<ServiceType> resultItem = null;
        try {
            resultItem = inventoryClient.getServiceType(typeId);
            if (resultItem.isPresent()) {
                json = objectMapper.writeValueAsString(resultItem.get());
            }
            String uri = request.getRequestURI();
            if (uri != null && !uri.isEmpty()) {
            String uri_depl =
                uriBuilder.replacePath(uri).path("/deployments").build().toUriString();
            StringBuffer linkHeader = new StringBuffer();
            String linkStr_depl = "<" + uri_depl + ">; rel=\"" + "deployments" + "\"";
            linkHeader.append(linkStr_depl);
            response.addHeader("Link", linkHeader.toString());
            }
        } catch (Exception e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of blueprints items failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getBlueprintsByPage caught exception");
            RestResponseError result = null;
            if (e instanceof HttpStatusCodeException)
                result =
                new RestResponseError(((HttpStatusCodeException) e).getResponseBodyAsString());
            else
                result = new RestResponseError("Failed to get blueprints", e);
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                json = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        }
        return json;
    }

    /**
     * Deletes the specified blueprint.
     *
     * @param id Blueprint ID
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return status code on success; error on failure.
     * @throws Exception On serialization failure
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(
        value = {SERVICE_TYPES_PATH + "/{typeid}"},
        method = RequestMethod.DELETE,
        produces = "application/json")
    @ResponseBody
    public String deleteBlueprint(@PathVariable("typeid") String typeId, HttpServletRequest request,
        HttpServletResponse response, ServletUriComponentsBuilder uriBuilder) throws Exception {
        preLogAudit(request);
        String json = "{\"204\": \"Blueprint deleted\"}";
        String bpComp = "";
        boolean allow = true;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        @SuppressWarnings("unchecked")
        List itemList =
        (List<ServiceTypeSummary>) getCacheManager().getObject("dcae-service-types");
        lock.readLock().unlock();
        try {
            if (itemList != null) {
                Predicate<ServiceTypeSummary> idFilter = p -> p.getTypeId().get().equals(typeId);

                itemList = (List<ServiceTypeSummary>) itemList.stream().filter(idFilter)
                    .collect(Collectors.toList());
                bpComp = ((ServiceTypeSummary) itemList.get(0)).getComponent();
            } else {
                try {
                    ServiceType item = inventoryClient.getServiceType(typeId).get();
                    bpComp = ((ServiceType) item).getComponent();
                } catch (NoSuchElementException nse) {
                    throw new ServiceTypeNotFoundException("invalid blueprint ID given in query");
                }
            }
            if (!isAuthorized(request, bpComp)) {
                response.setStatus(HttpStatus.SC_FORBIDDEN);
                json = objectMapper.writeValueAsString(
                    new RestResponseError("Un-authorized to perform this operation"));
                return json;
            }
            /*
            ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
            ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
            if (srvcRefs != null && srvcRefs.totalCount > 0) {
                throw new Exception(
                    "Services exist for the service type template, delete not permitted");
            }
            */
            
            // check if these dep_ids exist in cloudify
            List<CloudifyDeployedTenant> deplForBp = 
                cloudifyClient.getDeploymentForBlueprint("TID-"+typeId);
            if (deplForBp.size() > 0 ) {
                allow = false;
            } else {                        
                ServiceQueryParams qryParams = 
                    new ServiceQueryParams.Builder().typeId(typeId).build();
                ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
                Set<String> dep_ids = srvcRefs.items.stream().map(x -> ((ServiceRef)x).id).collect(Collectors.toSet());  
                if (dep_ids.size() > 0) {
                    // now check again if these dep_ids still exist in cloudify
                    List<String> allDepNames = cloudifyClient.getDeploymentNamesWithFilter(request);
                    for (String str: dep_ids) {
                        if (allDepNames.stream().anyMatch(s->s.equalsIgnoreCase(str))) {
                            allow = false;
                            break;
                        }
                    }
                }
            }
            if (!allow) {
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                json = objectMapper.writeValueAsString(
                    new RestResponseError("ERROR: Deployments exist for this blueprint"));
                /*
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(json);
                out.flush();
                */
                return json;
            } else {
                inventoryClient.deleteServiceType(typeId);
                String uri = request.getRequestURI();
                if (uri != null && !uri.isEmpty()) {
                    String uri_str = uri.substring(0, uri.lastIndexOf("/"));
                    String uri_all = uriBuilder.replacePath(uri_str).build().toUriString();
                    StringBuffer linkHeader = new StringBuffer();
                    String linkStr = "<" + uri_all + ">; rel=\"" + "current" + "\"";
                    linkHeader.append(linkStr);
                    response.addHeader("Link", linkHeader.toString());
                }
            }
        } catch (ServiceTypeNotFoundException e) {
            response.setStatus(HttpStatus.SC_GONE);
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServiceTypeAlreadyDeactivatedException e) {
            response.setStatus(HttpStatus.SC_GONE);
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            response.setStatus(HttpStatus.SC_BAD_GATEWAY);
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json =
                objectMapper.writeValueAsString(new RestResponseError("deleteBlueprint failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Un-deploy an application or service
     * 
     * @param deploymentId
     * @param request
     * @param tenant
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}"},
        method = RequestMethod.DELETE,
        produces = "application/json")
    public String deleteDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request, @RequestParam(value = "tenant", required = true) String tenant,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = null;
        StringBuffer status = new StringBuffer();
        try {
            if (tenant == null) {
                throw new Exception("tenant name is missing");
            }
            ReadWriteLock lock = new ReentrantReadWriteLock();
            lock.readLock().lock();
            @SuppressWarnings("unchecked")
            List itemList = (List<CloudifyDeployment>) getCacheManager()
            .getObject("service-list" + ":" + tenant);
            lock.readLock().unlock();
            if (itemList != null) {
                Predicate<CloudifyDeployment> idFilter = p -> p.id.equals(deploymentId);
                itemList = (List<CloudifyDeployment>) itemList.stream().filter(idFilter)
                    .collect(Collectors.toList());
            } else {
                CloudifyDeploymentList cfyDeplList =
                    cloudifyClient.getDeployment(deploymentId, tenant);
                if (cfyDeplList != null && !cfyDeplList.items.isEmpty()) {
                    itemList = cfyDeplList.items;
                }
            }
            if (itemList != null && !itemList.isEmpty()) {
                String depItem = ((CloudifyDeployment) itemList.get(0)).id;
                if (depItem.contains("_")) {
                    String depComp = depItem.split("_")[0];
                    if (!isAuthorized(request, depComp)) {
                        response.setStatus(HttpStatus.SC_FORBIDDEN);
                        json = objectMapper.writeValueAsString(
                            new RestResponseError("Un-authorized to perform this operation"));
                        return json;
                    }
                    deploymentHandlerClient.deleteDeployment(deploymentId, tenant);
                    String self = request.getRequestURL().toString().split("\\?")[0];
                    status.append(self).append("/executions?tenant=").append(tenant);
                    DeploymentResource deplRsrc = new DeploymentResource(deploymentId,
                        new DeploymentResourceLinks(self, "", status.toString()));
                    JSONObject statObj = new JSONObject(deplRsrc);
                    json = statObj.toString();
                }
            }
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DeploymentNotFoundException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("deleteDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Cancels an execution.
     * 
     * @param id Execution ID
     * @param deploymentId Deployment ID (not clear why this is needed)
     * @param action Action to perform (not clear why this is needed)
     * @param request HttpServletRequest
     * @param response HttpServletRequest
     * @return Passes thru HTTP status code from remote endpoint; no body on success
     * @throws Exception on serialization failure
     */
    @RequestMapping(
        value = {EXECUTIONS_PATH + "/{id}"},
        method = RequestMethod.POST,
        produces = "application/json")
    @ResponseBody
    public String cancelExecution(@RequestHeader HttpHeaders headers, @PathVariable("id") String id,
        @RequestBody Map<String, String> parameters, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        List<String> tenant = null;
        try {
            tenant = headers.get("tenant");
            result = cloudifyClient.cancelExecution(id, parameters, tenant.get(0));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Cancelling execution " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "cancelExecution caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "API Controller");
            MDC.put("TargetServiceName", "API Controller");
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

    private void preLogAudit(HttpServletRequest request) {
        begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
        String user = null;
        if (request instanceof CadiWrap) {
            user = ((CadiWrap) request).getUser();
        } 
        logger.setRequestBasedDefaultsIntoGlobalLoggingContext(request, APP_NAME, user, user);
    }

    private void postLogAudit(HttpServletRequest request) {
        end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put("TargetEntity", "API Controller");
        MDC.put("TargetServiceName", "API Controller");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        // MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger,
            request.getMethod() + " " + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + " " + request.getRequestURI());
    }
}
