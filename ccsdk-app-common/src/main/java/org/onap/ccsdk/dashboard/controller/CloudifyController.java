/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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

package org.onap.ccsdk.dashboard.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.onap.ccsdk.dashboard.model.ECTransportModel;
import org.onap.ccsdk.dashboard.model.RestResponseError;
import org.onap.ccsdk.dashboard.model.RestResponsePage;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentExt;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentHelm;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEvent;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPlugin;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenant;
import org.onap.ccsdk.dashboard.model.cloudify.ServiceRefCfyList;
import org.onap.ccsdk.dashboard.model.consul.ConsulDeploymentHealth;
import org.onap.ccsdk.dashboard.model.inventory.Service;
import org.onap.ccsdk.dashboard.model.inventory.ServiceQueryParams;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRef;
import org.onap.ccsdk.dashboard.model.inventory.ServiceRefList;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeServiceMap;
import org.onap.ccsdk.dashboard.model.inventory.ServiceTypeSummary;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.rest.ConsulClient;
import org.onap.ccsdk.dashboard.rest.InventoryClient;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.onap.portalsdk.core.util.SystemProperties;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Controller for Cloudify features: blueprints, deployments, executions.
 * Methods serve Ajax requests made by Angular scripts on pages that show
 * content.
 */
@RestController
@RequestMapping("/")
public class CloudifyController extends DashboardRestrictedBaseController {

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(CloudifyController.class);

    @Autowired
    CloudifyClient cloudifyClient;
    @Autowired
    InventoryClient inventoryClient;
    @Autowired
    ConsulClient consulClient;

    /**
     * Enum for selecting an item type.
     */
    public enum CloudifyDataItem {
        BLUEPRINT, DEPLOYMENT, EXECUTION, TENANT, SERVICE_ID, SERVICE_ID_COUNT;
    }

    private static final String BLUEPRINTS_PATH = "blueprints";
    private static final String DEPLOYMENTS_PATH = "deployments";
    private static final String EXECUTIONS_PATH = "executions";
    private static final String TENANTS_PATH = "tenants";
    private static final String NODE_INSTANCES_PATH = "node-instances";
    private static final String EVENTS_PATH = "events";
    private static final String SERVICE_ID = "service-list";
    private static final String SERVICE_ID_COUNT = "service-list-count";
    private static final String PLUGINS_PATH = "plugins";
    private static final String PLUGIN_COUNT = "plugins-count";
    private static final String SERVICE_TYPES = "dcae-service-types";
    private static final String TARGET_ENTITY_KEY = "TargetEntity";
    private static final String TARGET_SERVICE_KEY = "TargetServiceName";
    private static final String CFY_DEP_ENTITY = "DCAE Deployment";
    private static final String CFY_TARGET_SERVICE = "DCAE Cloudify";
    private static final String ERROR_RESPONSE = "ERROR";
    private static final String ERROR_CODE_KEY = "ErrorCode";
    private static final String ERROR_CODE = "300";
    private static final String ERROR_CATEGORY_KEY = "ErrorCategory";
    private static final String ERROR_DESCRIPTION_KEY = "ErrorDescription";
    private static final String SORT_BY = "sortBy";
    private static final String SEARCH_BY = "searchBy";
    
    private AbstractCacheManager cacheManager;

    @Autowired
    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public AbstractCacheManager getCacheManager() {
        return cacheManager;
    }

    private void updateBpOwnerId(List<CloudifyDeployment> itemList,
        Map<String, List<CloudifyDeployedTenant>> ownerDepMap) {
        for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
            for (Map.Entry<String, List<CloudifyDeployedTenant>> entry : ownerDepMap.entrySet()) {
                List<CloudifyDeployedTenant> deplExtItems = entry.getValue().stream()
                    .filter((Predicate<? super CloudifyDeployedTenant>) s -> s.id.equals(srvc.id))
                    .collect(Collectors.toList());
                if (deplExtItems != null && !deplExtItems.isEmpty()) {
                    srvc.owner = entry.getKey();
                    break;
                }
            }
        }
    }

    private String getJsonErr(String errStr) {
        final String errJsonStr = "{ \"error\" : \"";
        StringBuffer errJson = new StringBuffer();
        errJson.append(errJsonStr).append(errStr).append("\"}");
        return errJson.toString();
    }
    
    private void updateWorkflowHelmStatus(List<CloudifyDeployment> itemList,
        List<CloudifyDeploymentExt> cfyDeplExt, List<CloudifyDeploymentHelm> cfyDeplHelm) {
        for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
            // correlate the cached data for deployments and deployment extensions
            List<CloudifyDeploymentExt> deplExtItems = cfyDeplExt.stream()
                .filter((Predicate<? super CloudifyDeploymentExt>) s -> s.id.equals(srvc.id))
                .collect(Collectors.toList());
            if (deplExtItems != null && deplExtItems.size() > 0) {
                srvc.lastExecution = deplExtItems.get(0).lastExecution;
            }
        }
        for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
            // correlate the cached data for deployments and deployment helm info
            List<CloudifyDeploymentHelm> deplHelmItems = cfyDeplHelm.stream()
                .filter((Predicate<? super CloudifyDeploymentHelm>) s -> s.id.equals(srvc.id))
                .collect(Collectors.toList());
            if (deplHelmItems != null && deplHelmItems.size() > 0) {
                srvc.helmStatus = deplHelmItems.get(0).helmStatus;
                srvc.isHelm = deplHelmItems.get(0).isHelm;
            }
        }
    }

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
    private String getItemListForPage(HttpServletRequest request, 
        CloudifyDataItem option, int pageNum, int pageSize, String sortBy, String searchBy)
        throws Exception {
        HttpSession session = AppUtils.getSession(request);
        String user = UserUtils.getUserSession(request).getLoginId();
        HashMap<String, Boolean> compDeployTab =
            (HashMap<String, Boolean>) session.getAttribute("comp_access");
        String roleLevel = (String) session.getAttribute("role_level");
        String roleAuth = (String) session.getAttribute("auth_role");
        if (roleAuth == null) {
            roleAuth = "READ";
        }
        if (roleLevel == null) {
            roleLevel = "dev";
        }
        if (compDeployTab == null) {
            compDeployTab = new HashMap<String, Boolean>();
        }
        Set<String> userApps = (Set<String>) session.getAttribute("authComponents");
        if (userApps == null) {
            userApps = new TreeSet<String>();
        }
        List itemList = null;
        new ArrayList<Service>();
        int totalItems = 0;
        int deplPgSize = 500;
        int deplPgOffset = 0;
        String tenantFilterStr = "";
        String svcRefFilterStr = "";
        String statusFilterStr = "";
        String helmFilterStr = "";
        String ownerFilterStr = "";
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<CloudifyDeploymentExt> cfyDeplExt = null;
        List<CloudifyDeploymentExt> cfyDepExList = new ArrayList<>();
        List<CloudifyDeploymentHelm> cfyDeplHelm = null;
        List<CloudifyDeploymentHelm> cfyDeplHelmList = new ArrayList<>();
        boolean useCache = true;
        boolean userLevel = false;
        List<CloudifyTenant> cfyTenantList = null;
        // apply tenant search filter
        if (searchBy != null && !searchBy.isEmpty()) {
            // parse the search filters string
            List<String> searchFilters = new ArrayList<String>(Arrays.asList(searchBy.split(";")));
            if (searchFilters.stream().anyMatch(s -> s.startsWith("tenant"))) {
                List<String> tenantsList = searchFilters.stream()
                    .filter(s -> s.startsWith("tenant")).collect(Collectors.toList());
                if (tenantsList.get(0).split(":").length > 1) {
                    tenantFilterStr = tenantsList.get(0).split(":")[1];
                }
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("cache"))) {
                List<String> cacheStr = searchFilters.stream().filter(s -> s.startsWith("cache"))
                    .collect(Collectors.toList());
                useCache = Boolean.parseBoolean(cacheStr.get(0).split(":")[1]);
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("user"))) {
                List<String> userStr = searchFilters.stream().filter(s -> s.startsWith("user"))
                    .collect(Collectors.toList());
                userLevel = Boolean.parseBoolean(userStr.get(0).split(":")[1]);
            }
            if (searchFilters.stream().anyMatch(s -> s.startsWith("owner"))) {
                List<String> ownerList = searchFilters.stream().filter(s -> s.startsWith("owner"))
                    .collect(Collectors.toList());
                ownerFilterStr = ownerList.get(0).split(":")[1];
            }
        }
        lock.readLock().lock();
        Map<String, List<CloudifyDeployedTenant>> deplPerOwner =
            (Map<String, List<CloudifyDeployedTenant>>) getCacheManager()
                .getObject("owner_deploy_map");
        lock.readLock().unlock();
        switch (option) {
            case SERVICE_ID_COUNT:
                List items = null;
                if (!ownerFilterStr.isEmpty()) {
                    if (deplPerOwner != null) {
                        List<CloudifyDeployedTenant> ownerDeplList = deplPerOwner.get(user);
                        items = ownerDeplList;
                    }
                } else {
                    List<CloudifyDeployment> itemsList =
                        cloudifyClient.getDeploymentsWithFilter(request);
                    items = itemsList;
                }
                if (items == null) {
                    items = new ArrayList<CloudifyDeployment>();
                }
                totalItems = items.size();
                RestResponsePage<List> model = new RestResponsePage<>(totalItems, 1, items);
                String outboundJson = objectMapper.writeValueAsString(model);
                return outboundJson;
            case SERVICE_ID:
                final String svcIdTenant = tenantFilterStr;
                if (useCache) {
                    lock.readLock().lock();
                    itemList = (List<CloudifyDeployment>) getCacheManager()
                        .getObject(SERVICE_ID + ":" + svcIdTenant);
                    lock.readLock().unlock();
                }
                if (itemList == null) {
                    itemList = cloudifyClient.getDeployments(tenantFilterStr, deplPgSize,
                        deplPgOffset, true);
                }
                Set<String> svcIdList = new HashSet<String>();
                if (itemList != null) {
                    svcIdList = (Set) itemList.stream().map(x -> ((CloudifyDeployment) x).id)
                        .collect(Collectors.toSet());
                    // apply role based filtering
                    if (roleLevel.equals("app")) {
                        @SuppressWarnings("unchecked")
                        List<String> myApps = new ArrayList<String>(userApps);
                        svcIdList = svcIdList.stream()
                            .filter(s -> myApps.stream().anyMatch(
                                roleFilter -> s.toLowerCase().startsWith(roleFilter)))
                            .collect(Collectors.toSet());
                    }
                }
                return objectMapper.writeValueAsString(svcIdList);
            case DEPLOYMENT:
                itemList = new ArrayList<CloudifyDeployment>();
                List<CloudifyDeployment> cfyDepPerTntList = null;
                List<CloudifyDeployment> cfyDepList = null;
                try {
                    if (tenantFilterStr.isEmpty()) {
                        cfyDepList = new ArrayList<>();
                        if (useCache) {
                            lock.readLock().lock();
                            cfyTenantList =
                                (List<CloudifyTenant>) getCacheManager().getObject(TENANTS_PATH);
                            for (CloudifyTenant cfyTenObj : cfyTenantList) {
                                cfyDepPerTntList = (List<CloudifyDeployment>) getCacheManager()
                                    .getObject(SERVICE_ID + ":" + cfyTenObj.name);
                                cfyDepList.addAll(cfyDepPerTntList);
                            }
                            lock.readLock().unlock();
                        } else {
                            cfyTenantList =
                                cloudifyClient.getTenants().items;
                            for (CloudifyTenant cfyTenObj : cfyTenantList) {
                                cfyDepPerTntList = cloudifyClient.getDeployments(cfyTenObj.name,
                                    deplPgSize, deplPgOffset, true, false);
                                cfyDepList.addAll(cfyDepPerTntList);
                            }
                        }
                    } else {
                        if (useCache) {
                            lock.readLock().lock();
                            cfyDepList = (List<CloudifyDeployment>) getCacheManager()
                                .getObject(SERVICE_ID + ":" + tenantFilterStr);
                            lock.readLock().unlock();
                        }
                        if (cfyDepList == null) {
                            cfyDepList = cloudifyClient.getDeployments(tenantFilterStr, deplPgSize,
                                deplPgOffset, true, false);
                        }
                    }
                    totalItems = cfyDepList.size();

                    if (useCache) {
                        lock.readLock().lock();
                        cfyDeplExt = (List<CloudifyDeploymentExt>) getCacheManager()
                            .getObject(SERVICE_ID + ":" + tenantFilterStr + ":ext");
                        if (cfyDeplExt != null) {
                            cfyDepExList.addAll(cfyDeplExt);
                        }
                        cfyDeplHelm = (List<CloudifyDeploymentHelm>) getCacheManager()
                            .getObject(SERVICE_ID + ":" + tenantFilterStr + ":helm");
                        if (cfyDeplHelm != null) {
                            cfyDeplHelmList.addAll(cfyDeplHelm);
                        }
                        lock.readLock().unlock();
                    }

                    if (roleLevel.equals("app") && !userLevel) {
                        @SuppressWarnings("unchecked")
                        List<String> myApps = new ArrayList(userApps);
                        cfyDepList =
                            cfyDepList.stream()
                                .filter(s -> myApps.stream()
                                    .anyMatch(roleFilter -> s.id
                                        .toLowerCase().startsWith(roleFilter)))
                                .collect(Collectors.toList());
                    }
                    // apply user search filters
                    if (searchBy != null && !searchBy.isEmpty()) {
                        // parse the search filters string
                        // look for service name patterns
                        List<String> searchFilters =
                            new ArrayList<>(Arrays.asList(searchBy.split(";")));
                        if (searchFilters.stream().anyMatch(s -> s.startsWith("serviceRef"))) {
                            List<String> svcRefsList =
                                searchFilters.stream().filter(s -> s.startsWith("serviceRef"))
                                    .collect(Collectors.toList());
                            svcRefFilterStr = svcRefsList.get(0).split(":")[1];
                        }
                        if (searchFilters.stream().anyMatch(s -> s.startsWith("status"))) {
                            List<String> statusList = searchFilters.stream()
                                .filter(s -> s.startsWith("status")).collect(Collectors.toList());
                            statusFilterStr = statusList.get(0).split(":")[1];
                        }
                        if (searchFilters.stream().anyMatch(s -> s.startsWith("helm"))) {
                            List<String> helmList = searchFilters.stream()
                                .filter(s -> s.startsWith("helm")).collect(Collectors.toList());
                            helmFilterStr = helmList.get(0).split(":")[1];
                        }
                        if (!ownerFilterStr.isEmpty()) {
                            List<String> ownerFilterList =
                                new ArrayList<>(Arrays.asList(ownerFilterStr.split(",")));
                            if (ownerFilterList.size() == 1
                                && ownerFilterList.get(0).equals("undefined")) {
                                ownerFilterList.clear();
                                ownerFilterList.add(user);
                            }
                            if (deplPerOwner != null && cfyDepList != null) {
                                List<CloudifyDeployedTenant> ownerDeplList =
                                    new ArrayList<>();
                                Stream<Map.Entry<String, List<CloudifyDeployedTenant>>> deplOwnerEntriesStream =
                                    deplPerOwner.entrySet().stream();
                                Set<Map.Entry<String, List<CloudifyDeployedTenant>>> deplOwnerSet =
                                    deplOwnerEntriesStream
                                        .filter(m -> ownerFilterList.stream()
                                            .anyMatch(ownFilter -> m.getKey()
                                                .equalsIgnoreCase(ownFilter)))
                                        .collect(Collectors.toSet());
                                deplOwnerSet.stream()
                                    .forEach(e -> ownerDeplList.addAll(e.getValue()));
                                if (!ownerDeplList.isEmpty()) {
                                    Predicate<CloudifyDeployment> In2 = s -> ownerDeplList.stream()
                                        .anyMatch(mc -> s.id.equals(mc.id));
                                    cfyDepList = cfyDepList.stream().filter(In2)
                                        .collect(Collectors.toList());
                                } else {
                                    cfyDepList = new ArrayList<>();
                                }
                            }
                        }
                        if (!svcRefFilterStr.isEmpty()) {
                            List<String> svcFilterList =
                                new ArrayList<String>(Arrays.asList(svcRefFilterStr.split(",")));
                            if (!svcFilterList.isEmpty()) {
                                cfyDepList = cfyDepList.stream()
                                    .filter(s -> svcFilterList.stream()
                                        .anyMatch(svcFilter -> s.id
                                            .toLowerCase().contains(svcFilter.toLowerCase())))
                                    .collect(Collectors.toList());
                            }
                        }
                        if (!statusFilterStr.isEmpty()) {
                            List<String> statusFilterList =
                                new ArrayList<String>(Arrays.asList(statusFilterStr.split(",")));
                            Predicate<CloudifyDeployment> srvcFilter =
                                p -> p.lastExecution.status != null;
                            Stream<CloudifyDeployment> svcStream = cfyDepList.stream();
                            if (cfyDepExList == null || cfyDepExList.isEmpty()) {
                                cfyDepExList = cloudifyClient.updateWorkflowStatus(cfyDepList);
                            }
                            if (cfyDepExList != null) {
                                updateWorkflowHelmStatus(cfyDepList, cfyDepExList, cfyDeplHelmList);
                                cfyDepList = svcStream
                                    .filter(srvcFilter.and(s -> statusFilterList.stream().anyMatch(
                                        statusFilter -> s.lastExecution.status
                                            .equalsIgnoreCase(statusFilter))))
                                    .collect(Collectors.toList());
                            }
                        }
                        if (!helmFilterStr.isEmpty()) {
                            Predicate<CloudifyDeployment> helmFilter = p -> p.helmStatus != null;
                            boolean isHelm = Boolean.parseBoolean(helmFilterStr);
                            if (cfyDeplHelmList == null || cfyDeplHelmList.isEmpty()) {
                                cfyDeplHelmList = cloudifyClient.updateHelmInfo(cfyDepList);
                            }
                            if (cfyDeplHelmList != null && isHelm) {
                                updateWorkflowHelmStatus(cfyDepList, cfyDepExList, cfyDeplHelmList);
                                cfyDepList = (List) cfyDepList.stream()
                                    .filter(helmFilter
                                        .and(s -> s.isHelm))
                                    .collect(Collectors.toList());
                            }
                        }
                    }
                    itemList.addAll(cfyDepList);
                } catch (Exception cfyDepErr) {
                    logger.error(EELFLoggerDelegate.errorLogger, cfyDepErr.getMessage());
                }
                if (sortBy != null) {
                    if (sortBy.equals("id")) {
                        ((List<CloudifyDeployment>) itemList).sort((CloudifyDeployment o1,
                            CloudifyDeployment o2) -> o1.id.compareTo(o2.id));
                    } else if (sortBy.equals("blueprint_id")) {
                        ((List<CloudifyDeployment>) itemList).sort((CloudifyDeployment o1,
                            CloudifyDeployment o2) -> o1.blueprint_id.compareTo(o2.blueprint_id));
                    } else if (sortBy.equals("created_at")) {
                        ((List<CloudifyDeployment>) itemList).sort((CloudifyDeployment o1,
                            CloudifyDeployment o2) -> o1.created_at.compareTo(o2.created_at));
                    } else if (sortBy.equals("updated_at")) {
                        ((List<CloudifyDeployment>) itemList).sort((CloudifyDeployment o1,
                            CloudifyDeployment o2) -> o1.updated_at.compareTo(o2.updated_at));
                    }
                }
                break;
            case TENANT:
                lock.readLock().lock();
                cfyTenantList = (List<CloudifyTenant>) getCacheManager().getObject(TENANTS_PATH);
                lock.readLock().unlock();
                if (cfyTenantList == null || cfyTenantList.isEmpty()) {
                    cfyTenantList = (List<CloudifyTenant>) cloudifyClient.getTenants().items;
                }

                itemList = cfyTenantList;
                break;
            default:              
                MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
                MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
                MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
                MDC.put(ERROR_CODE_KEY, ERROR_CODE);
                MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
                MDC.put(ERROR_DESCRIPTION_KEY, "Getting page of items failed!");
                logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPage caught exception");
                throw new Exception(
                    "getItemListForPage failed: unimplemented case: " + option.name());
        }
        // Shrink if needed
        if (itemList != null) {
            totalItems = itemList.size();
        }
        final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        if (totalItems > pageSize) {
            itemList = getPageOfList(pageNum, pageSize, itemList);
        }

        if (option.equals(CloudifyDataItem.DEPLOYMENT)) {
            // update blueprint owner for each deployment record
            if (deplPerOwner != null) {
                updateBpOwnerId(itemList, deplPerOwner);
            }
            if (cfyDepExList == null || cfyDepExList.isEmpty()) {
                cfyDepExList = cloudifyClient.updateWorkflowStatus(itemList);
            }
            if (cfyDeplHelmList == null || cfyDeplHelmList.isEmpty()) {
                cfyDeplHelmList = cloudifyClient.updateHelmInfo(itemList);
            }
            for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
                try {
                    ConsulDeploymentHealth serviceHealth =
                        consulClient.getServiceHealthByDeploymentId(srvc.id);
                    if (serviceHealth != null) {
                        srvc.healthStatus = serviceHealth.getStatus();
                    }
                } catch (Exception e) {
                    logger.error(EELFLoggerDelegate.errorLogger,
                        "consul getServiceHealth caught exception");
                }
                // correlate the cached data for deployments and deployment extensions
                if (cfyDepExList != null) {
                    List<CloudifyDeploymentExt> deplExtItems = cfyDepExList.stream()
                        .filter(
                            (Predicate<? super CloudifyDeploymentExt>) s -> s.id.equals(srvc.id))
                        .collect(Collectors.toList());
                    if (deplExtItems != null && !deplExtItems.isEmpty()) {
                        srvc.lastExecution = deplExtItems.get(0).lastExecution;
                    }
                }
                if (cfyDeplHelmList != null) {
                    List<CloudifyDeploymentHelm> deplHelmItems = cfyDeplHelmList.stream()
                        .filter(
                            (Predicate<? super CloudifyDeploymentHelm>) s -> s.id.equals(srvc.id))
                        .collect(Collectors.toList());
                    if (deplHelmItems != null && !deplHelmItems.isEmpty()) {
                        srvc.helmStatus = deplHelmItems.get(0).helmStatus;
                        srvc.isHelm = deplHelmItems.get(0).isHelm;
                    }
                }
            }
            // check for authorization to perform delete deployed blueprints
            if (!roleLevel.equals("ops")) {
                if (roleLevel.equals("dev") || roleLevel.equals("app_dev")) {
                    boolean deployFlag = roleAuth.equals("WRITE") ? true : false;
                    for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
                        srvc.canDeploy = deployFlag;
                    }
                } else {
                    for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
                        String deplRef = srvc.id.split("_")[0].toLowerCase();
                        if (compDeployTab.containsKey(deplRef)) {
                            boolean enableDeploy = compDeployTab.get(deplRef);
                            srvc.canDeploy = enableDeploy;
                        } else {
                            srvc.canDeploy = false;
                        }
                    }
                }
            } else {
                for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
                    srvc.canDeploy = true;
                }
            }
        }
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
     * @param inputKey Input key to search for if using filters
     * @param inputValue Input value to search for if using filters
     * @return JSON with one page of objects; or an error.
     */
    protected String getItemListForPageWrapper(HttpServletRequest request, CloudifyDataItem option,
        String sortBy, String searchBy) {
        String outboundJson = null;
        RestResponseError result = null;
        try {
            User appUser = UserUtils.getUserSession(request);
            if (appUser == null || appUser.getLoginId() == null
                || appUser.getLoginId().length() == 0) {
                throw new Exception("getItemListForPageWrapper: Failed to get application user");
            }
            int pageNum = getRequestPageNumber(request);
            int pageSize = getRequestPageSize(request);
            outboundJson = getItemListForPage(request, option, pageNum, pageSize,
                sortBy, searchBy);
        } catch (HttpStatusCodeException ex) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, "Getting page of items failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception");
            result =
                    new RestResponseError(ex.getResponseBodyAsString());
            try {
                outboundJson = objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException jpe) {
                // Should never, ever happen
                outboundJson = "{ \"error\" : \"" + jpe.toString() + "\"}";
            }
        } catch (Exception ex) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, "Getting page of items failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getItemListForPageWrapper caught exception");
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
     * Serves one page of deployments
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
    @GetMapping(
        value = {DEPLOYMENTS_PATH},
        produces = "application/json")
    public String getDeploymentsByPage(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, CloudifyDataItem.DEPLOYMENT,
            request.getParameter(SORT_BY), request.getParameter(SEARCH_BY));
        postLogAudit(request);
        return json;
    }

    /**
     * Gets the specified blueprint content for viewing.
     * 
     * @param id
     * Blueprint ID
     * @param request
     * HttpServletRequest
     * @return Blueprint as YAML; or error.
     * @throws Exception
     * on serialization error
     * 
     */
    @GetMapping(
        value = {BLUEPRINTS_PATH + "/{id:.+}" + "/archive"},
        produces = "application/yaml")
    public byte[] viewBlueprintContentById(@PathVariable("id") String id,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        byte[] result = null;
        final String errStr = "View blueprint failed: " + id ;
        final String errLogStr = "viewBlueprintContentById caught exception";
        try {
            result = cloudifyClient.viewBlueprint(tenant, id);
        } catch (HttpStatusCodeException e) {       
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
        } catch (Exception t) {          
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
        } finally {
            postLogAudit(request);
        }
        return result;
    }

    /**
     * Serves the complete list of deployment IDs.
     * 
     * @param request HttpServletRequest
     * 
     * @return list of cloudify deployment IDs
     */
    @GetMapping(value = {SERVICE_ID}, produces = "application/json")
    public String getAllServiceNames(HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, CloudifyDataItem.SERVICE_ID,
            request.getParameter(SORT_BY), request.getParameter(SEARCH_BY));
        postLogAudit(request);
        return json;
    }

    /**
     * Serves the count of deployment IDs.
     * 
     * @param request HttpServletRequest
     * 
     * @return list of cloudify deployment IDs
     */
    @GetMapping(
        value = {SERVICE_ID_COUNT},
        produces = "application/json")
    public String getDepCount(HttpServletRequest request) {
        preLogAudit(request);
        String json = null;
        json = getItemListForPageWrapper(request, CloudifyDataItem.SERVICE_ID_COUNT,
            request.getParameter(SORT_BY), request.getParameter(SEARCH_BY));
        postLogAudit(request);
        return json;
    }

    /**
     * Serves the count of plugins.
     * 
     * @param request HttpServletRequest
     * 
     * @return count of cloudify plugins
     * @throws JsonProcessingException
     */
    @GetMapping(
        value = {PLUGIN_COUNT},
        produces = "application/json")
    public String getPluginCount(HttpServletRequest request) throws JsonProcessingException {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting plugins failed!";
        final String errLogStr = "getPlugins caught exception";
        try {
            int totalItems = (int) cloudifyClient.getPlugins().metadata.pagination.total;
            RestResponsePage<List> model = new RestResponsePage<>(totalItems, 1, null);
            return objectMapper.writeValueAsString(model);
        } catch (HttpStatusCodeException e) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
            return objectMapper.writeValueAsString(result);
        } catch (Exception t) {            
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getSecret failed", t);
            return objectMapper.writeValueAsString(result);
        } finally {
            postLogAudit(request);
        }
    }

    /**
     * get the tenants list.
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
    @GetMapping(
        value = {TENANTS_PATH},
        produces = "application/json")
    public String getTenants(HttpServletRequest request) {
        preLogAudit(request);
        String json = getItemListForPageWrapper(request, CloudifyDataItem.TENANT,
            request.getParameter(SORT_BY), request.getParameter(SEARCH_BY));
        postLogAudit(request);
        return json;
    }

    /**
     * Gets the specified blueprint metadata.
     * 
     * @param id Blueprint ID
     * @param request HttpServletRequest
     * @return Blueprint as JSON; or error.
     * @throws Exception on serialization error
     * 
     */
    @GetMapping(
        value = {BLUEPRINTS_PATH + "/{id:.+}"},
        produces = "application/json")
    public String getBlueprintById(@PathVariable("id") String id,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting blueprint failed: " + id ;
        final String errLogStr = "getBlueprintById caught exception";
        try {
            result = cloudifyClient.getBlueprint(id, tenant);
        } catch (HttpStatusCodeException e) {
            
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getBlueprintById failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Deletes the specified blueprint.
     * 
     * @param id Blueprint ID
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return No content on success; error on failure.
     * @throws Exception On serialization failure
     */
    @DeleteMapping(
        value = {BLUEPRINTS_PATH + "/{id}"},
        produces = "application/json")
    public String deleteBlueprint(@PathVariable("id") String id,
        @RequestParam(value = "tenant", required = false) String tenant, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = "{\"202\": \"OK\"}";
        final String errStr = "Deleting blueprint " + id + " failed!";
        final String errLogStr = "deleteBlueprint caught exception";
        try {
            cloudifyClient.deleteBlueprint(id, tenant);
            response.setStatus(202);
        } catch (HttpStatusCodeException e) {         
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Exception t) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            json = objectMapper
                .writeValueAsString(new RestResponseError("deleteBlueprint failed on ID " + id, t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }

    /**
     * 
     * Deployment IDs for the given list of blueprint IDs.
     * 
     */
    @PostMapping(
        value = {"deployment_blueprint_map"},
        produces = "application/json")
    public String getDeploymentsForType(HttpServletRequest request, @RequestBody String[] typeList)
        throws Exception {
        List<ServiceTypeServiceMap> result = new ArrayList<ServiceTypeServiceMap>();
        for (String typeId : typeList) {
            List<CloudifyDeployedTenant> deplForBpAggr = new ArrayList<CloudifyDeployedTenant>();
            List<CloudifyDeployedTenant> deplForBp =
                cloudifyClient.getDeploymentForBlueprint("TID-" + typeId);
            deplForBpAggr.addAll(deplForBp);

            ServiceQueryParams qryParams = new ServiceQueryParams.Builder().typeId(typeId).build();
            ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
            Set<String> depIds =
                srvcRefs.items.stream().map(x -> ((ServiceRef) x).id).collect(Collectors.toSet());
            if (!depIds.isEmpty()) {
                // lookup these dep_ids in cloudify for tenant mapping
                for (String str : depIds) {
                    List<CloudifyDeployedTenant> deplForBpInv =
                        cloudifyClient.getDeploymentForBlueprint(str);
                    deplForBpAggr.addAll(deplForBpInv);
                }
            }
            ServiceRefCfyList cfyDeplRefList =
                new ServiceRefCfyList(deplForBpAggr, deplForBpAggr.size());
            ServiceTypeServiceMap srvcMap = new ServiceTypeServiceMap(typeId, cfyDeplRefList);
            result.add(srvcMap);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Scheduled method to build and update application cache
     * containing a collection of user to deployments mapping.
     */
    @SuppressWarnings("unchecked")
    @Scheduled(fixedDelay = 3600000, initialDelay = 180000)
    public void cacheOwnerDeployMap() {
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheOwnerDeployMap begin");
        Map<String, List<CloudifyDeployedTenant>> deplPerOwner =
            new HashMap<>();
        new HashMap<String, List<ServiceTypeSummary>>();
        List<ServiceTypeSummary> bpNoDeplItems = new ArrayList<>();
        List<ServiceTypeServiceMap> result = new ArrayList<>();
        List<CloudifyDeployedTenant> deplForBpAggr = new ArrayList<>();
        String typeId = "";
        String owner = "";

        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        List<ServiceTypeSummary> bpItems =
            (List<ServiceTypeSummary>) getCacheManager().getObject(SERVICE_TYPES);
        lock.readLock().unlock();
        if (bpItems != null) {
            for (ServiceTypeSummary item : bpItems) {
                try {
                    typeId = item.getTypeId().get();
                    owner = item.getOwner();
                    List<CloudifyDeployedTenant> deplForBp =
                        cloudifyClient.getDeploymentForBlueprint("TID-" + typeId);
                    deplForBpAggr.clear();
                    deplForBpAggr.addAll(deplForBp);
                    ServiceQueryParams qryParams =
                        new ServiceQueryParams.Builder().typeId(typeId).build();
                    ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);
                    Set<String> depIds = srvcRefs.items.stream().map(x -> (x).id)
                        .collect(Collectors.toSet());
                    if (!depIds.isEmpty()) {
                        for (String str : depIds) {
                            List<CloudifyDeployedTenant> deplForBpInv =
                                cloudifyClient.getDeploymentForBlueprint(str);
                            deplForBpAggr.addAll(deplForBpInv);
                        }
                    }
                } catch (Exception e) {
                    logger.error(EELFLoggerDelegate.errorLogger,
                        "cacheOwnerDeployMap: " + e.getMessage());
                } finally {
                    if (deplForBpAggr.isEmpty()) {
                        bpNoDeplItems.add(item);
                    }
                    List<CloudifyDeployedTenant> iterBpIdDepl =
                        new ArrayList<CloudifyDeployedTenant>();
                    iterBpIdDepl.addAll(deplForBpAggr);
                    ServiceRefCfyList cfyDeplRefList =
                        new ServiceRefCfyList(iterBpIdDepl, iterBpIdDepl.size());
                    ServiceTypeServiceMap srvcMap =
                        new ServiceTypeServiceMap(typeId, cfyDeplRefList);
                    result.add(srvcMap);

                    List<CloudifyDeployedTenant> iterDeplBpAggr =
                        new ArrayList<>();
                    iterDeplBpAggr.addAll(deplForBpAggr);
                    if (deplPerOwner.containsKey(owner)) {
                        List<CloudifyDeployedTenant> currOwnerDepl = deplPerOwner.get(owner);
                        iterDeplBpAggr.addAll(0, currOwnerDepl);
                        deplPerOwner.put(owner, iterDeplBpAggr);
                    } else {
                        deplPerOwner.putIfAbsent(owner, iterDeplBpAggr);
                    }
                }
            }
        }

        lock.writeLock().lock();
        getCacheManager().putObject("bp_deploy_map", result);
        getCacheManager().putObject("owner_deploy_map", deplPerOwner);
        lock.writeLock().unlock();
    }

    /**
     * Gets the specified deployment.
     * 
     * @param id Deployment ID
     * @param request HttpServletRequest
     * @return Deployment for the specified ID; error on failure.
     * @throws Exception On serialization failure
     * 
     */
    @GetMapping(
        value = {DEPLOYMENTS_PATH + "/{id:.+}"},
        produces = "application/json")
    public String getDeploymentById(@PathVariable("id") String id,
        @RequestParam(value = "tenant", required = false) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting deployment " + id + " failed!";
        final String errLogStr = "getDeploymentById caught exception";
        try {
            if (tenant != null && tenant.length() > 0) {
                result = cloudifyClient.getDeployment(id, tenant);
            } else {
                result = cloudifyClient.getDeployment(id);
            }
        } catch (HttpStatusCodeException e) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {          
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getDeploymentById failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets and serves one page of executions.
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
     * @param request HttpServletRequest
     * @param deployment_id Optional request parameter; if found, only executions
     * for that deployment ID are returned.
     * @param status Optional request parameter; if found, only executions
     * with that status are returned.
     * @return List of CloudifyExecution objects
     * @throws Exception on serialization failure
     */
    @SuppressWarnings("unchecked")
    @GetMapping(
        value = {EXECUTIONS_PATH},
        produces = "application/json")
    public String getExecutionsByPage(HttpServletRequest request,
        @RequestParam(value = "deployment_id", required = true) String deploymentId,
        @RequestParam(value = "status", required = false) String status,
        @RequestParam(value = "tenant", required = true) String tenant) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting executions failed!";
        final String errLogStr = "getExecutionsByPage caught exception";
        try {
            List<CloudifyExecution> itemList = new ArrayList<CloudifyExecution>();
            List<String> depIds = new ArrayList<>();
            if (deploymentId == null) {
                CloudifyDeploymentList depList = cloudifyClient.getDeployments(tenant, 100, 0);
                for (CloudifyDeployment cd : depList.items)
                    depIds.add(cd.id);
            } else {
                depIds.add(deploymentId);
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
                    if (!status.equals(ce.status)) {
                        exeIter.remove();
                    }
                }
            }
            itemList.sort((CloudifyExecution o1, CloudifyExecution o2) -> o1
                .created_at.compareTo(o2.created_at));
            // Paginate
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize) {
                itemList = getPageOfList(pageNum, pageSize, itemList);
            }
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (Exception t) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(errLogStr, t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(
        value = {EXECUTIONS_PATH + "/tenant"},
        produces = "application/json")
    public String getExecutionsPerTenant(HttpServletRequest request,
        @RequestParam(value = "tenant", required = true) String tenant,
        @RequestParam(value = "status", required = false) String status) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<CloudifyTenant> cfyTenantList = null;
        List<CloudifyTenant> myTenantsList = null;
        final String errStr = "Getting executions failed!";
        final String errLogStr = "getExecutionsByPage caught exception";
        try {
            List<CloudifyExecution> itemList = new ArrayList<>();
            if (tenant == null) {
                // process all tenants that are relevant
                lock.readLock().lock();
                cfyTenantList = (List<CloudifyTenant>) getCacheManager().getObject(TENANTS_PATH);
                lock.readLock().unlock();
                if (cfyTenantList == null || cfyTenantList.isEmpty()) {
                    cfyTenantList = cloudifyClient.getTenants().items;
                }
                myTenantsList = cfyTenantList;

                for (CloudifyTenant tenItem : myTenantsList) {
                    CloudifyExecutionList exeList =
                        cloudifyClient.getExecutionsSummaryPerTenant(tenItem.name);
                    // Filter down to specified status as needed
                    if (status != null && !status.isEmpty()) {
                        Iterator<CloudifyExecution> exeIter = exeList.items.iterator();
                        while (exeIter.hasNext()) {
                            CloudifyExecution ce = exeIter.next();
                            if (!status.equals(ce.status)) {
                                exeIter.remove();
                            }
                        }
                    }
                    itemList.addAll(exeList.items);
                }
            } else {
                CloudifyExecutionList exeList =
                    cloudifyClient.getExecutionsSummaryPerTenant(tenant);
                itemList.addAll(exeList.items);

                // Filter down to specified status as needed
                if (status != null && !status.isEmpty()) {
                    Iterator<CloudifyExecution> exeIter = itemList.iterator();
                    while (exeIter.hasNext()) {
                        CloudifyExecution ce = exeIter.next();
                        if (!status.equals(ce.status)) {
                            exeIter.remove();
                        }
                    }
                }
            }
            // Paginate
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize) {
                itemList = getPageOfList(pageNum, pageSize, itemList);
            }
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (Exception t) {           
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(errLogStr, t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(
        value = {EXECUTIONS_PATH + "/{id:.+}"},
        produces = "application/json")
    public String queryExecution(@PathVariable("id") String id,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting executions failed!";
        final String errLogStr = "getExecutionsByPage caught exception";
        try {
            List<CloudifyExecution> itemList = new ArrayList<>();
            CloudifyExecution cfyExecObj = cloudifyClient.getExecutionIdSummary(id, tenant);
            itemList.add(cfyExecObj);
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize) {
                itemList = getPageOfList(pageNum, pageSize, itemList);
            }
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (Exception t) {            
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(errLogStr, t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(
        value = {EXECUTIONS_PATH + "/active"},
        produces = "application/json")
    public String getActiveExecutions(HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<CloudifyTenant> cfyTenantList = null;
        List<CloudifyTenant> myTenantsList = null;
        String status = "started";
        final String errStr = "Getting executions failed!";
        final String errLogStr = "getExecutionsByPage caught exception";
        try {
            List<CloudifyExecution> itemList = new ArrayList<>();
            // process all tenants that are relevant
            lock.readLock().lock();
            cfyTenantList = (List<CloudifyTenant>) getCacheManager().getObject(TENANTS_PATH);
            lock.readLock().unlock();
            if (cfyTenantList == null || cfyTenantList.isEmpty()) {
                cfyTenantList = cloudifyClient.getTenants().items;
            }
            myTenantsList = cfyTenantList;

            for (CloudifyTenant tenItem : myTenantsList) {
                CloudifyExecutionList exeList = null;
                try {
                    exeList = cloudifyClient.getExecutionsSummaryPerTenant(tenItem.name);
                } catch (Exception e) {
                    continue;
                }
                // Filter down to specified status as needed
                Iterator<CloudifyExecution> exeIter = exeList.items.iterator();
                while (exeIter.hasNext()) {
                    CloudifyExecution ce = exeIter.next();
                    if (!status.equals(ce.status))
                        exeIter.remove();
                }
                itemList.addAll(exeList.items);
            }
            // Paginate
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize) {
                itemList = getPageOfList(pageNum, pageSize, itemList);
            }
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (Exception t) {            
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(errLogStr, t);
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
     * @param tenant tenant name (query parameter)
     * @param request HttpServletRequest
     * @return CloudifyExecutionList
     * @throws Exception on serialization failure
     */
    @SuppressWarnings("unchecked")
    @GetMapping(
        value = {EVENTS_PATH},
        produces = "application/json")
    public String getExecutionEventsById(
        @RequestParam(value = "execution_id", required = false) String executionId,
        @RequestParam(value = "logType", required = false) String isLogEvent,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        CloudifyEventList eventsList = null;
        ECTransportModel result = null;
        final String errStr = "Getting executions failed for: " + executionId;
        final String errLogStr = "getExecutionEventsById caught exception";        
        try {
            eventsList = cloudifyClient.getEventlogs(executionId, tenant);
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
            itemList.sort((CloudifyEvent o1, CloudifyEvent o2) -> o1
                .reported_timestamp.compareTo(o2.reported_timestamp));
            Collections.reverse(itemList);
            final int pageNum = getRequestPageNumber(request);
            final int pageSize = getRequestPageSize(request);
            final int totalItems = itemList.size();
            final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
            // Shrink if needed
            if (totalItems > pageSize) {
                itemList = getPageOfList(pageNum, pageSize, itemList);
            }
            result = new RestResponsePage<>(totalItems, pageCount, itemList);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getExecutionEventsById failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Processes request to create an execution based on a deployment.
     * 
     * @param request HttpServletRequest
     * @param execution Execution model
     * @return Information about the execution
     * @throws Exception on serialization failure
     */
    @PostMapping(
        value = {EXECUTIONS_PATH},
        produces = "application/json")
    public String startExecution(HttpServletRequest request,
        @RequestBody CloudifyExecutionRequest execution) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Starting execution failed!";
        final String errLogStr = "startExecution caught exception";
        try {
            if (!(execution.workflow_id.equals("status")
                || execution.workflow_id.equals("execute_operation"))
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
            if (execution.workflow_id.equals("upgrade")) {
                String repoUser =
                    cloudifyClient.getSecret("controller_helm_user", execution.getTenant()).value;
                String repoUserPassword = cloudifyClient.getSecret("controller_helm_password",
                    execution.getTenant()).value;
                execution.getParameters().put("repo_user", repoUser);
                execution.getParameters().put("repo_user_password", repoUserPassword);
            }
            result = cloudifyClient.startExecution(execution);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {     
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("startExecution failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
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
    @PostMapping(
        value = {EXECUTIONS_PATH + "/{id}"},
        produces = "application/json")
    public String cancelExecution(@RequestHeader HttpHeaders headers, @PathVariable("id") String id,
        @RequestBody Map<String, String> parameters, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        List<String> tenant = null;
        final String errStr = "Cancelling execution failed: " + id;
        final String errLogStr = "cancelExecution caught exception";
        try {
            tenant = headers.get("tenant");
            result = cloudifyClient.cancelExecution(id, parameters, tenant.get(0));
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("cancelExecution failed on ID " + id, t);
        } finally {
            postLogAudit(request);
        }
        if (result == null) {
            return null;
        }
        else {
            return objectMapper.writeValueAsString(result);
        }
    }

    /**
     * Gets the specified node-instances details
     * 
     * @query param deployment deployment ID
     * @query param tenant tenant name
     * @param request HttpServletRequest
     * @return node instances as a string; or error.
     * @throws Exception on serialization error
     * 
     */
    @GetMapping(
        value = {"node-instances-data"},
        produces = "application/json")
    public String getNodeInstanceDetails(
        @RequestParam(value = "deployment", required = true) String deployment,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting node-instance failed for deploymentId: " + deployment;
        final String errLogStr = "getNodeInstance caught exception";
        try {
            result = cloudifyClient.getNodeInstanceDetails(deployment, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getNodeInstance failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the specified node-instances for viewing.
     * 
     * @param id deployment ID
     * @param request HttpServletRequest
     * @return node instances as a string; or error.
     * @throws Exception on serialization error
     * 
     */
    @GetMapping(
        value = {NODE_INSTANCES_PATH + "/{deploymentId:.+}"},
        produces = "application/yaml")
    public String getNodeInstances(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        final String errStr = "Getting node-instance-id failed for deploymentId: " + deploymentId;
        final String errLogStr = "getNodeInstanceId caught exception";
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            result = cloudifyClient.getNodeInstances(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, ERROR_RESPONSE);
            MDC.put(TARGET_ENTITY_KEY, CFY_DEP_ENTITY);
            MDC.put(TARGET_SERVICE_KEY, CFY_TARGET_SERVICE);
            MDC.put(ERROR_CODE_KEY, ERROR_CODE);
            MDC.put(ERROR_CATEGORY_KEY, ERROR_RESPONSE);
            MDC.put(ERROR_DESCRIPTION_KEY, errStr);
            logger.error(EELFLoggerDelegate.errorLogger, errLogStr);
            result = new RestResponseError("getNodeInstanceId failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    /**
     * Gets the specified node-instance-id content for viewing.
     * 
     * @param id deployment ID
     * @param id node ID
     * @param request HttpServletRequest
     * @return Blueprint as YAML; or error.
     * @throws Exception on serialization error
     * 
     */
    @GetMapping(
        value = {NODE_INSTANCES_PATH + "/{deploymentId:.+}/{nodeId}"},
        produces = "application/yaml")
    public String getNodeInstanceId(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant", required = true) String tenant,
        @PathVariable("nodeId") String nodeId, HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            result = cloudifyClient.getNodeInstanceId(deploymentId, nodeId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance-id with deploymentId " + deploymentId
                + " and nodeId " + nodeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstanceId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance-id with deploymentId " + deploymentId
                + " and nodeId " + nodeId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstanceId caught exception");
            result = new RestResponseError("getNodeInstanceId failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }

    @GetMapping(
        value = {DEPLOYMENTS_PATH + "/{deploymentId:.+}/revisions"},
        produces = "application/json")
    public String getDeploymentRevisions(@PathVariable("deploymentId") String deploymentId,
        @RequestParam(value = "tenant") String tenant, HttpServletRequest request)
        throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            result = cloudifyClient.getNodeInstanceVersion(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription",
                "Getting executions for deployment " + deploymentId + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger,
                "getExecutionByIdAndDeploymentId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
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
     * Gets the cloudify plugins list
     * 
     * 
     * @param request
     * HttpServletRequest
     * @return list of CloudifyPlugin
     * @throws Exception
     * on serialization failure
     */
    @SuppressWarnings("unchecked")
    @GetMapping(
        value = {PLUGINS_PATH},
        produces = "application/json")
    public String getPlugins(HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            List<CloudifyPlugin> resultsArr = cloudifyClient.getPlugins().items;
            return objectMapper.writeValueAsString(resultsArr);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting plugins failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getPlugins caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
            return objectMapper.writeValueAsString(result);
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting plugins failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getPlugins caught exception");
            result = new RestResponseError("getSecret failed", t);
            return objectMapper.writeValueAsString(result);
        } finally {
            postLogAudit(request);
        }
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
        MDC.put("TargetEntity", "Cloudify Manager");
        MDC.put("TargetServiceName", "Cloudify Manager");
        MDC.put(SystemProperties.AUDITLOG_END_TIMESTAMP, logDateFormat.format(end));
        MDC.put(SystemProperties.METRICSLOG_END_TIMESTAMP, logDateFormat.format(end));
        logger.info(EELFLoggerDelegate.auditLogger, request.getMethod() + request.getRequestURI());
        logger.info(EELFLoggerDelegate.metricsLogger,
            request.getMethod() + request.getRequestURI());
    }
}
