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
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenant;
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
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private static final String TARGET_ENTITY_KEY = "TargetEntity";
    private static final String TARGET_SERVICE_KEY = "TargetServiceName";
    private static final String INV_BP_ENTITY = "DCAE Blueprint";
    private static final String INV_TARGET_SERVICE = "DCAE Inventory";
    private static final String CFY_DEP_ENTITY = "DCAE Deployment";
    private static final String CFY_TARGET_SERVICE = "DCAE Cloudify";
    private static final String CFY_EXEC_ENTITY = "Executions";
    private static final String CNSL_SVC_ENTITY = "Consul Service";
    private static final String CNSL_TARGET_SERVICE = "Consul API";
    private static final String ERROR_RESPONSE = "ERROR";
    private static final String ERROR_CODE_KEY = "ErrorCode";
    private static final String ERROR_CODE = "300";
    private static final String ERROR_CATEGORY_KEY = "ErrorCategory";
    private static final String ERROR_CATEGORY = "ERROR";
    private static final String ERROR_DESCRIPTION_KEY = "ErrorDescription";

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

    /**
     * get the tenants list.
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
    @SuppressWarnings({"unchecked"})
    @GetMapping(value = TENANTS_PATH, produces = "application/json")
    public String getTenants(HttpServletRequest request) throws Exception {
        preLogAudit(request);
        List<CloudifyTenant> itemList = cloudifyClient.getTenants().items;
        final int totalItems = itemList.size();
        final int pageSize = 20;
        final int pageNum = 1;
        final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        if (totalItems > pageSize) {
            itemList = getPageOfList(pageNum, pageSize, itemList);
        }
        RestResponsePage<List<CloudifyTenant>> model =
            new RestResponsePage<>(totalItems, pageCount, itemList);
        return objectMapper.writeValueAsString(model);
    }

    private boolean isAuthorized() {
        return true;
    }

    private String getSelfLink(String uriSelf) {
        StringBuffer selfSb = new StringBuffer();
        selfSb.append("<").append(uriSelf).append(">; rel=\"self\"");
        return selfSb.toString();
    }

    private String getAllLink(String uriAll) {
        StringBuffer allSb = new StringBuffer();
        allSb.append("<").append(uriAll).append(">; rel=\"current\"");
        return allSb.toString();
    }

    private void publishResponseHeader(ServletUriComponentsBuilder uriBuilder,
        HttpServletResponse response, int page, int pageCount, int size) {
        try {
            uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
            eventPub.publishEvent(new PaginatedResultsRetrievedEvent<String>(String.class,
                uriBuilder, response, page, pageCount, size));
        } catch (Exception e) {
            // skip exception
        }
    }

    private String getJsonErr(String errStr) {
        final String errJsonStr = "{ \"error\" : \"";
        StringBuffer errJson = new StringBuffer();
        errJson.append(errJsonStr).append(errStr).append("\"}");
        return errJson.toString();
    }

    /**
     * Upload new blueprint to inventory.
     * 
     * @param request
     * @param response
     * @param serviceTypeUplReq
     * @param uriBuilder
     * @return
     * @throws Exception
     */
    @PostMapping(value = {SERVICE_TYPES_PATH}, produces = "application/json")
    public String createBlueprint(HttpServletRequest request, HttpServletResponse response,
        @RequestBody ServiceTypeUploadRequest serviceTypeUplReq,
        ServletUriComponentsBuilder uriBuilder) throws Exception {
        preLogAudit(request);
        String json = null;
        final String errDescrStr = "Uploading service type failed!";
        final String errLogStr = "Uploading service type caught exception";
        try {
            Blueprint.parse(serviceTypeUplReq.getBlueprintTemplate());

            if (serviceTypeUplReq.component == null || serviceTypeUplReq.component.isEmpty()) {
                json = objectMapper.writeValueAsString(
                    new RestResponseError("Component name missing in blueprint request body"));
                logger.error(EELFLoggerDelegate.errorLogger,
                    "Component name missing in blueprint request body");
                return json;
            }
            Collection<String> serviceIds = new ArrayList<>();
            Collection<String> vnfTypes = new ArrayList<>();
            Collection<String> serviceLocations = new ArrayList<>();
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
                String uriAll = uriBuilder.replacePath(uri).build().toUriString();
                StringBuffer linkHeader = new StringBuffer();
                String currTypeId = apiResponse.getTypeId().orElse("missingId");
                String uriSelf = uriBuilder.path("/" + currTypeId).build().toUriString();
                linkHeader.append(getSelfLink(uriSelf));
                if (linkHeader.length() > 0) {
                    linkHeader.append(", ");
                }
                linkHeader.append(getAllLink(uriAll));
                response.addHeader("Link", linkHeader.toString());
            }
        } catch (BlueprintParseException e) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper
                .writeValueAsString(new RestResponseError("Invalid blueprint format.", e));
        } catch (HttpStatusCodeException e) {
            response.setStatus(HttpStatus.SC_BAD_GATEWAY);
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json =
                objectMapper.writeValueAsString(new RestResponseError(e.getResponseBodyAsString()));
        } catch (Exception t) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper
                .writeValueAsString(new RestResponseError("updateServiceTypeBlueprint failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Query all blueprints from inventory, follows pagination.
     * 
     * @param request
     * @param page
     * @param size
     * @param uriBuilder
     * @param response
     * @return
     */
    @GetMapping(value = {SERVICE_TYPES_PATH}, produces = "application/json")
    public String getBlueprintsByPage(HttpServletRequest request,
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "size", required = false) Integer size,
        ServletUriComponentsBuilder uriBuilder, HttpServletResponse response) {
        preLogAudit(request);
        String json = null;
        RestResponseError result = null;
        final String errDescrStr = "Getting page of blueprints items failed!";
        final String errLogStr = "getBlueprintsByPage caught exception";
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
            itemList = getItemListForPageWrapper(request);
            // Shrink if needed
            if (itemList != null) {
                totalItems = itemList.size();
            }
            final int pageCount = (int) Math.ceil((double) totalItems / size);
            if (totalItems > size) {
                itemList = getPageOfList(pageNum, size, itemList);
            }
            RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
            json = objectMapper.writeValueAsString(model);
            publishResponseHeader(uriBuilder, response, page, pageCount, size);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError((e).getResponseBodyAsString());
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                json = getJsonErr(jpe.toString());
            }
        } catch (Exception e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("Failed to get blueprints", e);
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                json = getJsonErr(jpe.toString());
            }
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    private List<ServiceTypeSummary> processRequestFilters(HttpServletRequest request,
        List<ServiceTypeSummary> itemList) {
        String filters = request.getParameter("filters");
        final String ownerStr = "owner";
        if (filters != null) {
            JSONObject filterJson = new JSONObject(filters);
            if (filterJson.has(ownerStr)) {
                String ownFilter = filterJson.getString(ownerStr);
                Predicate<ServiceTypeSummary> ownPred =
                    p -> p.getOwner() != null && p.getOwner().contains(ownFilter);
                itemList = itemList.stream().filter(ownPred).collect(Collectors.toList());
            }
            if (filterJson.has("name")) {
                String bpNameFilter = filterJson.getString("name");
                Predicate<ServiceTypeSummary> bpNamePred =
                    p -> p.getTypeName() != null && p.getTypeName().contains(bpNameFilter);
                itemList = itemList.stream().filter(bpNamePred).collect(Collectors.toList());
            }
            if (filterJson.has("id")) {
                String bpIdFilter = filterJson.getString("id");
                Predicate<ServiceTypeSummary> bpIdPred =
                    p -> p.getTypeId().get().contains(bpIdFilter);
                itemList = itemList.stream().filter(bpIdPred).collect(Collectors.toList());
            }
        }
        return itemList;
    }

    @SuppressWarnings("unchecked")
    private List getItemListForPageWrapper(HttpServletRequest request) throws Exception {
        String sort = request.getParameter("sort");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<ServiceTypeSummary> itemList = null;
        try {
            lock.readLock().lock();
            itemList = (List<ServiceTypeSummary>) getCacheManager().getObject("dcae-service-types");
            lock.readLock().unlock();
            if (itemList == null) {
                itemList = inventoryClient.getServiceTypes().collect(Collectors.toList());
            }
            // Handle request filter object
            itemList = processRequestFilters(request, itemList);

            if (sort != null) {
                if (sort.equals("owner")) {
                    (itemList).sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getOwner()
                        .compareTo(o2.getOwner()));
                } else if (sort.equals("typeId")) {
                    (itemList).sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getTypeId()
                        .get().compareTo(o2.getTypeId().get()));
                } else if (sort.equals("typeName")) {
                    (itemList).sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1
                        .getTypeName().compareTo(o2.getTypeName()));
                } else if (sort.equals("typeVersion")) {
                    (itemList).sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1
                        .getTypeVersion().compareTo(o2.getTypeVersion()));
                } else if (sort.equals("created")) {
                    (itemList).sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1
                        .getCreated().get().compareTo(o2.getCreated().get()));
                }
            }
        } finally {
            postLogAudit(request);
        }
        return itemList;
    }

    @GetMapping(value = {DEPLOYMENTS_PATH + "/{deploymentId}"}, produces = "application/json")
    public String getDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        try {
            List<CloudifyDeployment> items = cloudifyClient.getDeployment(deploymentId).items;
            json = objectMapper.writeValueAsString(items);
        } catch (HttpStatusCodeException gen) {
            logger.error(EELFLoggerDelegate.errorLogger, "getDeployment caught exception");
            RestResponseError result = null;
            result = new RestResponseError(gen.getResponseBodyAsString());
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                json = getJsonErr(jpe.toString());
            }
        } catch (Exception gen) {
            logger.error(EELFLoggerDelegate.errorLogger, "getDeployment caught exception");
            RestResponseError result = null;
            result = new RestResponseError("Failed to get deployment", gen);
            try {
                json = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                json = getJsonErr(jpe.toString());
            }
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = {DEPLOYMENTS_PATH}, produces = "application/json")
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
            publishResponseHeader(uriBuilder, response, page, pageCount, size);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, "Getting page of deployments items failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getAllDeploymentsByPage caught exception");
            RestResponseError result = null;
            result = new RestResponseError(e.getResponseBodyAsString());
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = getJsonErr(jpe.toString());
            }
        } catch (Exception e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, "Getting page of deployments items failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getAllDeploymentsByPage caught exception");
            RestResponseError result = null;
            result = new RestResponseError("Failed to get deployments", e);
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = getJsonErr(jpe.toString());
            }
        } finally {
            postLogAudit(request);
        }
        return outboundJson;
    }

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

        List<ServiceTypeSummary> itemList =
            inventoryClient.getServiceTypes(serviceQueryParams).collect(Collectors.toList());

        if (version.isPresent()) {
            itemList = itemList.stream().filter(s -> (s).contains(version.get().toString()))
                .collect(Collectors.toList());
        }
        if (typeId != null && typeId.equals("typeId")) {
            item = itemList.get(0);
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
    @GetMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}/inputs"},
        produces = "application/json")
    public String getDeploymentInputs(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        String json = "";
        String errDescrStr = "Getting inputs for deployment failed: " + deploymentId;
        String errLogStr = "getDeploymentInputs caught exception";
        try {
            List<CloudifyDeployment> response =
                cloudifyClient.getDeploymentInputs(deploymentId, tenant).items;
            json = objectMapper.writeValueAsString(response);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
            json = objectMapper.writeValueAsString(result);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescrStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getDeploymentInputs failed", t);
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
    @PutMapping(value = {DEPLOYMENTS_PATH + "/{deploymentId}"}, produces = "application/json")
    public String modifyDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request, InputStream upgParams) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errDescStr = "Updating deployment failed!";
        final String errLogStr = "updateDeployment caught exception";
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
                String repoUser = cloudifyClient.getSecret("controller_helm_user", tenant).value;
                String repoUserPassword =
                    cloudifyClient.getSecret("controller_helm_password", tenant).value;
                parameters.put("repo_user", repoUser);
                parameters.put("repo_user_password", repoUserPassword);
            }
            CloudifyExecutionRequest execution = new CloudifyExecutionRequest(deploymentId,
                workflow, false, false, tenant, parameters);
            result = cloudifyClient.startExecution(execution);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_EXEC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_EXEC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("updateDeployment failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    @GetMapping(
        value = {SERVICE_TYPES_PATH + "/{typeid}" + "/deployments"},
        produces = "application/json")
    public String getServicesForType(HttpServletRequest request,
        @PathVariable("typeid") String typeId) throws Exception {
        preLogAudit(request);
        List<ServiceTypeServiceMap> result = new ArrayList<>();
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
        } catch (Exception ne) {
            dataSet.put(key, val);
        }
    }

    @PostMapping(value = {DEPLOYMENTS_PATH}, produces = "application/json")
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
        final String errLogStr = "createDeployment caught exception";

        if (cName == null || cName.isEmpty()) {
            json = objectMapper.writeValueAsString(
                new RestResponseError("Component name missing in deployment request body"));
            logger.error(EELFLoggerDelegate.errorLogger,
                "Component name missing in deployment request body");
            return json;
        }
        try {
            bpVersion = Optional.of(deploymentRequestObject.getBlueprintVersion().orElse(1));
            srvcTypeId = deploymentRequestObject.getBlueprintId().orElse("");
            if (srvcTypeId.isEmpty()) {
                // get the serviceTypeId from inventory using the blueprint name
                bpSummItem = getBlueprintItem(bpName, bpVersion, "typeId");
                if (bpSummItem != null && bpSummItem.getTypeId().isPresent()) {
                    srvcTypeId = bpSummItem.getTypeId().get();
                }
            }
            bpItem = inventoryClient.getServiceType(srvcTypeId).orElseThrow();

            // process the JSON inputs
            Map<String, Object> processedInputs = new HashMap<>();
            Map<String, Object> mergedInputs = new HashMap<>();
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
        } catch (HttpStatusCodeException | BadRequestException | ServiceAlreadyExistsException
            | ServerErrorException | DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errLogStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errLogStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
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
    @GetMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}" + "/" + EXECUTIONS_PATH},
        produces = "application/json")
    public String getExecutionByDeploymentId(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errDescStr = "Get execution failed for deployment: " + deploymentId;
        final String errLogStr = "getExecutionByIdAndDeploymentId caught exception";
        try {
            result = cloudifyClient.getExecutionsSummary(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_EXEC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_EXEC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errDescStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
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
    @GetMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId}" + "/" + SERVICE_HEALTH_PATH},
        produces = "application/json")
    public String getServiceHealthByDeploymentId(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request) throws Exception {
        preLogAudit(request);
        Object result = null;
        final String errStr = "Getting service health for deployment failed: " + deploymentId;
        try {
            result = consulClient.getServiceHealthByDeploymentId(deploymentId);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CNSL_SVC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CNSL_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            result = new RestResponseError("getServiceHealthByDeploymentId failed", t);
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
    @GetMapping(value = {SERVICE_TYPES_PATH + "/{typeid}"}, produces = "application/json")
    public String queryBlueprint(@PathVariable("typeid") String typeId, HttpServletRequest request,
        HttpServletResponse response, ServletUriComponentsBuilder uriBuilder) throws Exception {
        String json = "";
        Optional<ServiceType> resultItem = null;
        final String errStr = "Failed to get blueprint for ID: " + typeId;
        try {
            resultItem = inventoryClient.getServiceType(typeId);
            if (resultItem.isPresent()) {
                json = objectMapper.writeValueAsString(resultItem.get());
            }
            String uri = request.getRequestURI();
            if (uri != null && !uri.isEmpty()) {
                String uriDepl =
                    uriBuilder.replacePath(uri).path("/deployments").build().toUriString();
                StringBuffer linkHeader = new StringBuffer();
                String linkStrDepl = "<" + uriDepl + ">; rel=\"" + DEPLOYMENTS_PATH + "\"";
                linkHeader.append(linkStrDepl);
                response.addHeader("Link", linkHeader.toString());
            }
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            RestResponseError result = new RestResponseError(e.getResponseBodyAsString());
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            RestResponseError result = new RestResponseError("Failed to get blueprints", e);
            json = objectMapper.writeValueAsString(result);
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
    @DeleteMapping(
        value = {SERVICE_TYPES_PATH + "/{typeId:.+}"},
        produces = "application/json")
    public String deleteBlueprint(@PathVariable("typeId") String typeId, HttpServletRequest request,
        HttpServletResponse response, ServletUriComponentsBuilder uriBuilder) throws Exception {
        preLogAudit(request);
        String json = "{\"204\": \"Blueprint deleted\"}";
        final String errStr = "Deleting service type failed: " + typeId;
        boolean allow = true;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        List<ServiceTypeSummary> itemList =
            (List<ServiceTypeSummary>) getCacheManager().getObject("dcae-service-types");
        lock.readLock().unlock();
        ServiceType itemFull = null;
        ServiceTypeSummary item = null;
        try {
            if (itemList != null) {
                Predicate<ServiceTypeSummary> idFilter = p -> p.getTypeId().get().equals(typeId);
                itemList = itemList.stream().filter(idFilter).collect(Collectors.toList());
                if (!itemList.isEmpty()) { 
                    item = itemList.get(0);
                }
            } else {
                itemFull = inventoryClient.getServiceType(typeId).get();
            }
            if (item != null || itemFull != null) {
                // check if these dep_ids exist in cloudify
                List<CloudifyDeployedTenant> deplForBp =
                    cloudifyClient.getDeploymentForBlueprint("TID-" + typeId);
                if (!deplForBp.isEmpty()) {
                    allow = false;
                } else {
                    ServiceQueryParams qryParams =
                        new ServiceQueryParams.Builder().typeId(typeId).build();
                    ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
                    Set<String> depIds =
                        srvcRefs.items.stream().map(x -> x.id).collect(Collectors.toSet());
                    if (!depIds.isEmpty()) {
                        // now check again if these dep_ids still exist in cloudify
                        List<String> allDepNames =
                            cloudifyClient.getDeploymentNamesWithFilter(request);
                        for (String str : depIds) {
                            if (allDepNames.stream().anyMatch(s -> s.equalsIgnoreCase(str))) {
                                allow = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (!allow) {
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                json = objectMapper.writeValueAsString(
                    new RestResponseError("ERROR: Deployments exist for this blueprint"));
                return json;
            } else {
                inventoryClient.deleteServiceType(typeId);
                String uri = request.getRequestURI();
                if (uri != null && !uri.isEmpty()) {
                    String uriStr = uri.substring(0, uri.lastIndexOf("/"));
                    String uriAll = uriBuilder.replacePath(uriStr).build().toUriString();
                    StringBuffer linkHeader = new StringBuffer();
                    String linkStr = "<" + uriAll + ">; rel=\"" + "current" + "\"";
                    linkHeader.append(linkStr);
                    response.addHeader("Link", linkHeader.toString());
                }
            }
        } catch (ServiceTypeNotFoundException | ServiceTypeAlreadyDeactivatedException e) {
            response.setStatus(HttpStatus.SC_GONE);
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, INV_BP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, INV_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
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
    @DeleteMapping(value = {DEPLOYMENTS_PATH + "/{deploymentId:.+}"}, produces = "application/json")
    public String deleteDeployment(@PathVariable("deploymentId") String deploymentId,
        HttpServletRequest request, @RequestParam(value = "tenant", required = true) String tenant,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = null;
        final String errStr = "Deleting deployment failed: " + deploymentId;
        StringBuffer status = new StringBuffer();
        try {
            ReadWriteLock lock = new ReentrantReadWriteLock();
            lock.readLock().lock();
            List<CloudifyDeployment> itemList = (List<CloudifyDeployment>) getCacheManager()
                .getObject("service-list" + ":" + tenant);
            lock.readLock().unlock();
            if (itemList != null) {
                Predicate<CloudifyDeployment> idFilter = p -> p.id.equals(deploymentId);
                itemList = itemList.stream().filter(idFilter).collect(Collectors.toList());
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
                    if (!isAuthorized()) {
                        response.setStatus(HttpStatus.SC_FORBIDDEN);
                        json =
                            objectMapper.writeValueAsString(new RestResponseError("UNAUTH_ERROR"));
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
        } catch (BadRequestException | ServerErrorException | DownstreamException
            | DeploymentNotFoundException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
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
    @PostMapping(value = {EXECUTIONS_PATH + "/{id}"}, produces = "application/json")
    public String cancelExecution(@RequestHeader HttpHeaders headers, @PathVariable("id") String id,
        @RequestBody Map<String, String> parameters, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        List<String> tenant = null;
        String errStr = "Cancelling execution failed: " + id;
        try {
            tenant = headers.get("tenant");
            result = cloudifyClient.cancelExecution(id, parameters, tenant.get(0));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_EXEC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_EXEC_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_CATEGORY);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errStr);
            result = new RestResponseError(errStr, t);
        } finally {
            postLogAudit(request);
        }
        if (result == null) {
            return null;
        } else {
            return objectMapper.writeValueAsString(result);
        }
    }

    private void preLogAudit(HttpServletRequest request) {
        Date begin = new Date();
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
        Date end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put(TARGET_ENTITY_KEY, "API Controller");
        MDC.put(TARGET_SERVICE_KEY, "API Controller");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        logger.info(EELFLoggerDelegate.auditLogger,
            request.getMethod() + " " + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + " " + request.getRequestURI());
    }
}
