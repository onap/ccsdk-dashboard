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
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 *******************************************************************************/

package org.onap.ccsdk.dashboard.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.inventory.Blueprint;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.ccsdk.dashboard.util.DashboardProperties;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Controller for Inventory features: services, service types, services groupby.
 * Methods serve Ajax requests made by Angular scripts on pages that show
 * content.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController extends DashboardRestrictedBaseController {

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(InventoryController.class);

    @Autowired
    InventoryClient inventoryClient;

    /**
     * Enum for selecting an item type.
     */
    public enum InventoryDataItem {
        SERVICES, SERVICE_TYPES, SERVICES_GROUPBY;
    }

    private static Date begin, end;
    private static final String SERVICES_PATH = "dcae-services";
    private static final String SERVICE_TYPES_PATH = "dcae-service-types";
    private static final String VIEW_SERVICE_TYPE_BLUEPRINT_PATH = "dcae-service-type-blueprint";
    private static final String DEP_IDS_FOR_TYPE = "dcae-services/typeIds";

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
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String getItemListForPage(HttpServletRequest request, InventoryDataItem option,
        int pageNum, int pageSize, String sortBy, String searchBy) throws Exception {
        List itemList = null;
        switch (option) {
            case SERVICES:
                itemList = inventoryClient.getServices().collect(Collectors.toList());
                if (searchBy != null) {
                    itemList = (List) itemList.stream()
                        .filter(s -> ((Service) s).contains(searchBy)).collect(Collectors.toList());
                }
                for (Service bp : (List<Service>) itemList) {
                    bp.setCanDeploy(Optional.of(true));
                }
                if (sortBy != null) {
                    if (sortBy.equals("deploymentRef")) {
                        Collections.sort(itemList, serviceDeploymentRefComparator);
                    } else if (sortBy.equals("serviceId")) {
                        Collections.sort(itemList, serviceIdComparator);
                    } else if (sortBy.equals("created")) {
                        Collections.sort(itemList, serviceCreatedComparator);
                    } else if (sortBy.equals("modified")) {
                        Collections.sort(itemList, serviceModifiedComparator);
                    }
                }
                break;
            case SERVICE_TYPES:
                itemList = inventoryClient.getServiceTypes().collect(Collectors.toList());
                if (searchBy != null) {
                    itemList =
                        (List) itemList.stream().filter(s -> ((ServiceType) s).contains(searchBy))
                            .collect(Collectors.toList());
                }
                for (ServiceType bp : (List<ServiceType>) itemList) {
                    bp.setCanDeploy(Optional.of(true));
                }
                if (sortBy != null) {
                    if (sortBy.equals("owner")) {
                        Collections.sort(itemList, serviceTypeOwnerComparator);
                    } else if (sortBy.equals("typeId")) {
                        Collections.sort(itemList, serviceTypeIdComparator);
                    } else if (sortBy.equals("typeName")) {
                        Collections.sort(itemList, serviceTypeNameComparator);
                    } else if (sortBy.equals("typeVersion")) {
                        Collections.sort(itemList, serviceTypeVersionComparator);
                    } else if (sortBy.equals("created")) {
                        Collections.sort(itemList, serviceTypeCreatedComparator);
                    } else if (sortBy.equals("application")) {
                        Collections.sort(itemList, serviceTypeApplComparator);
                    } else if (sortBy.equals("component")) {
                        Collections.sort(itemList, serviceTypeCompComparator);
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
     * version with user role auth Gets one page of objects and supporting
     * information via the REST client. On success, returns a PaginatedRestResponse
     * object as String.
     * 
     * @param option Specifies which item list type to get
     * @param pageNum Page number of results
     * @param pageSize Number of items per browser page
     * @return JSON block as String, see above.
     * @throws Exception On any error; e.g., Network failure.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String getItemListForPageAuth(HttpServletRequest request, InventoryDataItem option,
        int pageNum, int pageSize, String sortBy, String searchBy) throws Exception {
        HttpSession session = AppUtils.getSession(request);
        HashMap<String, Boolean> comp_deploy_tab =
            (HashMap<String, Boolean>) session.getAttribute("comp_access");
        String roleLevel = (String) session.getAttribute("role_level");

        if (roleLevel == null) {
            roleLevel = "dev";
        }
        if (comp_deploy_tab == null) {
            comp_deploy_tab = new HashMap<String, Boolean>();
        }

        Set<String> userApps = (Set<String>) session.getAttribute("authComponents");
        if (userApps == null) {
            userApps = new TreeSet<String>();
        }

        List itemList = null;
        List<ServiceType> filterList = new ArrayList<ServiceType>();
        List<Service> authDepList = new ArrayList<Service>();
        switch (option) {
            case SERVICES:
                itemList = inventoryClient.getServices().collect(Collectors.toList());
                if (roleLevel.equals("app")) {
                    for (String userRole : userApps) {
                        logger.debug(">>>> check component type from deployment: " + userRole);
                        for (Service cont : (List<Service>) itemList) {
                            String deplRef = cont.getDeploymentRef().toLowerCase();
                            logger.debug(">>>> container deployment name: " + deplRef);
                            if (deplRef.contains(userRole)) {
                                logger.debug(">>>> adding deployment item to filtered subset");
                                authDepList.add(cont);
                            }
                        }
                    }
                }

                if (searchBy != null) {
                    if (!roleLevel.equals("app")) {
                        itemList =
                            (List) itemList.stream().filter(s -> ((Service) s).contains(searchBy))
                                .collect(Collectors.toList());
                    } else {
                        if (!authDepList.isEmpty()) {
                            authDepList = (List) authDepList.stream()
                                .filter(s -> ((Service) s).contains(searchBy))
                                .collect(Collectors.toList());
                        }
                    }
                }
                if (roleLevel.equals("app")) {
                    logger.debug(">>>> update response with authorized content");
                    itemList.clear();
                    itemList.addAll(authDepList);
                }

                // check for authorization to perform delete deployed blueprints

                if (!roleLevel.equals("ops")) {
                    for (Service bp : (List<Service>) itemList) {
                        String deplRef = bp.getDeploymentRef().split("_")[0].toLowerCase();
                        logger.debug(">>>> deployment reference: " + deplRef);
                        if (comp_deploy_tab.containsKey(deplRef)) {
                            boolean enableDeploy = comp_deploy_tab.get(deplRef);
                            logger.debug(">>>> enable deploy button: " + enableDeploy);
                            bp.setCanDeploy(Optional.of(enableDeploy));
                        } else {
                            bp.setCanDeploy(Optional.of(false));
                        }
                    }
                } else {
                    for (Service bp : (List<Service>) itemList) {
                        bp.setCanDeploy(Optional.of(true));
                    }
                }

                if (sortBy != null) {
                    if (sortBy.equals("deploymentRef")) {
                        Collections.sort(itemList, serviceDeploymentRefComparator);
                    } else if (sortBy.equals("serviceId")) {
                        Collections.sort(itemList, serviceIdComparator);
                    } else if (sortBy.equals("created")) {
                        Collections.sort(itemList, serviceCreatedComparator);
                    } else if (sortBy.equals("modified")) {
                        Collections.sort(itemList, serviceModifiedComparator);
                    }
                }
                break;
            case SERVICE_TYPES:
                ServiceTypeQueryParams serviceQueryParams =
                    new ServiceTypeQueryParams.Builder().onlyLatest(false).build();
                itemList = inventoryClient.getServiceTypes(serviceQueryParams)
                    .collect(Collectors.toList());
                if (roleLevel.equals("app")) {
                    for (String userApp : userApps) {
                        logger.debug(">>>> check component type from BP: " + userApp);
                        for (ServiceType bp : (List<ServiceType>) itemList) {
                            String bpComp = bp.getComponent();
                            String bpOwner = bp.getOwner(); // for backward compatibility
                            logger.debug(">>>> BP component name: " + bpComp);
                            if ((bpComp != null && bpComp.equalsIgnoreCase(userApp))
                                || bpOwner.contains(userApp)) {
                                logger.debug(">>>> adding item to filtered subset");
                                filterList.add(bp);
                            }
                        }
                    }
                }
                if (searchBy != null) {
                    if (!roleLevel.equals("app")) {
                        itemList = (List) itemList.stream()
                            .filter(s -> ((ServiceType) s).contains(searchBy))
                            .collect(Collectors.toList());
                    } else {
                        if (!filterList.isEmpty()) {
                            filterList = (List) filterList.stream()
                                .filter(s -> ((ServiceType) s).contains(searchBy))
                                .collect(Collectors.toList());
                        }
                    }
                }
                if (roleLevel.equals("app")) {
                    logger.debug(">>>> update response with authorized content");
                    itemList.clear();
                    itemList.addAll(filterList);
                }

                // check for authorization to perform update/delete/deploy blueprints
                if (!roleLevel.equals("ops")) {
                    for (ServiceType bp : (List<ServiceType>) itemList) {
                        String bpComp = bp.getComponent();
                        if (bpComp != null && bpComp.length() > 0) {
                            bpComp = bpComp.toLowerCase();
                        } else {
                            String bpOwner = bp.getOwner(); // for backward compatibility
                            if (bpOwner != null && bpOwner.contains(":")) {
                                bpComp = bp.getOwner().split(":")[0].toLowerCase();
                            }
                        }
                        logger.debug(">>>> BP component name: " + bpComp);
                        if (comp_deploy_tab.containsKey(bpComp)) {
                            boolean enableDeploy = comp_deploy_tab.get(bpComp);
                            logger.debug(">>>> enable deploy button: " + enableDeploy);
                            bp.setCanDeploy(Optional.of(enableDeploy));
                        } else {
                            bp.setCanDeploy(Optional.of(false));
                        }
                    }
                } else {
                    for (ServiceType bp : (List<ServiceType>) itemList) {
                        bp.setCanDeploy(Optional.of(true));
                    }
                }

                if (sortBy != null) {
                    if (sortBy.equals("owner")) {
                        Collections.sort(itemList, serviceTypeOwnerComparator);
                    } else if (sortBy.equals("typeId")) {
                        Collections.sort(itemList, serviceTypeIdComparator);
                    } else if (sortBy.equals("typeName")) {
                        Collections.sort(itemList, serviceTypeNameComparator);
                    } else if (sortBy.equals("typeVersion")) {
                        Collections.sort(itemList, serviceTypeVersionComparator);
                    } else if (sortBy.equals("created")) {
                        Collections.sort(itemList, serviceTypeCreatedComparator);
                    } else if (sortBy.equals("application")) {
                        Collections.sort(itemList, serviceTypeApplComparator);
                    } else if (sortBy.equals("component")) {
                        Collections.sort(itemList, serviceTypeCompComparator);
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
     * @param option Item type to get
     * @return JSON with one page of objects; or an error.
     */
    protected String getItemListForPageWrapper(HttpServletRequest request, InventoryDataItem option,
        String sortBy, String searchBy) {
        preLogAudit(request);
        String outboundJson = null;
        try {
            int pageNum = getRequestPageNumber(request);
            int pageSize = getRequestPageSize(request);
            String appEnv = "os";
            appEnv =
                DashboardProperties.getPropertyDef(DashboardProperties.CONTROLLER_TYPE, "auth");
            if (appEnv.equals("os")) {
                outboundJson =
                    getItemListForPage(request, option, pageNum, pageSize, sortBy, searchBy);
            } else {
                outboundJson =
                    getItemListForPageAuth(request, option, pageNum, pageSize, sortBy, searchBy);
            }
        } catch (Exception ex) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting page of items failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getItemListForPageWrapper caught exception");
            RestResponseError result = null;
            if (ex instanceof HttpStatusCodeException)
                result =
                    new RestResponseError(((HttpStatusCodeException) ex).getResponseBodyAsString());
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
     * Supports sorting service types by owner
     */
    private static Comparator<ServiceType> serviceTypeOwnerComparator =
        new Comparator<ServiceType>() {
            @Override
            public int compare(ServiceType o1, ServiceType o2) {
                return o1.getOwner().compareToIgnoreCase(o2.getOwner());
            }
        };

    /**
     * Supports sorting service types by application
     */
    private static Comparator<ServiceType> serviceTypeApplComparator =
        new Comparator<ServiceType>() {
            @Override
            public int compare(ServiceType o1, ServiceType o2) {
                return o1.getApplication().compareToIgnoreCase(o2.getApplication());
            }
        };

    /**
     * Supports sorting service types by component
     */
    private static Comparator<ServiceType> serviceTypeCompComparator =
        new Comparator<ServiceType>() {
            @Override
            public int compare(ServiceType o1, ServiceType o2) {
                return o1.getComponent().compareToIgnoreCase(o2.getComponent());
            }
        };

    /**
     * Supports sorting service types by type id
     */
    private static Comparator<ServiceType> serviceTypeIdComparator = new Comparator<ServiceType>() {
        @Override
        public int compare(ServiceType o1, ServiceType o2) {
            return o1.getTypeId().get().compareToIgnoreCase(o2.getTypeId().get());
        }
    };

    /**
     * Supports sorting service types by type name
     */
    private static Comparator<ServiceType> serviceTypeNameComparator =
        new Comparator<ServiceType>() {
            @Override
            public int compare(ServiceType o1, ServiceType o2) {
                return o1.getTypeName().compareToIgnoreCase(o2.getTypeName());
            }
        };

    /**
     * Supports sorting service types by type version
     */
    private static Comparator<ServiceType> serviceTypeVersionComparator =
        new Comparator<ServiceType>() {
            @Override
            public int compare(ServiceType o1, ServiceType o2) {
                return o1.getTypeVersion().compareTo(o2.getTypeVersion());
            }
        };

    /**
     * Supports sorting service types by created date
     */
    private static Comparator<ServiceType> serviceTypeCreatedComparator =
        new Comparator<ServiceType>() {
            @Override
            public int compare(ServiceType o1, ServiceType o2) {
                return o1.getCreated().get().compareToIgnoreCase(o2.getCreated().get());
            }
        };

    /**
     * Supports sorting services by deploymentRef
     */
    private static Comparator<Service> serviceDeploymentRefComparator = new Comparator<Service>() {
        @Override
        public int compare(Service o1, Service o2) {
            return o1.getDeploymentRef().compareToIgnoreCase(o2.getDeploymentRef());
        }
    };

    /**
     * Supports sorting services by service id
     */
    private static Comparator<Service> serviceIdComparator = new Comparator<Service>() {
        @Override
        public int compare(Service o1, Service o2) {
            return o1.getServiceId().compareToIgnoreCase(o2.getServiceId());
        }
    };

    /**
     * Supports sorting services by created date
     */
    private static Comparator<Service> serviceCreatedComparator = new Comparator<Service>() {
        @Override
        public int compare(Service o1, Service o2) {
            return o1.getCreated().compareToIgnoreCase(o2.getCreated());
        }
    };

    /**
     * Supports sorting services by created date
     */
    private static Comparator<Service> serviceModifiedComparator = new Comparator<Service>() {
        @Override
        public int compare(Service o1, Service o2) {
            return o1.getModified().compareToIgnoreCase(o2.getModified());
        }
    };

    /**
     * Serves one page of service types
     * 
     * @param request HttpServletRequest
     * @return List of ServiceTypes objects
     */
    @RequestMapping(
        value = {SERVICE_TYPES_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    @ResponseBody
    public String getServiceTypesByPage(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, InventoryDataItem.SERVICE_TYPES,
            request.getParameter("sortBy"), request.getParameter("searchBy"));
        postLogAudit(request);
        return json;
    }

    /**
     * Query Service objects matching a service type ID
     * 
     */
    @RequestMapping(
        value = {DEP_IDS_FOR_TYPE},
        method = RequestMethod.POST,
        produces = "application/json")
    public String getServicesForType(HttpServletRequest request, @RequestBody String[] typeList)
        throws Exception {
        preLogAudit(request);
        List<ServiceTypeServiceMap> result = new ArrayList<ServiceTypeServiceMap>();
        for (String typeId : typeList) {
            ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
            ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
            ServiceTypeServiceMap srvcMap = new ServiceTypeServiceMap(typeId, srvcRefs);
            result.add(srvcMap);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Serves one page of services
     *
     * @param request HttpServletRequest
     *
     * @return List of Service objects
     */
    @RequestMapping(
        value = {SERVICES_PATH},
        method = RequestMethod.GET,
        produces = "application/json")
    @ResponseBody
    public String getServicesByPage(HttpServletRequest request) {
        // preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICES,
            request.getParameter("sortBy"), request.getParameter("searchBy"));
        postLogAudit(request);
        return json;
    }

    /**
     * Gets the specified blueprint content for viewing.
     * 
     * @param id Blueprint ID
     * @param request HttpServletRequest
     * @return Blueprint as YAML; or error.
     * @throws Exception on serialization error
     * 
     */
    @RequestMapping(
        value = {VIEW_SERVICE_TYPE_BLUEPRINT_PATH + "/{typeid}"},
        method = RequestMethod.GET,
        produces = "application/yaml")
    @ResponseBody
    public String viewServiceTypeBlueprintContentById(@PathVariable("typeid") String typeId,
        HttpServletRequest request) throws Exception {
        preLogAudit(request);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(inventoryClient.getServiceType(typeId).get());
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Viewing service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "viewServiceTypeBlueprintContentById caught exception");
            json =
                objectMapper.writeValueAsString(new RestResponseError(e.getResponseBodyAsString()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Viewing service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "viewServiceTypeBlueprintContentById caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("getBlueprintContentById failed", t));
        } finally {
            postLogAudit(request);
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
    @RequestMapping(
        value = {SERVICE_TYPES_PATH + "/{typeid}"},
        method = RequestMethod.DELETE,
        produces = "application/json")
    @ResponseBody
    public String deleteServiceType(@PathVariable("typeid") String typeid,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = "{\"202\": \"OK\"}";
        try {
            inventoryClient.deleteServiceType(typeid);
        } catch (ServiceTypeNotFoundException | ServiceTypeAlreadyDeactivatedException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeid + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteServiceType caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeid + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteServiceType caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("deleteDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Deletes the specified service i.e. deployment from inventory
     *
     * @param id Service ID
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return status code on success; error on failure.
     * @throws Exception On serialization failure
     */
    @RequestMapping(
        value = {SERVICES_PATH + "/{serviceId}"},
        method = RequestMethod.DELETE,
        produces = "application/json")
    @ResponseBody
    public String deleteService(@PathVariable("serviceId") String serviceId,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = "{\"202\": \"OK\"}";
        try {
            inventoryClient.deleteService(serviceId);
        } catch (ServiceNotFoundException | ServiceAlreadyDeactivatedException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service " + serviceId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteServiceType caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service " + serviceId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteServiceType caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("deleteDeployment failed", t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * Processes request to update a blueprint currently existing in DCAE Inventory.
     *
     * @param request HttpServletRequest
     * @param blueprint Cloudify blueprint
     * @return Blueprint as uploaded; or error.
     * @throws Exception on serialization error
     */
    @RequestMapping(
        value = {SERVICE_TYPES_PATH + "/update"},
        method = RequestMethod.POST,
        produces = "application/json")
    @ResponseBody
    public String updateServiceTypeBlueprint(HttpServletRequest request,
        @RequestBody ServiceType serviceType) throws Exception {
        preLogAudit(request);
        String json = "{\"201\": \"OK\"}";
        try {
            // Verify that the Service Type can be parsed for inputs.
            Blueprint.parse(serviceType.getBlueprintTemplate());
            // InventoryClient inventoryClient = getInventoryClient(request);
            inventoryClient.addServiceType(serviceType);
        } catch (BlueprintParseException e) {
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
        } catch (Exception t) {
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

    /**
     * Processes request to update a blueprint currently existing in DCAE Inventory.
     *
     * @param request HttpServletRequest
     * @param blueprint Cloudify blueprint
     * @return Blueprint as uploaded; or error.
     * @throws Exception on serialization error
     */
    @RequestMapping(
        value = {SERVICE_TYPES_PATH + "/upload"},
        method = RequestMethod.POST,
        produces = "application/json")
    @ResponseBody
    public String uploadServiceTypeBlueprint(HttpServletRequest request,
        @RequestBody ServiceTypeRequest serviceTypeRequest) throws Exception {
        preLogAudit(request);
        String json = "{\"201\": \"OK\"}";
        try {
            Blueprint.parse(serviceTypeRequest.getBlueprintTemplate());
            // InventoryClient inventoryClient = getInventoryClient(request);
            inventoryClient.addServiceType(serviceTypeRequest);
        } catch (BlueprintParseException e) {
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
        } catch (Exception t) {
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
        MDC.put("TargetEntity", "DCAE Inventory");
        MDC.put("TargetServiceName", "DCAE Inventory");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.MDC_TIMER, Long.toString((end.getTime() - begin.getTime())));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + request.getRequestURI());
    }
}
