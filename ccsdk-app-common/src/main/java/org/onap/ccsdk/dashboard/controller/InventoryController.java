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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.onap.ccsdk.dashboard.exceptions.inventory.BlueprintParseException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeAlreadyDeactivatedException;
import org.onap.ccsdk.dashboard.exceptions.inventory.ServiceTypeNotFoundException;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.ServiceRefCfyList;
import org.onap.ccsdk.dashboard.model.inventory.Blueprint;
import org.onap.ccsdk.dashboard.model.inventory.BlueprintResponse;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceType;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeRequest;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    CloudifyClient cloudifyClient;
    @Autowired
    ConsulClient consulClient;

    /**
     * For caching data
     */
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
        SERVICE_TYPES, SERVICE_TYPE_NAME, OWNER, SERVICE_TYPE_ID;
    }

    private static final String OWNERS = "owners";
    private static final String SERVICE_TYPES_PATH = "dcae-service-types";
    private static final String SERVICE_TYPE_NAME = "service-type-list";
    private static final String VIEW_SERVICE_TYPE_BLUEPRINT_PATH = "dcae-service-type-blueprint";
    private static final String DEP_IDS_FOR_TYPE = "dcae-services/typeIds";
    private static final String SERVICE_TYPE_ID = "service-type-id-list";

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
        HashMap<String, Boolean> compDeployTab =
            (HashMap<String, Boolean>) session.getAttribute("comp_access");
        String roleLevel = (String) session.getAttribute("role_level");
        String roleAuth = (String) session.getAttribute("auth_role");
        String user = UserUtils.getUserSession(request).getLoginId();

        if (roleLevel == null) {
            roleLevel = "dev";
        }
        if (compDeployTab == null) {
            compDeployTab = new HashMap<String, Boolean>();
        }
        if (roleAuth == null) {
            roleAuth = "READ";
        }
        Set<String> userApps = (Set<String>) session.getAttribute("authComponents");
        if (userApps == null) {
            userApps = new TreeSet<String>();
        }
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List itemList = null;
        List bpItemList = null;
        int totalItems = 0;

        lock.readLock().lock();
        List<ServiceTypeSummary> bpList =
            (List<ServiceTypeSummary>) getCacheManager().getObject(SERVICE_TYPES_PATH);
        lock.readLock().unlock();
        if (bpList == null) {
            bpList = inventoryClient.getServiceTypes().collect(Collectors.toList());
        }

        String svcRefFilterStr = "";
        String appFilterStr = "";
        String compFilterStr = "";
        String ownerFilterStr = "";
        String containsFilterStr = "";
        // apply user search filters
        if (searchBy != null && !searchBy.isEmpty()) {
            // parse the search filters string
            // look for service name patterns
            List<String> searchFilters = new ArrayList<String>(Arrays.asList(searchBy.split(";")));
            if (searchFilters.stream().anyMatch(s -> s.startsWith("contains"))) {
                List<String> containsList = searchFilters.stream()
                    .filter(s -> s.startsWith("contains")).collect(Collectors.toList());
                containsFilterStr = containsList.get(0).split(":")[1];
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("serviceRef"))) {
                List<String> svcRefsList = searchFilters.stream()
                    .filter(s -> s.startsWith("serviceRef")).collect(Collectors.toList());
                svcRefFilterStr = svcRefsList.get(0).split(":")[1];
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("app"))) {
                List<String> appsList = searchFilters.stream().filter(s -> s.startsWith("app"))
                    .collect(Collectors.toList());
                appFilterStr = appsList.get(0).split(":")[1];
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("comp"))) {
                List<String> compList = searchFilters.stream().filter(s -> s.startsWith("comp"))
                    .collect(Collectors.toList());
                compFilterStr = compList.get(0).split(":")[1];
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("owner"))) {
                List<String> ownerList = searchFilters.stream().filter(s -> s.startsWith("owner"))
                    .collect(Collectors.toList());
                ownerFilterStr = ownerList.get(0).split(":")[1];
            }
            if (!ownerFilterStr.isEmpty()) {
                List<ServiceTypeSummary> ownerBpList = new ArrayList<ServiceTypeSummary>();
                lock.readLock().lock();
                Map<String, List<ServiceTypeSummary>> bpPerOwner =
                    (Map<String, List<ServiceTypeSummary>>) getCacheManager()
                        .getObject("owner_bp_map");
                lock.readLock().unlock();

                List<String> ownerFilterList =
                    new ArrayList<String>(Arrays.asList(ownerFilterStr.split(",")));

                if (ownerFilterList.size() == 1 && ownerFilterList.get(0).equals("undefined")) {
                    ownerFilterList.clear();
                    ownerFilterList.add(user);
                }

                if (bpPerOwner != null) {
                    Stream<Map.Entry<String, List<ServiceTypeSummary>>> bpOwnerEntriesStream =
                        bpPerOwner.entrySet().stream();

                    Set<Map.Entry<String, List<ServiceTypeSummary>>> bpOwnerSet =
                        bpOwnerEntriesStream
                            .filter(m -> ownerFilterList.stream()
                                .anyMatch(ownFilter -> m.getKey().equalsIgnoreCase(ownFilter)))
                            .collect(Collectors.toSet());
                    bpOwnerSet.stream().forEach(e -> ownerBpList.addAll(e.getValue()));
                    bpItemList = ownerBpList;
                }
                if (bpItemList == null) {
                    bpItemList = new ArrayList<ServiceTypeSummary>();
                    bpItemList.addAll(bpList);
                }
            }
        }
        if (bpItemList == null) {
            bpItemList = new ArrayList<ServiceTypeSummary>();
            bpItemList.addAll(bpList);
        }
        // apply role based filtering
        if (roleLevel.equals("app")) {
            @SuppressWarnings("unchecked")
            List<String> myApps = new ArrayList(userApps);
            bpItemList = (List<ServiceTypeSummary>) bpItemList.stream()
                .filter(s -> myApps.stream()
                    .anyMatch(appFilter -> (((ServiceTypeSummary) s).getComponent() != null
                        && ((ServiceTypeSummary) s).getComponent().equalsIgnoreCase(appFilter))))
                .collect(Collectors.<ServiceTypeSummary>toList());
        } else if (roleLevel.equals("app_dev")) {
            Predicate<ServiceTypeSummary> appFilter =
                p -> p.getComponent() != null && !p.getComponent().equalsIgnoreCase("dcae")
                    && !p.getComponent().equalsIgnoreCase("d2a");
            bpItemList = (List<ServiceTypeSummary>) bpItemList.stream().filter(appFilter)
                .collect(Collectors.toList());
        }

        switch (option) {
            case OWNER:
                Set<String> ownersList = (Set) bpItemList.stream()
                    .map(x -> ((ServiceTypeSummary) x).getOwner()).collect(Collectors.toSet());
                return objectMapper.writeValueAsString(ownersList);
            case SERVICE_TYPE_ID:
                itemList = bpItemList;
                totalItems = itemList.size();
                RestResponsePage<List> model = new RestResponsePage<>(totalItems, 1, itemList);
                String outboundJson = objectMapper.writeValueAsString(model);
                return outboundJson;
            case SERVICE_TYPE_NAME:
                Set<String> svcTypeList = (Set) bpItemList.stream()
                    .map(x -> ((ServiceTypeSummary) x).getTypeName()).collect(Collectors.toSet());
                itemList = new ArrayList<String>();
                itemList.addAll(svcTypeList);
                break;
            case SERVICE_TYPES:
                // apply response filters
                itemList = bpItemList;
                String outFilter = request.getParameter("_include");
                if (outFilter != null) {
                    List<String> svcSummaryList =
                        (List) itemList.stream().map(x -> extractBpSummary((ServiceTypeSummary) x))
                            .collect(Collectors.toList());
                    return objectMapper.writeValueAsString(svcSummaryList);
                }
                // apply user search filters
                if (searchBy != null && !searchBy.isEmpty()) {
                    if (!svcRefFilterStr.isEmpty()) {
                        List<String> svcFilterList =
                            new ArrayList<>(Arrays.asList(svcRefFilterStr.split(",")));
                        if (!svcFilterList.isEmpty()) {
                            itemList = (List) itemList.stream()
                                .filter(s -> svcFilterList.stream()
                                    .anyMatch(svcFilter -> ((ServiceTypeSummary) s).getTypeName()
                                        .toLowerCase().contains(svcFilter.toLowerCase())))
                                .collect(Collectors.toList());
                        }
                    }
                    if (!appFilterStr.isEmpty()) {
                        List<String> appFilterList =
                            new ArrayList<>(Arrays.asList(appFilterStr.split(",")));
                        Predicate<ServiceTypeSummary> srvcAppFilter =
                            p -> p.getApplication() != null;
                        Stream<ServiceTypeSummary> svcStream = itemList.stream();

                        itemList =
                            svcStream
                                .filter(srvcAppFilter.and(s -> appFilterList.stream()
                                    .anyMatch(appFilter -> ((ServiceTypeSummary) s).getApplication()
                                        .equalsIgnoreCase(appFilter))))
                                .collect(Collectors.toList());
                    }
                    if (!compFilterStr.isEmpty()) {
                        List<String> compFilterList =
                            new ArrayList<>(Arrays.asList(compFilterStr.split(",")));
                        Predicate<ServiceTypeSummary> srvcCompFilter =
                            p -> p.getComponent() != null;
                        Stream<ServiceTypeSummary> svcStream = itemList.stream();
                        itemList = svcStream
                            .filter(srvcCompFilter.and(s -> compFilterList.stream().anyMatch(
                                compFilter -> (s).getComponent().equalsIgnoreCase(compFilter))))
                            .collect(Collectors.toList());
                    }

                    if (!containsFilterStr.isEmpty()) {
                        final String simpleSrch = containsFilterStr.split(",")[0];
                        itemList = (List<ServiceTypeSummary>) itemList.stream()
                            .filter(s -> ((ServiceTypeSummary) s).contains(simpleSrch))
                            .collect(Collectors.toList());
                    }
                }
                if (sortBy != null) {
                    if (sortBy.equals("owner")) {
                        ((List<ServiceTypeSummary>) itemList).sort((ServiceTypeSummary o1,
                            ServiceTypeSummary o2) -> o1.getOwner().compareTo(o2.getOwner()));
                    } else if (sortBy.equals("typeId")) {
                        ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getTypeId()
                                .get().compareTo(o2.getTypeId().get()));
                    } else if (sortBy.equals("typeName")) {
                        ((List<ServiceTypeSummary>) itemList).sort((ServiceTypeSummary o1,
                            ServiceTypeSummary o2) -> o1.getTypeName().compareTo(o2.getTypeName()));
                    } else if (sortBy.equals("typeVersion")) {
                        ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1
                                .getTypeVersion().compareTo(o2.getTypeVersion()));
                    } else if (sortBy.equals("created")) {
                        ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1.getCreated()
                                .get().compareTo(o2.getCreated().get()));
                    } else if (sortBy.equals("application")) {
                        ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1
                                .getApplication().compareTo(o2.getApplication()));
                    } else if (sortBy.equals("component")) {
                        ((List<ServiceTypeSummary>) itemList)
                            .sort((ServiceTypeSummary o1, ServiceTypeSummary o2) -> o1
                                .getComponent().compareTo(o2.getComponent()));
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
        if (itemList != null) {
            totalItems = itemList.size();
        }
        final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        if (totalItems > pageSize && option.equals(InventoryDataItem.SERVICE_TYPES)) {
            itemList = getPageOfList(pageNum, pageSize, itemList);
        }
        if (option.equals(InventoryDataItem.SERVICE_TYPES)) {
            if (!roleLevel.equals("ops")) {
                if (roleLevel.equals("dev") || roleLevel.equals("app_dev")) {
                    boolean deployFlag = roleAuth.equals("WRITE");
                    for (ServiceTypeSummary bp : (List<ServiceTypeSummary>) itemList) {
                        bp.setCanDeploy(Optional.of(deployFlag));
                    }
                } else {
                    for (ServiceTypeSummary bp : (List<ServiceTypeSummary>) itemList) {
                        String bpComp = bp.getComponent();
                        if (bpComp != null && bpComp.length() > 0) {
                            bpComp = bpComp.toLowerCase();
                        } else {
                            String bpOwner = bp.getOwner(); // for backward compatibility
                            if (bpOwner != null && bpOwner.contains(":")) {
                                bpComp = bp.getOwner().split(":")[0].toLowerCase();
                            }
                        }
                        if (compDeployTab.containsKey(bpComp)) {
                            boolean enableDeploy = compDeployTab.get(bpComp);
                            logger.debug(">>>> enable deploy button: " + enableDeploy);
                            bp.setCanDeploy(Optional.of(enableDeploy));
                        } else {
                            bp.setCanDeploy(Optional.of(false));
                        }
                    }
                }
            } else {
                for (ServiceTypeSummary bp : (List<ServiceTypeSummary>) itemList) {
                    bp.setCanDeploy(Optional.of(true));
                }
            }
            // add the deployments mapping to the list
            lock.readLock().lock();
            List<ServiceTypeServiceMap> cacheBpMapArr =
                (List<ServiceTypeServiceMap>) getCacheManager().getObject("bp_deploy_map");
            lock.readLock().unlock();
            if (cacheBpMapArr != null) {
                for (ServiceTypeSummary bpSum : (List<ServiceTypeSummary>) itemList) {
                    // correlate the cached data for deployments
                    List<ServiceTypeServiceMap> bp_depl_list = cacheBpMapArr
                        .stream().filter((Predicate<? super ServiceTypeServiceMap>) s -> s
                            .getServiceTypeId().equals(bpSum.getTypeId().get()))
                        .collect(Collectors.toList());
                    if (bp_depl_list != null && !bp_depl_list.isEmpty()) {
                        bpSum.setDeployments(
                            (ServiceRefCfyList) bp_depl_list.get(0).getServiceRefList());
                    }
                }
            }
        }
        RestResponsePage<List> model = new RestResponsePage<>(totalItems, pageCount, itemList);
        return objectMapper.writeValueAsString(model);
    }

    /**
     * scheduled method to build and update application cache
     * store containing a collection of user to blueprints list mapping.
     */
    @SuppressWarnings("unchecked")
    @Scheduled(fixedDelay = 600000, initialDelay = 150000)
    public void cacheOwnerToBpMap() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        List<ServiceTypeSummary> bpList =
            (List<ServiceTypeSummary>) getCacheManager().getObject(SERVICE_TYPES_PATH);
        lock.readLock().unlock();
        if (bpList != null) {
            Map<String, List<ServiceTypeSummary>> bpPerOwner =
                bpList.stream().collect(Collectors.groupingBy(bp -> bp.getOwner()));

            lock.writeLock().lock();
            getCacheManager().putObject("owner_bp_map", bpPerOwner);
            lock.writeLock().unlock();
        }
    }

    private BlueprintResponse extractBpSummary(ServiceTypeSummary bp) {
        return new BlueprintResponse(bp.getTypeName(), bp.getTypeVersion(), bp.getTypeId().get());
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
            outboundJson =
                getItemListForPageAuth(request, option, pageNum, pageSize, sortBy, searchBy);
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
            if (ex instanceof HttpStatusCodeException) {
                result =
                    new RestResponseError(((HttpStatusCodeException) ex).getResponseBodyAsString());
            } else {
                result = new RestResponseError("Failed to get " + option.name(), ex);
            }
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
     * Serves one page of blueprint owners
     * 
     * @param request HttpServletRequest
     * @return List of ServiceTypes objects
     */
    @GetMapping(value = {OWNERS}, produces = "application/json")
    public String getOwnersByPage(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, InventoryDataItem.OWNER,
            request.getParameter("sortBy"), request.getParameter("searchBy"));
        postLogAudit(request);
        return json;
    }

    /**
     * Serves one page of service types.
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
     * Query Service objects matching a service type ID.
     * 
     */
    @PostMapping(value = {DEP_IDS_FOR_TYPE}, produces = "application/json")
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
     * Serves the complete list of service type names.
     * 
     * @param request HttpServletRequest
     * 
     * @return list of service type names
     */
    @GetMapping(value = {SERVICE_TYPE_NAME}, produces = "application/json")
    @ResponseBody
    @Cacheable
    public String getAllServiceTypeNames(HttpServletRequest request) {
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICE_TYPE_NAME,
            request.getParameter("sortBy"), request.getParameter("searchBy"));
        postLogAudit(request);
        return json;
    }

    /**
     * Serves the aggregate count of service types.
     * 
     * @param request HttpServletRequest
     * 
     * @return count of service types
     */
    @GetMapping(value = {SERVICE_TYPE_ID}, produces = "application/json")
    public String getAllServiceTypeIds(HttpServletRequest request) {
        String json = null;
        json = getItemListForPageWrapper(request, InventoryDataItem.SERVICE_TYPE_ID,
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
    @GetMapping(
        value = {VIEW_SERVICE_TYPE_BLUEPRINT_PATH + "/{typeid}"},
        produces = "application/yaml")
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
    @DeleteMapping(value = {SERVICE_TYPES_PATH + "/{typeId:.+}"}, produces = "application/json")
    public String deleteServiceType(@PathVariable("typeId") String typeId,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = "{\"204\": \"Blueprint deleted\"}";
        boolean allow = true;
        try {
            // check if these dep_ids exist in cloudify
            List<CloudifyDeployedTenant> deplForBp =
                cloudifyClient.getDeploymentForBlueprint("TID-" + typeId);
            if (!deplForBp.isEmpty()) {
                allow = false;
            } else {
                ServiceQueryParams qryParams =
                    new ServiceQueryParams.Builder().typeId(typeId).build();
                ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
                Set<String> depIds = srvcRefs.items.stream().map(x -> ((ServiceRef) x).id)
                    .collect(Collectors.toSet());
                if (!depIds.isEmpty()) {
                    // now check again if these dep_ids still exist in cloudify
                    List<String> allDepNames = cloudifyClient.getDeploymentNamesWithFilter(request);
                    for (String str : depIds) {
                        if (allDepNames.stream().anyMatch(s -> s.equalsIgnoreCase(str))) {
                            allow = false;
                            break;
                        }
                    }
                }
            }
            if (!allow) {
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                json = objectMapper.writeValueAsString(
                    new RestResponseError("Deployments exist for this blueprint"));
                return json;
            } else {
                inventoryClient.deleteServiceType(typeId);
                this.cacheOwnerToBpMap();
            }
        } catch (ServiceTypeNotFoundException | ServiceTypeAlreadyDeactivatedException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteServiceType caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "DCAE Inventory");
            MDC.put("TargetServiceName", "DCAE Inventory");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting service type " + typeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteServiceType caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("delete blueprint failed", t));
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
    @PostMapping(value = {SERVICE_TYPES_PATH + "/update"}, produces = "application/json")
    public String updateServiceTypeBlueprint(HttpServletRequest request,
        @RequestBody ServiceType serviceType) throws Exception {
        preLogAudit(request);
        String json = "{\"201\": \"OK\"}";
        try {
            // Verify that the Service Type can be parsed for inputs.
            Blueprint.parse(serviceType.getBlueprintTemplate());
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
    @PostMapping(value = {SERVICE_TYPES_PATH + "/upload"}, produces = "application/json")
    public String uploadServiceTypeBlueprint(HttpServletRequest request,
        @RequestBody ServiceTypeRequest serviceTypeRequest) throws Exception {
        preLogAudit(request);
        String json = "{\"201\": \"OK\"}";
        try {
            Blueprint.parse(serviceTypeRequest.getBlueprintTemplate());
            inventoryClient.addServiceType(serviceTypeRequest);
            this.cacheOwnerToBpMap();
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
        Date begin = new Date();
        MDC.put(SystemProperties.AUDITLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.METRICSLOG_BEGIN_TIMESTAMP, logDateFormat.format(begin));
        MDC.put(SystemProperties.STATUS_CODE, "COMPLETE");
    }

    public void postLogAudit(HttpServletRequest request) {
        Date end = new Date();
        MDC.put("AlertSeverity", "0");
        MDC.put("TargetEntity", "DCAE Inventory");
        MDC.put("TargetServiceName", "DCAE Inventory");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + request.getRequestURI());
    }
}
