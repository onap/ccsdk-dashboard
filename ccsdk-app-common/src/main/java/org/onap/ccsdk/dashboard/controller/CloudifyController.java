/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2020 AT&T Intellectual Property. All rights reserved.
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    private static Date begin;
    private static Date end;
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
    
    private AbstractCacheManager cacheManager;   
    
    @Autowired
    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public AbstractCacheManager getCacheManager() {
        return cacheManager;
    }
    
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

    private void updateBpOwnerId(List<CloudifyDeployment> itemList,
        Map<String, List<CloudifyDeployedTenant>> ownerDepMap) {
        for (CloudifyDeployment srvc: (List<CloudifyDeployment>)itemList) {    
            for (Map.Entry<String, List<CloudifyDeployedTenant>> entry : 
                ownerDepMap.entrySet()) {
                List<CloudifyDeployedTenant> deplExtItems = 
                    entry.getValue().stream().filter((Predicate<? super CloudifyDeployedTenant>) s->s.id.equals(srvc.id)).
                    collect(Collectors.toList());
                if (deplExtItems != null && deplExtItems.size() > 0) {
                    srvc.owner = entry.getKey();
                    break;
                }
            }
        }
    }
    
    private List<CloudifyTenant> getMyTenants(HttpServletRequest request, 
        List<CloudifyTenant> cfyTenantList) {
        List<CloudifyTenant> itemList = new ArrayList<CloudifyTenant>();
        HttpSession session = AppUtils.getSession(request);
        String roleLevel = (String) session.getAttribute("role_level");
        if (roleLevel == null) {
            roleLevel = "dev";
        }
        Set<String> userApps = (Set<String>) session.getAttribute("authComponents");
        if (userApps == null) {
            userApps = new TreeSet<String>();
        }  
        Set filteredTenants = new HashSet<CloudifyTenant>();
        
        List dcaeTenants = (List) cfyTenantList.stream().filter
            (s -> ((CloudifyTenant)s).name.contains("DCAE-"))
            .collect(Collectors.toList());
            
        List cppTenants = (List) cfyTenantList.stream().filter
            (s -> ((CloudifyTenant)s).name.contains("DEAP")
                || ((CloudifyTenant)s).name.contains("CPP"))
            .collect(Collectors.toList());
        
        List appTenants = (List) cfyTenantList.stream().filter
            (s -> !(((CloudifyTenant)s).name.contains("DCAE-"))
                && !(((CloudifyTenant)s).name.contains("CPP"))
                && !(((CloudifyTenant)s).name.contains("DEAP"))
                && !(((CloudifyTenant)s).name.equals("default_tenant"))
                && !(((CloudifyTenant)s).name.contains("AAI")))
            .collect(Collectors.toList()); 
        
        Predicate<String> dcaeAppFilter =
            p -> p.contains("dcae");
        Predicate<String> d2aAppFilter =
                p -> p.contains("d2a"); 
                
        // apply role based filtering
        if (roleLevel.equals("app")) {
            List<String> myApps = new ArrayList(userApps);
            if (myApps.contains("dcae")) {
                filteredTenants.addAll(dcaeTenants);                      
                filteredTenants.addAll(appTenants);                
                myApps.removeIf(dcaeAppFilter);
            }
            if (myApps.contains("d2a")) {
                filteredTenants.addAll(cppTenants);
                myApps.removeIf(d2aAppFilter);
            }                   
            if (!myApps.isEmpty()) {
                filteredTenants.addAll(appTenants);
            }             
            itemList.addAll(filteredTenants);
        } else if (roleLevel.equals("app_dev")) {
            itemList.addAll(appTenants);
        } else {
            itemList.addAll(cfyTenantList);
        }       
        return itemList;
    }
    
    private void updateWorkflowHelmStatus(List<CloudifyDeployment> itemList, 
        List<CloudifyDeploymentExt> cfyDeplExt,
        List<CloudifyDeploymentHelm> cfyDeplHelm) {
        for (CloudifyDeployment srvc: (List<CloudifyDeployment>)itemList) {
            // correlate the cached data for deployments and deployment extensions
                List<CloudifyDeploymentExt> deplExtItems = 
                    cfyDeplExt.stream().filter((Predicate<? super CloudifyDeploymentExt>) s->s.id.equals(srvc.id)).
                    collect(Collectors.toList());
                if (deplExtItems != null && deplExtItems.size() > 0) {
                    srvc.lastExecution = deplExtItems.get(0).lastExecution;
                }
        }
        for (CloudifyDeployment srvc: (List<CloudifyDeployment>)itemList) {
            // correlate the cached data for deployments and deployment helm info
                List<CloudifyDeploymentHelm> deplHelmItems = 
                    cfyDeplHelm.stream().filter((Predicate<? super CloudifyDeploymentHelm>) s->s.id.equals(srvc.id)).
                    collect(Collectors.toList());
                if (deplHelmItems != null && deplHelmItems.size() > 0) {
                    srvc.helmStatus = deplHelmItems.get(0).helmStatus;
                    srvc.isHelm = deplHelmItems.get(0).isHelm;
                }
        }
    }
    
    private void updateHelmInfo(List<CloudifyDeployment> itemList, 
        List<CloudifyDeploymentExt> cfyDeplExt) {
        for (CloudifyDeployment srvc: (List<CloudifyDeployment>)itemList) {
            // correlate the cached data for deployments and deployment extensions
            List<CloudifyDeploymentExt> deplExtItems = 
                cfyDeplExt.stream().filter((Predicate<? super CloudifyDeploymentExt>) s->s.id.equals(srvc.id)).
                collect(Collectors.toList());
            srvc.helmStatus = deplExtItems.get(0).helmStatus;
            srvc.isHelm = deplExtItems.get(0).isHelm;
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
    private String getItemListForPage(HttpServletRequest request, long userId, 
        CloudifyDataItem option, int pageNum, int pageSize, String sortBy, String searchBy) 
            throws Exception {
    	HttpSession session = AppUtils.getSession(request);
        String user = UserUtils.getUserSession(request).getLoginId();
        HashMap<String, Boolean> comp_deploy_tab =
            (HashMap<String, Boolean>) session.getAttribute("comp_access");
        String roleLevel = (String) session.getAttribute("role_level");
        String roleAuth = (String) session.getAttribute("auth_role");
        if (roleAuth == null) {
            roleAuth = "READ";
        }
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
        new ArrayList<Service>();
        int totalItems = 0;
        int deplPgSize = 500;
        int deplPgOffset = 0;
        String tenantFilterStr = "";
        String cacheFilterStr = "true";
        String userFilterStr = "false";
        String svcRefFilterStr = "";
        String statusFilterStr = "";
        String helmFilterStr = "";
        String ownerFilterStr = "";
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<CloudifyDeploymentExt> cfyDeplExt = null;
        List<CloudifyDeploymentExt> cfyDepExList = new ArrayList<CloudifyDeploymentExt>();
        List<CloudifyDeploymentHelm> cfyDeplHelm = null;
        List<CloudifyDeploymentHelm> cfyDeplHelmList = new ArrayList<CloudifyDeploymentHelm>();
        boolean useCache = true;
        boolean userLevel = false;
        List<CloudifyTenant> cfyTenantList = null;
        // apply tenant search filter
        if (searchBy != null && !searchBy.isEmpty()) {
            // parse the search filters string  
            List<String> searchFilters = 
                new ArrayList<String>(Arrays.asList(searchBy.split(";")));
            if (searchFilters.stream().anyMatch(s->s.startsWith("tenant"))) {
                List<String> tenantsList = searchFilters.stream().filter(s->s.startsWith("tenant")).
                collect(Collectors.toList());
                if (tenantsList.get(0).split(":").length > 1) {
                    tenantFilterStr = tenantsList.get(0).split(":")[1];
                }
            }
            if (searchFilters.stream().anyMatch(s->s.startsWith("cache"))) {
                List<String> cacheStr = searchFilters.stream().filter(s->s.startsWith("cache")).
                collect(Collectors.toList());
                cacheFilterStr = cacheStr.get(0).split(":")[1];
                useCache = Boolean.parseBoolean(cacheFilterStr);
            }
            if (searchFilters.stream().anyMatch(s->s.startsWith("user"))) {
                List<String> userStr = searchFilters.stream().filter(s->s.startsWith("user")).
                collect(Collectors.toList());
                userFilterStr = userStr.get(0).split(":")[1];
                userLevel = Boolean.parseBoolean(userFilterStr);
            }
            if (searchFilters.stream().anyMatch(s->s.startsWith("owner"))) {
                List<String> ownerList = searchFilters.stream().filter(s->s.startsWith("owner")).
                    collect(Collectors.toList());
                ownerFilterStr = ownerList.get(0).split(":")[1];
            }
        }
        lock.readLock().lock();
        Map<String, List<CloudifyDeployedTenant>> deplPerOwner = 
            (Map<String, List<CloudifyDeployedTenant>>) getCacheManager().getObject("owner_deploy_map");
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
                RestResponsePage<List> model = 
                    new RestResponsePage<>(totalItems, 1, items);
                String outboundJson = objectMapper.writeValueAsString(model);
                return outboundJson;
            case SERVICE_ID: 
                final String svcIdTenant = tenantFilterStr;
                if (useCache) {
                    lock.readLock().lock();
                    itemList = 
                        (List<CloudifyDeployment>)getCacheManager().getObject(SERVICE_ID + ":" + svcIdTenant);
                    lock.readLock().unlock();
                }
                if (itemList == null) {
                    itemList = cloudifyClient.getDeployments(tenantFilterStr, deplPgSize, deplPgOffset, true);
                }
                Set<String> svcIdList = new HashSet<String>();
                if (itemList != null) {
                    svcIdList = 
                    (Set) itemList.stream().map(x -> ((CloudifyDeployment)x).id).collect(Collectors.toSet());
                    // apply role based filtering
                    if (roleLevel.equals("app")) {
                        @SuppressWarnings("unchecked")
                        List<String> myApps = new ArrayList(userApps);
                        svcIdList = svcIdList.stream().filter(s -> myApps.stream()
                            .anyMatch(roleFilter -> ((String)s).toLowerCase().startsWith(roleFilter)))
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
                        cfyDepList = new ArrayList<CloudifyDeployment>();
                        if (useCache) {
                            lock.readLock().lock();
                            cfyTenantList = (List<CloudifyTenant>)getCacheManager().getObject(TENANTS_PATH);
                            for (CloudifyTenant cfyTenObj: cfyTenantList) {
                                cfyDepPerTntList = (List<CloudifyDeployment>)getCacheManager().getObject(SERVICE_ID + ":" + cfyTenObj.name);
                                cfyDepList.addAll(cfyDepPerTntList);
                            }
                            lock.readLock().unlock();
                        } else {
                            cfyTenantList = (List<CloudifyTenant>)cloudifyClient.getTenants().items;
                            for (CloudifyTenant cfyTenObj: cfyTenantList) {
                                cfyDepPerTntList = cloudifyClient.getDeployments(cfyTenObj.name, deplPgSize, deplPgOffset, true, false);
                                cfyDepList.addAll(cfyDepPerTntList);
                            }
                        } 
                    } else {
                        if (useCache) {
                            lock.readLock().lock();
                            cfyDepList = (List<CloudifyDeployment>)getCacheManager().getObject(SERVICE_ID + ":" + tenantFilterStr);
                            lock.readLock().unlock();
                        }
                        if (cfyDepList == null) {
                            cfyDepList = cloudifyClient.getDeployments(tenantFilterStr, deplPgSize, deplPgOffset, true, false);                         
                        }
                    }
                    totalItems = cfyDepList.size();
                    
                    if (useCache) {
                        lock.readLock().lock();
                        cfyDeplExt = 
                            (List<CloudifyDeploymentExt>)getCacheManager().getObject(SERVICE_ID + ":" + tenantFilterStr + ":ext");
                        if (cfyDeplExt != null) {
                            cfyDepExList.addAll(cfyDeplExt);
                        }
                        cfyDeplHelm = 
                            (List<CloudifyDeploymentHelm>)getCacheManager().getObject(SERVICE_ID + ":" + tenantFilterStr + ":helm");
                        if (cfyDeplHelm != null) {
                            cfyDeplHelmList.addAll(cfyDeplHelm);
                        }
                        lock.readLock().unlock();                    
                    }
                    
                    if (roleLevel.equals("app") && !userLevel) {
                        @SuppressWarnings("unchecked")
                        List<String> myApps = new ArrayList(userApps);
                        cfyDepList = (List) cfyDepList.stream().filter(s -> myApps.stream()
                            .anyMatch(roleFilter -> ((CloudifyDeployment)s).id.toLowerCase().startsWith(roleFilter)))
                            .collect(Collectors.toList());         
                    }                 
                    // apply user search filters
                    if (searchBy != null && !searchBy.isEmpty()) {
                        // parse the search filters string 
                        // look for service name patterns                  
                        List<String> searchFilters = 
                            new ArrayList<String>(Arrays.asList(searchBy.split(";")));
                        if (searchFilters.stream().anyMatch(s->s.startsWith("serviceRef"))) {
                            List<String> svcRefsList = searchFilters.stream().filter(s->s.startsWith("serviceRef")).
                            collect(Collectors.toList());
                            svcRefFilterStr = svcRefsList.get(0).split(":")[1];
                        }
                        if (searchFilters.stream().anyMatch(s->s.startsWith("status"))) {
                            List<String> statusList = searchFilters.stream().filter(s->s.startsWith("status")).
                            collect(Collectors.toList());
                            statusFilterStr = statusList.get(0).split(":")[1];
                        }
                        if (searchFilters.stream().anyMatch(s->s.startsWith("helm"))) {
                            List<String> helmList = searchFilters.stream().filter(s->s.startsWith("helm")).
                            collect(Collectors.toList());
                            helmFilterStr = helmList.get(0).split(":")[1];
                        }          
                        if (!ownerFilterStr.isEmpty()) {
                            List ownerFilterList = 
                                    new ArrayList<String>(Arrays.asList(ownerFilterStr.split(",")));                
                            if (ownerFilterList.size() == 1 && ownerFilterList.get(0).equals("undefined")) {
                                ownerFilterList.clear();
                                ownerFilterList.add(user);
                            }
                            if (deplPerOwner != null && cfyDepList != null) {
                                List<CloudifyDeployedTenant> ownerDeplList = new ArrayList<CloudifyDeployedTenant>();
                                Stream<Map.Entry<String, List<CloudifyDeployedTenant>>> deplOwnerEntriesStream = 
                                    deplPerOwner.entrySet().stream(); 
                                Set<Map.Entry<String, List<CloudifyDeployedTenant>>> deplOwnerSet = 
                                    deplOwnerEntriesStream.filter(m -> ownerFilterList.stream().
                                    anyMatch(ownFilter -> m.getKey().equalsIgnoreCase((String) ownFilter))).collect(Collectors.toSet());
                                deplOwnerSet.stream().forEach(e -> ownerDeplList.addAll(e.getValue()));
                                if (ownerDeplList.size() > 0) {
                                    Predicate<CloudifyDeployment> In2 = 
                                        s -> ownerDeplList.stream().anyMatch(mc -> s.id.equals(mc.id));
                                    cfyDepList = cfyDepList.stream().filter(In2).collect(Collectors.toList());
                                } else {
                                    cfyDepList = new ArrayList<CloudifyDeployment>();
                                }
                            }
                        }
                        if (!svcRefFilterStr.isEmpty()) {
                            List<String> svcFilterList = 
                                new ArrayList<String>(Arrays.asList(svcRefFilterStr.split(",")));
                            if (!svcFilterList.isEmpty()) {
                                cfyDepList =  (List) cfyDepList.stream().filter(s -> svcFilterList.stream()
                                    .anyMatch(svcFilter -> ((CloudifyDeployment) s).id.toLowerCase().contains(svcFilter.toLowerCase())))
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
                                cfyDepList =  svcStream.filter( srvcFilter.and(
                                    s -> statusFilterList.stream()
                                    .anyMatch(statusFilter ->((CloudifyDeployment) s).lastExecution.status.equalsIgnoreCase(statusFilter))))
                                    .collect(Collectors.toList()); 
                            }
                        }                      
                        if (!helmFilterStr.isEmpty()) {
                            Predicate<CloudifyDeployment> helmFilter =
                                p -> p.helmStatus != null;
                            boolean isHelm = Boolean.parseBoolean(helmFilterStr);
                            if (cfyDeplHelmList == null || cfyDeplHelmList.isEmpty()) {
                                cfyDeplHelmList = cloudifyClient.updateHelmInfo(cfyDepList);
                            }
                            if (cfyDeplHelmList != null && isHelm ) {
                                updateWorkflowHelmStatus(cfyDepList, cfyDepExList, cfyDeplHelmList);
                                cfyDepList =  (List) cfyDepList.stream().filter(helmFilter.and(
                                    s -> ((CloudifyDeployment) s).isHelm == true))
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
                        ((List<CloudifyDeployment>)itemList).sort(
                            (CloudifyDeployment o1, CloudifyDeployment o2) -> o1.id.compareTo(o2.id));
                    } else if (sortBy.equals("blueprint_id")) {
                        ((List<CloudifyDeployment>)itemList).sort(
                            (CloudifyDeployment o1, CloudifyDeployment o2) -> o1.blueprint_id.compareTo(o2.blueprint_id));
                    } else if (sortBy.equals("created_at")) {
                        ((List<CloudifyDeployment>)itemList).sort(
                            (CloudifyDeployment o1, CloudifyDeployment o2) -> o1.created_at.compareTo(o2.created_at));
                    } else if (sortBy.equals("updated_at")) {
                        ((List<CloudifyDeployment>)itemList).sort(
                            (CloudifyDeployment o1, CloudifyDeployment o2) -> o1.updated_at.compareTo(o2.updated_at));
                    }
                }
                break;          
            case TENANT:
                lock.readLock().lock();
                cfyTenantList = (List<CloudifyTenant>)getCacheManager().getObject(TENANTS_PATH);
                lock.readLock().unlock();
                if (cfyTenantList == null || cfyTenantList.isEmpty()) {
                    cfyTenantList = (List<CloudifyTenant>)cloudifyClient.getTenants().items;
                }
                
                itemList = getMyTenants(request, cfyTenantList);
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
        if (itemList != null) {
            totalItems = itemList.size();
        }
        final int pageCount = (int) Math.ceil((double) totalItems / pageSize);
        if (totalItems > pageSize) {
            itemList = getPageOfList(pageNum, pageSize, itemList);
        }

        if (option.equals(CloudifyDataItem.DEPLOYMENT)) {
            // update blueprint owner for each deployment record
            if (deplPerOwner != null ) {
                updateBpOwnerId(itemList, deplPerOwner);
            }
            if (cfyDepExList == null || cfyDepExList.isEmpty()) {
                cfyDepExList = cloudifyClient.updateWorkflowStatus(itemList);
            }
            if (cfyDeplHelmList == null || cfyDeplHelmList.isEmpty()) {
                cfyDeplHelmList = cloudifyClient.updateHelmInfo(itemList);
            }
            for (CloudifyDeployment srvc: (List<CloudifyDeployment>)itemList) {
                try {
                    ConsulDeploymentHealth serviceHealth = 
                        consulClient.getServiceHealthByDeploymentId(srvc.id);
                    if (serviceHealth != null) {
                        srvc.healthStatus = serviceHealth.getStatus();
                    }
                } catch (Exception e) {
                    logger.error(EELFLoggerDelegate.errorLogger, "consul getServiceHealth caught exception");
                }
                // correlate the cached data for deployments and deployment extensions               
                if (cfyDepExList != null) {
                    List<CloudifyDeploymentExt> deplExtItems = 
                        cfyDepExList.stream().filter((Predicate<? super CloudifyDeploymentExt>) s->s.id.equals(srvc.id)).
                        collect(Collectors.toList());
                    if (deplExtItems != null && !deplExtItems.isEmpty()) {
                        srvc.lastExecution = deplExtItems.get(0).lastExecution;
                    }
                }
                if (cfyDeplHelmList != null) {
                    List<CloudifyDeploymentHelm> deplHelmItems = 
                        cfyDeplHelmList.stream().filter((Predicate<? super CloudifyDeploymentHelm>) s->s.id.equals(srvc.id)).
                        collect(Collectors.toList());
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
                        if (comp_deploy_tab.containsKey(deplRef)) {
                            boolean enableDeploy = comp_deploy_tab.get(deplRef);
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
     * @param option  Item type to get
     * @param inputKey Input key to search for if using filters
     * @param inputValue Input value to search for if using filters
     * @return JSON with one page of objects; or an error.
     */
    protected String getItemListForPageWrapper(HttpServletRequest request, CloudifyDataItem option,
        String sortBy, String searchBy) {
        String outboundJson = null;
        try {
            User appUser = UserUtils.getUserSession(request);
            if (appUser == null || appUser.getLoginId() == null || appUser.getLoginId().length() == 0)
                throw new Exception("getItemListForPageWrapper: Failed to get application user");
            int pageNum = getRequestPageNumber(request);
            int pageSize = getRequestPageSize(request);
            outboundJson = getItemListForPage(request, appUser.getId(), option, pageNum, pageSize,
                sortBy, searchBy);
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
     * Serves one page of deployments
     * 
     * @param request HttpServletRequest
     * @return List of CloudifyDeployment objects
     */
      @RequestMapping(value = { DEPLOYMENTS_PATH }, method = RequestMethod.GET,
      produces = "application/json")     
      @ResponseBody 
      public String getDeploymentsByPage(HttpServletRequest request)
      { 
          preLogAudit(request); 
          String json = getItemListForPageWrapper(request,
          CloudifyDataItem.DEPLOYMENT,request.getParameter("sortBy"), request.getParameter("searchBy")); 
          postLogAudit(request); 
          return json; 
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
              BLUEPRINTS_PATH + "/{id:.+}" + "/archive" }, method = RequestMethod.GET, produces = "application/yaml")
      @ResponseBody
      public byte[] viewBlueprintContentById(@PathVariable("id") String id, @RequestParam(value = "tenant", required = true) String tenant, HttpServletRequest request) throws Exception {
          preLogAudit(request);
          byte[] result = null;
          try {
              result = cloudifyClient.viewBlueprint(tenant, id);
          } catch (HttpStatusCodeException e) {
              MDC.put(SystemProperties.STATUS_CODE, "ERROR");
              MDC.put("TargetEntity", "Cloudify Manager");
              MDC.put("TargetServiceName", "Cloudify Manager");
              MDC.put("ErrorCode", "300");
              MDC.put("ErrorCategory", "ERROR");
              MDC.put("ErrorDescription", "Viewing blueprint " + id + " failed!");
              logger.error(EELFLoggerDelegate.errorLogger, "viewBlueprintContentById caught exception");
              //result = new RestResponseError(e.getResponseBodyAsString());
          } catch (Throwable t) {
              MDC.put(SystemProperties.STATUS_CODE, "ERROR");
              MDC.put("TargetEntity", "Cloudify Manager");
              MDC.put("TargetServiceName", "Cloudify Manager");
              MDC.put("ErrorCode", "300");
              MDC.put("ErrorCategory", "ERROR");
              MDC.put("ErrorDescription", "Viewing blueprint " + id + " failed!");
              logger.error(EELFLoggerDelegate.errorLogger, "viewBlueprintContentById caught exception");
              //result = new RestResponseError("getBlueprintContentById failed", t);
          } finally {
              postLogAudit(request);
          }
          return result;
              //objectMapper.writeValueAsString(result);
      }

      /**
       * Serves the complete list of deployment IDs
       * 
       * @param request HttpServletRequest
       * 
       * @return list of cloudify deployment IDs
       */
      @RequestMapping(
          value = {SERVICE_ID},
          method = RequestMethod.GET,
          produces = "application/json")
      @ResponseBody
      public String getAllServiceNames(HttpServletRequest request) {
          preLogAudit(request);
          String json = null;
          json = getItemListForPageWrapper(request, CloudifyDataItem.SERVICE_ID,
              request.getParameter("sortBy"), request.getParameter("searchBy"));
          postLogAudit(request);
          return json;
      }  
      
      /**
       * Serves the count of deployment IDs
       * 
       * @param request HttpServletRequest
       * 
       * @return list of cloudify deployment IDs
       */
      @RequestMapping(
          value = {SERVICE_ID_COUNT},
          method = RequestMethod.GET,
          produces = "application/json")
      @ResponseBody
      public String getDepCount(HttpServletRequest request) {
          preLogAudit(request);
          String json = null;
          json = getItemListForPageWrapper(request, CloudifyDataItem.SERVICE_ID_COUNT,
              request.getParameter("sortBy"), request.getParameter("searchBy"));
          postLogAudit(request);
          return json;
      } 
      
      /**
       * Serves the count of plugins
       * 
       * @param request HttpServletRequest
       * 
       * @return count of cloudify plugins
     * @throws JsonProcessingException 
       */
      @RequestMapping(
          value = {PLUGIN_COUNT},
          method = RequestMethod.GET,
          produces = "application/json")
      @ResponseBody
      public String getPluginCount(HttpServletRequest request) 
          throws JsonProcessingException {
          preLogAudit(request);
          ECTransportModel result = null;        
          try {
              int totalItems = 
                  (int) cloudifyClient.getPlugins().metadata.pagination.total;              
              RestResponsePage<List> model = 
                  new RestResponsePage<>(totalItems, 1, null);
              String outboundJson = objectMapper.writeValueAsString(model);
              return outboundJson;
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
          } catch (Throwable t) {
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
        String json = getItemListForPageWrapper(request, CloudifyDataItem.TENANT,
            request.getParameter("sortBy"), request.getParameter("searchBy"));
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
    @RequestMapping(value = { BLUEPRINTS_PATH + "/{id:.+}" }, method = RequestMethod.GET, produces = "application/json")
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
     * Deletes the specified blueprint.
     * 
     * @param id       Blueprint ID
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return No content on success; error on failure.
     * @throws Exception On serialization failure
     */
      @RequestMapping(value = { BLUEPRINTS_PATH + "/{id}" }, method = RequestMethod.DELETE, produces = "application/json")   
      @ResponseBody 
      public String deleteBlueprint(@PathVariable("id") String id,
          @RequestParam(value = "tenant", required = false) String tenant, 
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        preLogAudit(request);
        String json = "{\"202\": \"OK\"}";
        try {
            cloudifyClient.deleteBlueprint(id, tenant);
            response.setStatus(202);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting blueprint " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper.writeValueAsString(new RestResponseError(e.getMessage()));
        } catch (Throwable t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Deleting blueprint " + id + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "deleteBlueprint caught exception");
            json = objectMapper
                .writeValueAsString(new RestResponseError("deleteBlueprint failed on ID " + id, t));
        } finally {
            postLogAudit(request);
        }
        return json;
    }
     
    /**
     * 
     * Deployment IDs for the given list of blueprint IDs
     * 
     */
      @RequestMapping(
          value = {"deployment_blueprint_map"},
          method = RequestMethod.POST,
          produces = "application/json")
      @ResponseBody
      public String getDeploymentsForType(HttpServletRequest request, @RequestBody String[] typeList)
          throws Exception {
          List<ServiceTypeServiceMap> result = new ArrayList<ServiceTypeServiceMap>();
          for (String typeId : typeList) { 
              List<CloudifyDeployedTenant> deplForBpAggr = new ArrayList<CloudifyDeployedTenant>();             
              List<CloudifyDeployedTenant> deplForBp = 
                  cloudifyClient.getDeploymentForBlueprint("TID-"+typeId);
              deplForBpAggr.addAll(deplForBp);
              
              ServiceQueryParams qryParams = 
                  new ServiceQueryParams.Builder().typeId(typeId).build();
              ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);            
              Set<String> dep_ids = srvcRefs.items.stream().map(x -> ((ServiceRef)x).id).collect(Collectors.toSet());  
              if (dep_ids.size() > 0) {
                  // lookup these dep_ids in cloudify for tenant mapping                 
                  for (String str: dep_ids) {
                      List<CloudifyDeployedTenant> deplForBpInv = 
                          cloudifyClient.getDeploymentForBlueprint(str);
                      deplForBpAggr.addAll(deplForBpInv);
                  }
              } 
              ServiceRefCfyList cfyDeplRefList = new ServiceRefCfyList(deplForBpAggr, deplForBpAggr.size());
              ServiceTypeServiceMap srvcMap = new ServiceTypeServiceMap(typeId, cfyDeplRefList);
              result.add(srvcMap);
          }
          String resultStr = objectMapper.writeValueAsString(result);
          return resultStr;
      }

      @SuppressWarnings("unchecked")
      @Scheduled(fixedDelay=3600000, initialDelay=180000)
      public void cacheOwnerDeployMap() {
          logger.debug(EELFLoggerDelegate.debugLogger, "cacheOwnerDeployMap begin");
          Map<String, List<CloudifyDeployedTenant>> deplPerOwner = 
              new HashMap<String, List<CloudifyDeployedTenant>>();
          new HashMap<String, List<ServiceTypeSummary>>();
          List<ServiceTypeSummary> bpNoDeplItems = 
              new ArrayList<ServiceTypeSummary>();
          List<ServiceTypeServiceMap> result = new ArrayList<ServiceTypeServiceMap>();
          List<CloudifyDeployedTenant> deplForBpAggr = 
              new ArrayList<CloudifyDeployedTenant>();
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
                          cloudifyClient.getDeploymentForBlueprint("TID-"+typeId);
                      deplForBpAggr.clear();
                      deplForBpAggr.addAll(deplForBp);
                      ServiceQueryParams qryParams = 
                          new ServiceQueryParams.Builder().typeId(typeId).build();
                      ServiceRefList srvcRefs = inventoryClient.getServicesForType(qryParams);            
                      Set<String> dep_ids = srvcRefs.items.stream().map(x -> ((ServiceRef)x).id).collect(Collectors.toSet());  
                      if (dep_ids.size() > 0) {                
                          for (String str: dep_ids) {
                              List<CloudifyDeployedTenant> deplForBpInv = 
                                  cloudifyClient.getDeploymentForBlueprint(str);
                              deplForBpAggr.addAll(deplForBpInv);
                          }
                      }                  
                  } catch (Exception e) {
                      logger.error(EELFLoggerDelegate.errorLogger, "cacheOwnerDeployMap: " + e.getMessage());
                  } finally {
                      if (deplForBpAggr.isEmpty()) {
                          bpNoDeplItems.add(item);
                      }
                      List<CloudifyDeployedTenant> iterBpIdDepl = 
                          new ArrayList<CloudifyDeployedTenant>();
                      iterBpIdDepl.addAll(deplForBpAggr);
                      ServiceRefCfyList cfyDeplRefList = new ServiceRefCfyList(iterBpIdDepl, iterBpIdDepl.size());
                      ServiceTypeServiceMap srvcMap = new ServiceTypeServiceMap(typeId, cfyDeplRefList);
                      result.add(srvcMap); 
                      
                      List<CloudifyDeployedTenant> iterDeplBpAggr = 
                          new ArrayList<CloudifyDeployedTenant>();
                      iterDeplBpAggr.addAll(deplForBpAggr);
                      if (deplPerOwner.containsKey(owner)) {
                          List<CloudifyDeployedTenant> currOwnerDepl = 
                              deplPerOwner.get(owner);
                          iterDeplBpAggr.addAll(0, currOwnerDepl);
                          deplPerOwner.put(owner, iterDeplBpAggr);
                      }  else {
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
     * @param id      Deployment ID
     * @param request HttpServletRequest
     * @return Deployment for the specified ID; error on failure.
     * @throws Exception On serialization failure
     * 
     */
    @RequestMapping(value = { DEPLOYMENTS_PATH + "/{id:.+}" }, method = RequestMethod.GET, produces = "application/json")
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
        @RequestParam(value = "deployment_id", required = true) String deployment_id,
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
                CloudifyDeploymentList depList = cloudifyClient.getDeployments(tenant, 100, 0);
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

    @SuppressWarnings("unchecked")
    @RequestMapping(value = { EXECUTIONS_PATH + "/tenant" }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getExecutionsPerTenant(HttpServletRequest request,
        @RequestParam(value = "tenant", required = true) String tenant,
        @RequestParam(value = "status", required = false) String status) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<CloudifyTenant> cfyTenantList = null;
        List<CloudifyTenant> myTenantsList = null;
        try {
            List<CloudifyExecution> itemList = new ArrayList<CloudifyExecution>();
            if (tenant == null) {
                // process all tenants that are relevant
                lock.readLock().lock();
                cfyTenantList = (List<CloudifyTenant>)getCacheManager().getObject(TENANTS_PATH);
                lock.readLock().unlock();
                if (cfyTenantList == null || cfyTenantList.isEmpty()) {
                    cfyTenantList = (List<CloudifyTenant>)cloudifyClient.getTenants().items;
                }               
                myTenantsList = getMyTenants(request, cfyTenantList);
                
                for (CloudifyTenant tenItem : myTenantsList) {
                    CloudifyExecutionList exeList = 
                        cloudifyClient.getExecutionsSummaryPerTenant(tenItem.name);
                    // Filter down to specified status as needed
                    if (status != null && !status.isEmpty()) {
                        Iterator<CloudifyExecution> exeIter = exeList.items.iterator();
                        while (exeIter.hasNext()) {
                            CloudifyExecution ce = exeIter.next();
                            if (!status.equals(ce.status))
                                exeIter.remove();
                        }
                    }
                    itemList.addAll(exeList.items);
                }
            } else {
                CloudifyExecutionList exeList = cloudifyClient.getExecutionsSummaryPerTenant(tenant);
                itemList.addAll(exeList.items);

                // Filter down to specified status as needed
                if (status != null && !status.isEmpty()) {
                    Iterator<CloudifyExecution> exeIter = itemList.iterator();
                    while (exeIter.hasNext()) {
                        CloudifyExecution ce = exeIter.next();
                        if (!status.equals(ce.status))
                            exeIter.remove();
                    }
                }  
            }
            //Collections.sort(itemList, executionComparator);

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

    @SuppressWarnings("unchecked")
    @RequestMapping(value = { EXECUTIONS_PATH + "/{id:.+}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String queryExecution(@PathVariable("id") String id, @RequestParam(value = "tenant", required = true) 
        String tenant, HttpServletRequest request) throws Exception { 
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            List<CloudifyExecution> itemList = new ArrayList<CloudifyExecution>();
            CloudifyExecution cfyExecObj = cloudifyClient.getExecutionIdSummary(id, tenant);
            itemList.add(cfyExecObj);
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
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { EXECUTIONS_PATH + "/active"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getActiveExecutions(HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<CloudifyTenant> cfyTenantList = null;
        List<CloudifyTenant> myTenantsList = null;
        String status = "started";
        try {
            List<CloudifyExecution> itemList = new ArrayList<CloudifyExecution>();
            // process all tenants that are relevant
            lock.readLock().lock();
            cfyTenantList = (List<CloudifyTenant>)getCacheManager().getObject(TENANTS_PATH);
            lock.readLock().unlock();
            if (cfyTenantList == null || cfyTenantList.isEmpty()) {
                cfyTenantList = (List<CloudifyTenant>)cloudifyClient.getTenants().items;
            }               
            myTenantsList = getMyTenants(request, cfyTenantList);
            
            for (CloudifyTenant tenItem : myTenantsList) {
                CloudifyExecutionList exeList = null;
                try {
                    exeList = 
                    cloudifyClient.getExecutionsSummaryPerTenant(tenItem.name);
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
            //Collections.sort(itemList, executionComparator);

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
            if (!(execution.workflow_id.equals("status") || execution.workflow_id.equals("execute_operation"))
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
                String repo_user = 
                cloudifyClient.getSecret("controller_helm_user", execution.getTenant()).value;
                String repo_user_password = 
                cloudifyClient.getSecret("controller_helm_password", execution.getTenant()).value;
                execution.getParameters().put("repo_user", repo_user);
                execution.getParameters().put("repo_user_password", repo_user_password);
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
     * Gets the specified node-instances details
     * 
     * @query param deployment      deployment ID
     * @query param tenant      tenant name
     * @param request HttpServletRequest
     * @return node instances as a string; or error.
     * @throws Exception on serialization error
     * 
     */
    @RequestMapping(value = {"node-instances-data"},
            method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getNodeInstanceDetails(
            @RequestParam(value = "deployment", required = true) String deployment,
            @RequestParam(value = "tenant", required = true) String tenant,
            HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            result = cloudifyClient.getNodeInstanceDetails(deployment, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance with deploymentId " + deployment 
                    + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstance caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance with deploymentId " + deployment 
                    + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstance caught exception");
            result = new RestResponseError("getNodeInstance failed", t);
        } finally {
            postLogAudit(request);
        }
        return objectMapper.writeValueAsString(result);
    }
    
    /**
     * Gets the specified node-instances for viewing.
     * 
     * @param id      deployment ID
     * @param request HttpServletRequest
     * @return node instances as a string; or error.
     * @throws Exception on serialization error
     * 
     */
    @RequestMapping(value = { NODE_INSTANCES_PATH
            + "/{deploymentId:.+}" }, method = RequestMethod.GET, produces = "application/yaml")
    @ResponseBody
    public String getNodeInstances(@PathVariable("deploymentId") String deploymentId,
            @RequestParam(value = "tenant", required = true) String tenant,
            HttpServletRequest request) throws Exception {
        preLogAudit(request);
        ECTransportModel result = null;
        try {
            if (tenant == null) {
                throw new Exception("required tenant input missing");
            }
            result = cloudifyClient.getNodeInstances(deploymentId, tenant);
        } catch (HttpStatusCodeException e) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance-id with deploymentId " + deploymentId 
                    + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstanceId caught exception");
            result = new RestResponseError(e.getResponseBodyAsString());
        } catch (Exception t) {
            MDC.put(SystemProperties.STATUS_CODE, "ERROR");
            MDC.put("TargetEntity", "Cloudify Manager");
            MDC.put("TargetServiceName", "Cloudify Manager");
            MDC.put("ErrorCode", "300");
            MDC.put("ErrorCategory", "ERROR");
            MDC.put("ErrorDescription", "Getting node-instance-id with deploymentId " + deploymentId 
                    + " failed!");
            logger.error(EELFLoggerDelegate.errorLogger, "getNodeInstanceId caught exception");
            result = new RestResponseError("getNodeInstanceId failed", t);
        } finally {
            postLogAudit(request);
        }
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
            + "/{deploymentId:.+}/{nodeId}" }, method = RequestMethod.GET, produces = "application/yaml")
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
            DEPLOYMENTS_PATH + "/{deploymentId:.+}/revisions" }, method = RequestMethod.GET, produces = "application/json")
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

    /**
     * Gets the cloudify plugins list
     * 
     * 
     * @param request
     *            HttpServletRequest
     * @return list of CloudifyPlugin
     * @throws Exception
     *             on serialization failure
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { PLUGINS_PATH }, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlugins( 
            HttpServletRequest request) throws Exception {
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
        } catch (Throwable t) {
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
