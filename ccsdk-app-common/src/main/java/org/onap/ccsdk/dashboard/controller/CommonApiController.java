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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.onap.ccsdk.dashboard.domain.EcdComponent;
import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyTenant;
import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.RestResponseSuccess;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentInput;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResource;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResourceLinks;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponseLinks;
import org.onap.ccsdk.dashboard.model.inventory.Blueprint;
import org.onap.ccsdk.dashboard.model.inventory.BlueprintResponse;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeUploadRequest;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.DeploymentHandlerClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.ccsdk.dashboard.service.ControllerEndpointService;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/nb-api")
public class CommonApiController extends DashboardRestrictedBaseController {
    private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(CommonApiController.class);

    private static final String COMPONENTS_PATH = "components";
    private static final String DEPLOYMENTS_PATH = "deployments";
    private static final String SERVICE_TYPES_PATH = "blueprints";
    private static final String EXECUTIONS_PATH = "executions";
    private static final String DEP_TENANT_STATUS = "deployment-status";
    private static final String TENANTS_PATH = "tenants";

    @Autowired
    private ControllerEndpointService controllerEndpointService;

    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    DeploymentHandlerClient deploymentHandlerClient;

    @Autowired
    CloudifyClient cloudifyClient;

    /**
     * Enum for selecting an item type.
     */
    public enum InventoryDataItem {
        SERVICES, SERVICE_TYPES, SERVICES_GROUPBY;
    }

    private static Date begin, end;

    @RequestMapping(value = "/api-docs", method = RequestMethod.GET, produces = "application/json")
    public Resource apiDocs() {
        return new ClassPathResource("swagger.json");
    }

    @RequestMapping(value = { COMPONENTS_PATH }, method = RequestMethod.POST, produces = "application/json")
    public String insertComponent(HttpServletRequest request, @RequestBody EcdComponent newComponent) throws Exception {
        String outboundJson = null;
        controllerEndpointService.insertComponent(newComponent);
        RestResponseSuccess success = new RestResponseSuccess(
                "Inserted new component with name " + newComponent.getCname());
        outboundJson = objectMapper.writeValueAsString(success);
        return outboundJson;
    }

    @RequestMapping(value = { COMPONENTS_PATH }, method = RequestMethod.GET, produces = "application/json")
    public String getComponents(HttpServletRequest request) throws Exception {
        List<EcdComponent> result = controllerEndpointService.getComponents();
        return objectMapper.writeValueAsString(result);

    }

    /**
     * gets the tenants list
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = { TENANTS_PATH }, method = RequestMethod.GET, produces = "application/json")
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

    /**
     * Query status and tenant info for deployments
     * 
     */
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
        ECTransportModel result = null;
        List<CloudifyDeployedTenant> tenantList = new ArrayList<CloudifyDeployedTenant>();
        List<CloudifyExecution> cfyExecList = new ArrayList<CloudifyExecution>();
        try {
            List<CloudifyTenant> cldfyTen = cloudifyClient.getTenants().items;
            for (CloudifyTenant ct : (List<CloudifyTenant>) cldfyTen) {
                result = cloudifyClient.getTenantInfoFromDeploy(ct.name);
                tenantList.addAll(((CloudifyDeployedTenantList) result).items);
            }
            result = null;
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
            for (CloudifyDeployedTenant deplItem : currSrvcTenants) {
                CloudifyExecutionList execResults =
                    cloudifyClient.getExecutionsSummary(deplItem.id, deplItem.tenant_name);
                for (CloudifyExecution cfyExec : execResults.items) {
                    if (cfyExec.workflow_id.equalsIgnoreCase("install")) {
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
            logger.error(EELFLoggerDelegate.errorLogger, "getTenantStatusForService caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting deployments failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getTenantStatusForService caught exception");
            result = new RestResponseError("getTenantStatusForService failed", t);
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

    @RequestMapping(value = { SERVICE_TYPES_PATH }, method = RequestMethod.POST, produces = "application/json")
    public String createBlueprint(HttpServletRequest request, @RequestBody ServiceTypeUploadRequest serviceTypeUplReq)
            throws Exception {
        String json = null;
        try {
            Blueprint.parse(serviceTypeUplReq.getBlueprintTemplate());
            // InventoryClient inventoryClient = getInventoryClient();
            Collection<String> serviceIds = new ArrayList<String>();
            Collection<String> vnfTypes = new ArrayList<String>();
            Collection<String> serviceLocations = new ArrayList<String>();
            Optional<String> asdcServiceId = null;
            Optional<String> asdcResourceId = null;
            Optional<String> asdcServiceURL = null;

            ServiceTypeRequest invSrvcTypeReq = new ServiceTypeRequest(serviceTypeUplReq.owner,
                    serviceTypeUplReq.typeName, serviceTypeUplReq.typeVersion, serviceTypeUplReq.blueprintTemplate,
                    serviceTypeUplReq.application, serviceTypeUplReq.component, serviceIds, vnfTypes, serviceLocations,
                    asdcServiceId, asdcResourceId, asdcServiceURL);
            ServiceType response = inventoryClient.addServiceType(invSrvcTypeReq);
            // RestResponseSuccess success = new RestResponseSuccess("Uploaded new blueprint
            // with name " + serviceTypeUplReq.typeName);
            json = objectMapper.writeValueAsString(response);
        } catch (BlueprintParseException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating service type failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateServiceTypeBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("Invalid blueprint format.", e));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating service type failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateServiceTypeBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getResponseBodyAsString()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating service type failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateServiceTypeBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("updateServiceTypeBlueprint failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @RequestMapping(value = { SERVICE_TYPES_PATH }, method = RequestMethod.GET, produces = "application/json")
    public String getBlueprintsByPage(HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICE_TYPES, request.getParameter("name"),
                request.getParameter("_include"));
        postLogAudit(request);
        return json;
    }

    @RequestMapping(value = {
            SERVICE_TYPES_PATH + "/findByName" }, method = RequestMethod.GET, produces = "application/json")
    public String queryBlueprintFilter(HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICE_TYPES, request.getParameter("name"),
                request.getParameter("_include"));
        postLogAudit(request);
        return json;
    }

    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId}" }, method = RequestMethod.GET, produces = "application/json")
    public String getDeploymentsByPage(@PathVariable("deploymentId") String deploymentId, HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICES, deploymentId,
                request.getParameter("_include"));
        postLogAudit(request);
        return json;
    }

    @RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.GET, produces = "application/json")
    public String getAllDeploymentsByPage(HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICES, request.getParameter("deploymentId"),
                request.getParameter("_include"));
        postLogAudit(request);
        return json;
    }

    /**
     * Gets one page of the specified items. This method traps exceptions and
     * constructs an appropriate JSON block to report errors.
     * 
     * @param request Inbound request
     * @param option  Item type to get
     * @return JSON with one page of objects; or an error.
     */
    protected String getItemListForPageWrapper(HttpServletRequest request, InventoryDataItem option, String searchBy,
            String filters) {
        preLogAudit(request);

        String outboundJson = null;
        try {
            int pageNum = getRequestPageNumber(request);
            int pageSize = getRequestPageSize(request);
            outboundJson = getItemListForPage(option, pageNum, pageSize, searchBy, filters);
        } catch (Exception ex) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "ECOMP Inventory");
            MDC.put("TargetServiceName", "ECOMP Inventory");
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
        } finally {
            postLogAudit(request);
        }
        return outboundJson;
    }

    /**
     * Gets one page of objects and supporting information via the REST client. On
     * success, returns a PaginatedRestResponse object as String.
     * 
     * @param option   Specifies which item list type to get
     * @param pageNum  Page number of results
     * @param pageSize Number of items per browser page
     * @return JSON block as String, see above.
     * @throws Exception On any error; e.g., Network failure.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private String getItemListForPage(InventoryDataItem option, int pageNum, int pageSize, String searchBy,
            String filters) throws Exception {
        String outboundJson = "";
        List itemList = null;

        switch (option) {
            case SERVICES:
                itemList = inventoryClient.getServices().collect(Collectors.toList());
                if (searchBy != null) {
                    itemList = (List) itemList.stream()
                        .filter(s -> ((Service) s).contains(searchBy)).collect(Collectors.toList());
                }
                // Get the tenant names for all the deployments from Cloudify/API handler
                ECTransportModel result = null;
                List<CloudifyDeployedTenant> tenantList = new ArrayList<CloudifyDeployedTenant>();
                try {
                    List<CloudifyTenant> cldfyTen = cloudifyClient.getTenants().items;
                    for (CloudifyTenant ct : (List<CloudifyTenant>) cldfyTen) {
                        result = cloudifyClient.getTenantInfoFromDeploy(ct.name);
                        tenantList.addAll(((CloudifyDeployedTenantList) result).items);
                    }
                } catch (HttpStatusCodeException e) {
                    MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                    MDC.put("TargetEntity", "Cloudify Manager");
                    MDC.put("TargetServiceName", "Cloudify Manager");
                    MDC.put("ErrorCode", "300");
                    MDC.put("ErrorCategory", "ERROR");
                    MDC.put("ErrorDescription", "Getting deployments failed!");
                    logger.error(EELFLoggerDelegate.errorLogger,
                        "getTenantInfoFromDeploy caught exception");
                    //result = new RestResponseError(e.getResponseBodyAsString());
                } catch (Throwable t) {
                    MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                    MDC.put("TargetEntity", "Cloudify Manager");
                    MDC.put("TargetServiceName", "Cloudify Manager");
                    MDC.put("ErrorCode", "300");
                    MDC.put("ErrorCategory", "ERROR");
                    MDC.put("ErrorDescription", "Getting deployments failed!");
                    logger.error(EELFLoggerDelegate.errorLogger,
                        "getDeploymentById caught exception");
                    //result = new RestResponseError("getTenantInfoFromDeploy failed", t);
                } finally {

            }

            for (Service depl : (List<Service>) itemList) {
                for (CloudifyDeployedTenant deplTen : tenantList) {
                    if (depl.getDeploymentRef().equals(deplTen.id)) {
                        depl.setTenant(deplTen.tenant_name);
                        break;
                    }
                }
            }
            break;
        case SERVICE_TYPES:
            ServiceTypeQueryParams serviceQueryParams = null;
            serviceQueryParams = new ServiceTypeQueryParams.Builder().onlyLatest(false).build();

            itemList = inventoryClient.getServiceTypes(serviceQueryParams).collect(Collectors.toList());
            List<BlueprintResponse> filterList = new ArrayList<BlueprintResponse>();

            if (searchBy != null && searchBy.length() > 1) {
                itemList = (List) itemList.stream().filter(s -> ((ServiceType) s).contains(searchBy))
                        .collect(Collectors.toList());
            }
            if (filters != null && filters.length() > 0) {
                String filterArr[] = filters.split(",");
                for (ServiceType bp : (List<ServiceType>) itemList) {
                    BlueprintResponse bpOut = new BlueprintResponse();
                    for (String fltr : filterArr) {
                        switch (fltr) {
                        case "typeName":
                            bpOut.setTypeName(bp.getTypeName());
                            break;
                        case "typeId":
                            if (bp.getTypeId().isPresent()) {
                            bpOut.setTypeId(bp.getTypeId().get());
                            }
                            break;
                        case "typeVersion":
                            bpOut.setTypeVersion(bp.getTypeVersion());
                            break;
                        default:
                            break;
                        }
                    }
                    filterList.add(bpOut);
                }
                if (filterList.size() > 0) {
                    itemList.clear();
                    itemList.addAll(filterList);
                }
            }
            break;
        default:
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of items failed!");
            throw new Exception("getItemListForPage failed: unimplemented case: " + option.name());
        }
        // Shrink if needed
        final int totalItems = itemList.size();
        final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        if (totalItems > pageSize)
            itemList = getPageOfList(pageNum, pageSize, itemList);

        RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
        outboundJson = objectMapper.writeValueAsString(model);

        return outboundJson;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private String getBlueprintTypeId(String searchBy, Optional<Integer> version, String typeId) throws Exception {

        // InventoryClient inventoryClient = getInventoryClient();
        ServiceTypeQueryParams serviceQueryParams = null;

        if (version.isPresent()) {
            serviceQueryParams = new ServiceTypeQueryParams.Builder().typeName(searchBy).onlyLatest(false).build();
        } else {
            serviceQueryParams = new ServiceTypeQueryParams.Builder().typeName(searchBy).build();
        }

        List itemList = inventoryClient.getServiceTypes(serviceQueryParams).collect(Collectors.toList());

        if (version.isPresent()) {
            itemList = (List) itemList.stream().filter(s -> ((ServiceType) s).contains(version.get().toString()))
                    .collect(Collectors.toList());
        }
        Optional<String> bpId = Optional.of("");
        if (typeId != null && typeId.equals("typeId")) {
            ServiceType item = (ServiceType) ((List) itemList).get(0);
            bpId = item.getTypeId();
        }
        return bpId.get();
    }

    /**
     * Query the installed helm package revisions from cloudify
     * 
     * @param deploymentId
     * @param tenant
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId}/revisions" }, method = RequestMethod.GET, produces = "application/json")
    public String getDeploymentRevisions(@PathVariable("deploymentId") String deploymentId,
            @RequestParam(value = "tenant") String tenant, HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("tenant name is missing");
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
        } catch (Throwable t) {
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

    /**
     * Query inputs used to create a deployment
     * 
     * @param deploymentId
     * @param tenant
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId}/inputs" }, method = RequestMethod.GET, produces = "application/json")
    public String getDeploymentInputs(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("tenant name is missing");
            }
            result = cloudifyClient.getDeploymentInputs(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
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

    /**
     * Create an upgrade/rollback workflow execution for a deployment.
     * 
     * @param request   HttpServletRequest
     * @param execution Execution model
     * @return Information about the execution
     * @throws Exception on serialization failure
     */
    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId}" }, method = RequestMethod.PUT, produces = "application/json")
    public String modifyDeployment(@PathVariable("deploymentId") String deploymentId, HttpServletRequest request,
            InputStream upgParams) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            String nodeInstId = "";
            Map<String, Object> parameters = objectMapper.readValue(upgParams,
                    new TypeReference<Map<String, Object>>() {
                    });
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
            CloudifyExecutionRequest execution = new CloudifyExecutionRequest(deploymentId,
                workflow, false, false, tenant, parameters);
            result = cloudifyClient.startExecution(execution);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Updating deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "updateDeployment caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
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

    @RequestMapping(value = {
            SERVICE_TYPES_PATH + "/{typeid}" + "/services" }, method = RequestMethod.GET, produces = "application/json")
    public String getServicesForType(HttpServletRequest request, @PathVariable("typeid") String typeId)
            throws Exception {
        preLogAudit(request);
        List<ServiceTypeServiceMap> result = new ArrayList<ServiceTypeServiceMap>();
        // InventoryClient inventoryClient = getInventoryClient();
        ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
        ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
        ServiceTypeServiceMap srvcMap = new ServiceTypeServiceMap(typeId, srvcRefs);
        result.add(srvcMap);
        return objectMapper.writeValueAsString(result);
    }

    @RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.POST, produces = "application/json")
    public String createDeployment(HttpServletRequest request, @RequestBody DeploymentInput deploymentRequestObject)
            throws Exception {
        preLogAudit(request);
        String json = null;
        StringBuffer status = new StringBuffer();
        // Optional<String> bpId = Optional.empty();
        Optional<Integer> bpVersion = null;
        String srvcTypeId = null;
        String bpName = deploymentRequestObject.getBlueprintName();
        String cName = deploymentRequestObject.getComponent();
        String tag = deploymentRequestObject.getTag();
        String depName = cName + "_" + tag;

        if (deploymentRequestObject.getBlueprintVersion().isPresent()) {
            bpVersion = deploymentRequestObject.getBlueprintVersion();
        }
        if (deploymentRequestObject.getBlueprintId().isPresent()) {
            srvcTypeId = deploymentRequestObject.getBlueprintId().get();
            // srvcTypeId = bpId.get();
        }
        if (srvcTypeId == null) {
            // get the serviceTypeId from inventory using the blueprint name
            try {
                srvcTypeId = getBlueprintTypeId(bpName, bpVersion, "typeId");
            } catch (Exception ex) {
                MDC.put(SystemProperties.STATUS_CODE, "ERROR");
                MDC.put("TargetEntity", "ECOMP Inventory");
                MDC.put("TargetServiceName", "ECOMP Inventory");
                MDC.put("ErrorCode", "300");
                MDC.put("ErrorCategory", "ERROR");
                MDC.put("ErrorDescription", "Getting blueprint ID failed!");
                logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception");
                RestResponseError result = null;
                if (ex instanceof HttpStatusCodeException)
                    result = new RestResponseError(((HttpStatusCodeException) ex).getResponseBodyAsString());
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
            DeploymentResponse resp =
                deploymentHandlerClient.putDeployment(depName, deploymentRequestObject.getTenant(),
                    new DeploymentRequest(srvcTypeId, deploymentRequestObject.getInputs()));
            DeploymentResponseLinks deplLinks = resp.getLinks();
            String deplStatus = deplLinks.getStatus();
            if (!deplStatus.contains("cfy_tenant")) {
                deplStatus = deplStatus + "?cfy_tenant_name=" + deploymentRequestObject.getTenant();
            }
            String self = request.getRequestURL().append("/").append(depName).toString();
            status.append(self).append("/executions?tenant=").append(deploymentRequestObject.getTenant());
            DeploymentResource deplRsrc = new DeploymentResource(depName,
                    new DeploymentResourceLinks(self, deplStatus, status.toString()));
            JSONObject statObj = new JSONObject(deplRsrc);
            json = statObj.toString();
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServiceAlreadyExistsException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("putDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    @RequestMapping(value = {
            DEPLOYMENTS_PATH + "/{deploymentId}/update" }, method = RequestMethod.PUT, produces = "application/json")
    public String updateDeployment(@PathVariable("deploymentId") String deploymentId, HttpServletRequest request,
            @RequestBody DeploymentInput deploymentRequestObject) throws Exception {
        preLogAudit(request);
        String json = null;
        String srvcTypeId = "";
        Optional<Integer> bpVersion = null;
        String bpName = deploymentRequestObject.getBlueprintName();
        if (deploymentRequestObject.getBlueprintVersion().isPresent()) {
            bpVersion = deploymentRequestObject.getBlueprintVersion();
        }
        // get the serviceTypeId from inventory using the blueprint name
        try {
            srvcTypeId = getBlueprintTypeId(bpName, bpVersion, "typeId");
        } catch (Exception ex) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "ECOMP Inventory");
            MDC.put("TargetServiceName", "ECOMP Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting blueprint ID failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception");
            RestResponseError result = null;
            if (ex instanceof HttpStatusCodeException)
                result = new RestResponseError(((HttpStatusCodeException) ex).getResponseBodyAsString());
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
        try {
            json = objectMapper.writeValueAsString(deploymentHandlerClient.updateDeployment(
                deploymentId, deploymentRequestObject.getTenant(),
                new DeploymentRequest(srvcTypeId, deploymentRequestObject.getInputs())));
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServiceAlreadyExistsException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (JsonProcessingException jpe) {
            // Should never, ever happen
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = "{ \"error\" : \"" + jpe.toString() + "\"}";
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deployment failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "putDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("putDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Gets the executions for one deployment.
     * 
     * 
     * @param deployment_id Deployment ID (query parameter)
     * @param request       HttpServletRequest
     * @return CloudifyExecutionList
     * @throws Exception on serialization failure
     */
    @RequestMapping(value = { DEPLOYMENTS_PATH + "/{deploymentId}" + "/"
            + EXECUTIONS_PATH }, method = RequestMethod.GET, produces = "application/json")
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
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Throwable t) {
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

    /**
     * Deletes the specified blueprint.
     *
     * @param id       Blueprint ID
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return status code on success; error on failure.
     * @throws Exception On serialization failure
     */
    @RequestMapping(value = {
            SERVICE_TYPES_PATH + "/{typeid}" }, method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public String deleteBlueprint(@PathVariable("typeid") String typeId, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = "{\"202\": \"OK\"}";
        try {
            // InventoryClient inventoryClient = getInventoryClient();
            ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
            ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
            if (srvcRefs != null && srvcRefs.totalCount > 0) {
                throw new Exception("Services exist for the service type template, delete not permitted");
            }
            inventoryClient.deleteServiceType(typeId);
        } catch (ServiceTypeNotFoundException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServiceTypeAlreadyDeactivatedException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("deleteBlueprint failed", t));
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
            deploymentHandlerClient.deleteDeployment(deploymentId, tenant);
            String self = request.getRequestURL().toString().split("\\?")[0];
            status.append(self).append("/executions?tenant=").append(tenant);
            DeploymentResource deplRsrc = new DeploymentResource(deploymentId,
                    new DeploymentResourceLinks(self, "", status.toString()));
            JSONObject statObj = new JSONObject(deplRsrc);
            json = statObj.toString();
        } catch (BadRequestException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (ServerErrorException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DownstreamException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (DeploymentNotFoundException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Deployment Handler");
            MDC.put("TargetServiceName", "Deployment Handler");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteDeployment caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError("deleteDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
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
        } catch (Throwable t) {
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

    private void preLogAudit(HttpServletRequest request) {
        begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
    }

    private void postLogAudit(HttpServletRequest request) {
        end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put("TargetEntity", "Deployment Handler");
        MDC.put("TargetServiceName", "Deployment Handler");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        //MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger, request.getMethod() + request.getRequestURI());
    }
}
