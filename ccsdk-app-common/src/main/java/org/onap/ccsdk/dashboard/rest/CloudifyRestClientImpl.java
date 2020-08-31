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

package org.onap.ccsdk.dashboard.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.onap.ccsdk.dashboard.exceptions.DashboardControllerException;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprint;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentExt;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentHelm;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyPluginList;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyTenantList;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.onap.portalsdk.core.web.support.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpStatusCodeException;

@org.springframework.stereotype.Service
public class CloudifyRestClientImpl extends RestClientBase implements CloudifyClient {

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(CloudifyRestClientImpl.class);
    private String baseUrl;
    private static final String BLUEPRINTS = "blueprints";
    private static final String DEPLOYMENTS = "deployments";
    private static final String EXECUTIONS = "executions";
    private static final String TENANTS = "tenants";
    private static final String NODES = "nodes";
    private static final String NODE_INSTANCES = "node-instances";
    private static final String UPDATE_DEPLOYMENT = "update-deployment";
    private static final String EVENTS = "events";
    private static final String SECRETS = "secrets";
    private static final String SERVICE_ID = "service-list";
    private static final String PLUGINS = "plugins";

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

    @PostConstruct
    public void init() throws DashboardControllerException {
        String webapiUrl = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_URL);
        if (webapiUrl == null) {
            throw new IllegalArgumentException("Null URL not permitted");
        }
        String user = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_USERNAME);
        String pass = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_PASS);
        URL url = null;
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new DashboardControllerException("Failed to parse URL", ex);
        }
        String urlScheme = webapiUrl.split(":")[0];
        if (restTemplate == null) {
            createRestTemplate(url, user, pass, urlScheme);
        }
    }

    @SuppressWarnings("unchecked")
    public List<CloudifyDeploymentHelm> updateHelmInfo(List<CloudifyDeployment> itemList) {
        boolean isHelm = false;
        boolean helmStatus = false;
        List<CloudifyDeploymentHelm> result = new ArrayList<>();
        for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
            try {
                isHelm = false;
                helmStatus = false;
                CloudifyBlueprintList bpList =
                    this.getBlueprint(srvc.blueprint_id, srvc.tenant_name);
                Map<String, Object> bpPlan = bpList.items.get(0).plan;
                Map<String, String> workflows = (Map<String, String>) bpPlan.get("workflows");
                Map<String, String> pluginInfo =
                    ((List<Map<String, String>>) bpPlan.get("deployment_plugins_to_install"))
                        .get(0);
                if (pluginInfo.get("name").equals("helm-plugin")) {
                    isHelm = true;
                }
                if (workflows.containsKey("status")) {
                    helmStatus = true;
                }
                CloudifyDeploymentHelm cfyDeplHelm =
                    new CloudifyDeploymentHelm(srvc.id, isHelm, helmStatus);
                result.add(cfyDeplHelm);
            } catch (Exception e) {
                logger.error(EELFLoggerDelegate.errorLogger, "getBlueprint failed");
                CloudifyDeploymentHelm cfyDeplHelm =
                    new CloudifyDeploymentHelm(srvc.id, false, false);
                result.add(cfyDeplHelm);
                continue;
            }
        }
        return result;
    }

    public List<CloudifyDeploymentExt> updateWorkflowStatus(List<CloudifyDeployment> itemList) {
        List<CloudifyDeploymentExt> result = new ArrayList<>();
        for (CloudifyDeployment srvc : (List<CloudifyDeployment>) itemList) {
            try {
                // find deployment execution info per item
                CloudifyExecutionList execResults =
                    this.getExecutionsSummary(srvc.id, srvc.tenant_name);
                if (execResults.items != null && !execResults.items.isEmpty()) {
                    CloudifyDeploymentExt cfyDeplExt =
                        new CloudifyDeploymentExt(srvc.id, srvc.blueprint_id, srvc.tenant_name);
                    cfyDeplExt.lastExecution = execResults.items.get(0);
                    result.add(cfyDeplExt);
                }
            } catch (Exception e) {
                logger.error(EELFLoggerDelegate.errorLogger, "getExecutionsSummary failed");
                srvc.lastExecution = null;
                continue;
            }
        }
        return result;
    }

    public List<CloudifyDeployedTenant> getDeploymentForBlueprint(final String bpId) {
        String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, new String[] {"blueprint_id",
            bpId, "_all_tenants", "true", "_include", "id,created_at,updated_at,tenant_name"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeploymentForBlueprint begin: url {}",
            url);
        ResponseEntity<CloudifyDeployedTenantList> response = restTemplate.exchange(url,
            HttpMethod.GET, null, new ParameterizedTypeReference<CloudifyDeployedTenantList>() {});
        return response.getBody().items;
    }

    public CloudifyDeployment getDeploymentResource(final String id, final String tenant) {
        String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
            new String[] {"id", id, "_include",
                "id,blueprint_id,created_at,updated_at,created_by,description,tenant_name", "_sort",
                "-updated_at", "_sort", "-created_at"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeploymentResource begin: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyDeploymentList>() {});
        return response.getBody().items.get(0);
    }

    @Scheduled(fixedRate = 86400000, initialDelay = 15000)
    public void cacheTenants() {
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheTenants begin");
        CloudifyTenantList tenantsList = this.getTenants();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        getCacheManager().putObject(TENANTS, tenantsList.items);
        lock.writeLock().unlock();
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheTenants end");
    }

    @SuppressWarnings("unchecked")
    @Scheduled(fixedDelay = 3600000, initialDelay = 360000)
    public void cacheDeploymentExecInfo() {
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheDeploymentExecInfo begin");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        List<CloudifyTenant> tenantItems =
            (List<CloudifyTenant>) getCacheManager().getObject(TENANTS);
        lock.readLock().unlock();
        String cfyTen = "";
        if (tenantItems != null) {
            for (CloudifyTenant item : tenantItems) {
                cfyTen = item.name;
                lock.readLock().lock();
                List<CloudifyDeployment> cfyDeplList = (List<CloudifyDeployment>) getCacheManager()
                    .getObject(SERVICE_ID + ":" + cfyTen);
                lock.readLock().unlock();
                if (cfyDeplList != null) {
                    List<CloudifyDeploymentExt> cfyDeplExecList =
                        this.updateWorkflowStatus(cfyDeplList);
                    lock.writeLock().lock();
                    getCacheManager().putObject(SERVICE_ID + ":" + cfyTen + ":ext",
                        cfyDeplExecList);
                    lock.writeLock().unlock();
                }
            }
        }
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheDeploymentExecInfo end");
    }

    @SuppressWarnings("unchecked")
    @Scheduled(fixedDelay = 3900000, initialDelay = 600000)
    public void cacheDeploymentHelmInfo() {
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheDeploymentHelmInfo begin");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        List<CloudifyTenant> tenantItems =
            (List<CloudifyTenant>) getCacheManager().getObject(TENANTS);
        lock.readLock().unlock();
        String cfyTen = "";
        if (tenantItems != null) {
            for (CloudifyTenant item : tenantItems) {
                cfyTen = item.name;
                lock.readLock().lock();
                List<CloudifyDeployment> cfyDeplList = (List<CloudifyDeployment>) getCacheManager()
                    .getObject(SERVICE_ID + ":" + cfyTen);
                lock.readLock().unlock();
                if (cfyDeplList != null) {
                    List<CloudifyDeploymentHelm> cfyDeplHelmList = this.updateHelmInfo(cfyDeplList);
                    lock.writeLock().lock();
                    getCacheManager().putObject(SERVICE_ID + ":" + cfyTen + ":helm",
                        cfyDeplHelmList);
                    lock.writeLock().unlock();
                }
            }
        }
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheDeploymentHelmInfo end");
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 90000)
    public void cacheDeployments() {
        logger.debug(EELFLoggerDelegate.debugLogger, "cacheDeployments begin");
        int pageSize = 500;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        List<CloudifyTenant> tenantItems =
            (List<CloudifyTenant>) getCacheManager().getObject(TENANTS);
        lock.readLock().unlock();
        String cfyTen = "default_tenant";
        if (tenantItems != null) {
            for (CloudifyTenant item : tenantItems) {
                cfyTen = item.name;
                String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
                    new String[] {"_include",
                        "id,blueprint_id,created_at,updated_at,created_by,description,tenant_name",
                        "_sort", "-updated_at", "_sort", "-created_at", "_size",
                        new Integer(pageSize).toString(), "_offset", new Integer(0).toString()});
                logger.debug(EELFLoggerDelegate.debugLogger, "cacheDeployments begin: url {}", url);
                HttpEntity<String> entity = getTenantHeader(cfyTen);
                ResponseEntity<CloudifyDeploymentList> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<CloudifyDeploymentList>() {});
                List<CloudifyDeployment> cfyDeplList = new ArrayList<CloudifyDeployment>();
                cfyDeplList.addAll(response.getBody().items);
                int totalItems = (int) response.getBody().metadata.pagination.total;
                int deplPgOffset = 0;
                deplPgOffset += pageSize;
                while (deplPgOffset < totalItems) {
                    url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, new String[] {"_include",
                        "id,blueprint_id,created_at,updated_at,created_by,description,tenant_name",
                        "_sort", "-updated_at", "_sort", "-created_at", "_size",
                        new Integer(pageSize).toString(), "_offset",
                        new Integer(deplPgOffset).toString()});
                    response = restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<CloudifyDeploymentList>() {});
                    deplPgOffset += pageSize;
                    cfyDeplList.addAll(response.getBody().items);
                }
                lock.writeLock().lock();
                getCacheManager().putObject(SERVICE_ID + ":" + cfyTen, cfyDeplList);
                lock.writeLock().unlock();
            }
        }
        logger.debug(EELFLoggerDelegate.debugLogger,
            "cacheDeployments done putting deployment data");
    }

    @Override
    public CloudifyTenantList getTenants() {
        String url = buildUrl(new String[] {baseUrl, TENANTS}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getTenants: url {}", url);
        ResponseEntity<CloudifyTenantList> response = restTemplate.exchange(url, HttpMethod.GET,
            null, new ParameterizedTypeReference<CloudifyTenantList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyEventList getEventlogs(String executionId, String tenant)
        throws Exception {
        String url =
            buildUrl(new String[] {baseUrl, EVENTS}, new String[] {"execution_id", executionId});
        logger.debug(EELFLoggerDelegate.debugLogger, "getEventlogs: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyEventList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyEventList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceIdList getNodeInstanceId(String deploymentId, String nodeId,
        String tenant) throws Exception {
        String url = buildUrl(new String[] {baseUrl, NODE_INSTANCES},
            new String[] {"deployment_id", deploymentId, "node_id", nodeId, "_include", "id"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeInstanceIdList> response =
            restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyNodeInstanceIdList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceList getNodeInstanceDetails(String deploymentId, String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, NODE_INSTANCES},
            new String[] {"deployment_id", deploymentId});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceDetails: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeInstanceList> response = restTemplate.exchange(url,
            HttpMethod.GET, entity, new ParameterizedTypeReference<CloudifyNodeInstanceList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceIdList getNodeInstances(String deploymentId, String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, NODE_INSTANCES},
            new String[] {"deployment_id", deploymentId, "_include", "id"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeInstanceIdList> response =
            restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyNodeInstanceIdList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceList getNodeInstanceVersion(String deploymentId, String nodeId,
        String tenant) throws Exception {
        String url = buildUrl(new String[] {baseUrl, NODE_INSTANCES}, new String[] {"deployment_id",
            deploymentId, "node_id", nodeId, "_include", "runtime_properties,id"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceVersion: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeInstanceList> response = restTemplate.exchange(url,
            HttpMethod.GET, entity, new ParameterizedTypeReference<CloudifyNodeInstanceList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceList getNodeInstanceVersion(String bpId, String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, NODES},
            new String[] {"deployment_id", bpId, "type", "onap.nodes.component", "_include", "id"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeIdList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyNodeIdList>() {});
        CloudifyNodeIdList result = response.getBody();
        String nodeId = result.items.get(0).id;
        return getNodeInstanceVersion(bpId, nodeId, tenant);
    }

    @Override
    public CloudifyNodeInstanceIdList getNodeInstanceId(final String bpId, String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, NODES},
            new String[] {"deployment_id", bpId, "type", "onap.nodes.component", "_include", "id"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeIdList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyNodeIdList>() {});
        CloudifyNodeIdList result = response.getBody();
        String nodeId = result.items.get(0).id;
        return getNodeInstanceId(bpId, nodeId, tenant);
    }

    @Override
    public CloudifyExecutionList getExecutions(final String deploymentId, final String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS},
            new String[] {"deployment_id", deploymentId});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutions: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyExecutionList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyExecutionList getExecutionsSummary(final String deploymentId,
        final String tenant) throws Exception {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS},
            new String[] {"deployment_id", deploymentId, "_include",
                "deployment_id,id,status,workflow_id,tenant_name,created_at,ended_at", "_sort",
                "-created_at"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutions: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyExecutionList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyExecutionList getExecutionsSummaryPerTenant(final String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS},
            new String[] {"_include",
                "deployment_id,id,status,workflow_id,tenant_name,created_at,ended_at", "_sort",
                "-created_at"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutionsSummaryPerTenant: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyExecutionList>() {});
        return response.getBody();
    }

    public CloudifyExecution getExecutionIdSummary(final String id, final String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS, id},
            new String[] {"_include",
                "deployment_id,id,status,workflow_id,tenant_name,created_at,ended_at", "_sort",
                "-created_at"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutionIdSummary: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecution> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyExecution>() {});
        return response.getBody();
    }

    @Override
    public CloudifyExecutionList getInstallExecutionSummary(final String deploymentId,
        final String tenant) {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS},
            new String[] {"deployment_id", deploymentId, "workflow_id", "install", "_include",
                "deployment_id,id,status,workflow_id,tenant_name,created_at"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutions: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyExecutionList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyExecution startExecution(CloudifyExecutionRequest execution)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "startExecution: url {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Tenant", execution.tenant);
        HttpEntity<CloudifyExecutionRequest> entity =
            new HttpEntity<CloudifyExecutionRequest>(execution, headers);
        return restTemplate.postForObject(url, entity, CloudifyExecution.class);
    }

    @Override
    public CloudifyExecution cancelExecution(final String executionId,
        Map<String, String> parameters, final String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, EXECUTIONS, executionId}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "deleteExecution: url {}", url);
        JSONObject requestJson = new JSONObject(parameters);

        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Tenant", tenant);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(requestJson.toString(), headers);
        ResponseEntity<CloudifyExecution> response = restTemplate.exchange(url, HttpMethod.POST,
            entity, new ParameterizedTypeReference<CloudifyExecution>() {});
        return response.getBody();
    }

    @Override
    public CloudifyBlueprintList getBlueprint(final String id, String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, BLUEPRINTS}, new String[] {"id", id});
        logger.debug(EELFLoggerDelegate.debugLogger, "getBlueprint: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyBlueprintList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyBlueprintList>() {});
        return response.getBody();
    }

    @Override
    public byte[] viewBlueprint(String tenant, final String id)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, BLUEPRINTS, id, "archive"}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "viewBlueprint: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<byte[]> response =
            restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<CloudifyDeployment> getDeployments(String tenant, int pageSize, int pageOffset,
        boolean recurse) {
        return this.getDeployments(tenant, pageSize, pageOffset, true, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CloudifyDeployment> getDeployments(String tenant, int pageSize, int pageOffset,
        boolean recurse, boolean cache) {
        List<CloudifyDeployment> cfyDeplList = null;
        if (cache) {
            cfyDeplList =
                (List<CloudifyDeployment>) getCacheManager().getObject(SERVICE_ID + ":" + tenant);
        }
        if (cfyDeplList == null || cfyDeplList.isEmpty()) {
            String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, new String[] {"_include",
                "id,blueprint_id,created_at,updated_at,created_by,description,tenant_name", "_sort",
                "-updated_at", "_sort", "-created_at", "_size", new Integer(pageSize).toString(),
                "_offset", new Integer(pageOffset).toString()});
            logger.debug(EELFLoggerDelegate.debugLogger, "getDeployments: url {}", url);
            HttpEntity<String> entity = getTenantHeader(tenant);
            ResponseEntity<CloudifyDeploymentList> response =
                restTemplate.exchange(url, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<CloudifyDeploymentList>() {});
            cfyDeplList = new ArrayList<CloudifyDeployment>();
            cfyDeplList.addAll(response.getBody().items);
            if (recurse) {
                int totalItems = (int) response.getBody().metadata.pagination.total;
                int deplPgOffset = 0;
                deplPgOffset += pageSize;
                while (deplPgOffset < totalItems) {
                    url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, new String[] {"_include",
                        "id,blueprint_id,created_at,updated_at,created_by,description,tenant_name",
                        "_sort", "-updated_at", "_sort", "-created_at", "_size",
                        new Integer(pageSize).toString(), "_offset",
                        new Integer(deplPgOffset).toString()});
                    response = restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<CloudifyDeploymentList>() {});
                    deplPgOffset += pageSize;
                    cfyDeplList.addAll(response.getBody().items);
                }
            }
        }
        return cfyDeplList;
    }

    public CloudifyDeploymentList getDeployments(String tenant, int pageSize, int pageOffset) {
        String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
            new String[] {"_include",
                "id,blueprint_id,created_at,updated_at,created_by,description", "_sort",
                "-updated_at", "_sort", "-created_at", "_size", new Integer(pageSize).toString(),
                "_offset", new Integer(pageOffset).toString()});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployments: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyDeploymentList>() {});

        return response.getBody();
    }

    @Override
    public List<CloudifyDeployment> getDeploymentsByInputFilter(String inputKey, String inputValue)
        throws Exception {
        JSONObject inputObject =
            new JSONObject().put("inputKey", inputKey).put("inputValue", inputValue);
        String filter = new JSONObject().put("input", inputObject).toString();
        return getDeploymentsWithFilter(null, filter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getDeploymentNamesWithFilter(HttpServletRequest request) throws Exception {
        List<CloudifyDeployment> itemList = this.getDeploymentsWithFilter(request);
        Set<String> svcIdList = new HashSet<String>();
        if (itemList != null) {
            svcIdList = (Set) itemList.stream().map(x -> ((CloudifyDeployment) x).id)
                .collect(Collectors.toSet());
        }
        List<String> response = new ArrayList<String>();
        response.addAll(svcIdList);
        return response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CloudifyDeployment> getDeploymentsWithFilter(HttpServletRequest request)
        throws Exception {
        String filters = request.getParameter("filters");
        List<CloudifyDeployment> deployments = new ArrayList<CloudifyDeployment>();
        if (filters != null) {
            deployments = getDeploymentsWithFilter(request, filters);
        } else {
            List<CloudifyTenant> selectedTenants = new ArrayList<CloudifyTenant>();
            selectedTenants = getTenants().items;
            List<CloudifyDeployment> itemList = null;
            for (CloudifyTenant tenant : selectedTenants) {
                itemList = this.getDeployments(tenant.name, 500, 0, true);
                deployments.addAll(itemList);
            }
            // apply user role based auth
            Set<String> userApps = null;
            Set<String> userRoleSet = null;
            try {
                HttpSession session = AppUtils.getSession(request);
                userApps = (Set<String>) session.getAttribute("authComponents");
                userRoleSet = (Set<String>) session.getAttribute("user_roles");
            } catch (Exception e) {
                // requester is REST API
                userRoleSet = (Set<String>) request.getAttribute("userRoles");
                userApps = (Set<String>) request.getAttribute("userApps");
            }

            if (userApps == null) {
                userApps = new TreeSet<String>();
            }

            if (userRoleSet == null) {
                userRoleSet = new TreeSet<String>();
            }

            Predicate<String> adminPred =
                p -> p.contains("System_Administrator") || p.contains("Write_Access");

            Predicate<String> ecompSuperPred =
                p -> p.contains("ECOMPC_WRITE") || p.contains("ECOMPC_READ");

            if (userRoleSet.size() > 0) {
                if (userRoleSet.stream().noneMatch(adminPred)) {
                    List<String> myApps = new ArrayList(userApps);
                    if (userRoleSet.stream().noneMatch(ecompSuperPred)) {
                        deployments =
                            (List<CloudifyDeployment>) deployments.stream()
                                .filter(s -> myApps.stream()
                                    .anyMatch(roleFilter -> ((CloudifyDeployment) s).id
                                        .toLowerCase().startsWith(roleFilter)))
                                .collect(Collectors.toList());
                    } else {
                        Predicate<CloudifyDeployment> appFilter =
                            p -> p.id.toLowerCase().indexOf("dcae") == -1
                                || p.id.toLowerCase().indexOf("d2a") == -1;
                        deployments = (List<CloudifyDeployment>) deployments.stream()
                            .filter(appFilter).collect(Collectors.toList());
                    }
                }
            }
        }
        return deployments;
    }

    @Override
    public List<CloudifyDeployment> getDeploymentsWithFilter(HttpServletRequest request,
        String filter) throws Exception {
        String url = "";
        JSONObject filterJson = new JSONObject(filter);
        ReadWriteLock lock = new ReentrantReadWriteLock();

        // ---------Handle Tenant filter---------//
        List<CloudifyTenant> selectedTenants = new ArrayList<CloudifyTenant>();
        if (filterJson.has("tenant")) {
            String tenantFilterString = "";
            Object tenantObject = filterJson.get("tenant");

            // Check for logic operators
            if (tenantObject instanceof JSONObject) {
                JSONObject tenantJsonObject = filterJson.getJSONObject("tenant");
                if (tenantJsonObject.has("$not")) {
                    tenantFilterString = tenantJsonObject.getString("$not");
                    selectedTenants = tenantFilter(tenantFilterString, true);
                } else {
                    throw new Exception("ERROR: Not a valid logic operator");
                }
            } else if (tenantObject instanceof String) {
                tenantFilterString = filterJson.getString("tenant");
                selectedTenants = tenantFilter(tenantFilterString, false);
            }
        } else {
            selectedTenants = getTenants().items;
        }
        // ---------Get Deployments based on tenants selected---------//
        List<CloudifyDeployment> deployments = new ArrayList<CloudifyDeployment>();
        List<CloudifyDeployment> itemList = null;
        HttpEntity<String> entity;
        String tenantFilterStr = "";
        int pageSize = 500;

        // ---------Handle the _include filter---------//
        String include = filterJson.has("_include") ? filterJson.getString("_include") : null;
        for (CloudifyTenant tenant : selectedTenants) {
            tenantFilterStr = tenant.name;
            lock.readLock().lock();
            itemList = (List<CloudifyDeployment>) getCacheManager()
                .getObject("service-list" + ":" + tenantFilterStr);
            lock.readLock().unlock();

            if (itemList == null || include != null) {
                if (include == null || include.isEmpty()) {
                    include =
                        "id,blueprint_id,created_at,updated_at,created_by,description,tenant_name";
                }
                url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
                    new String[] {"_include", include, "_sort", "-updated_at", "_sort",
                        "-created_at", "_size", new Integer(pageSize).toString(), "_offset",
                        new Integer(0).toString()});

                logger.debug(EELFLoggerDelegate.debugLogger, "getDeployments: url {}", url);

                entity = getTenantHeader(tenant.name);
                ResponseEntity<CloudifyDeploymentList> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<CloudifyDeploymentList>() {});
                deployments.addAll(response.getBody().items);
                int totalItems = (int) response.getBody().metadata.pagination.total;
                int deplPgOffset = 0;
                deplPgOffset += pageSize;
                while (deplPgOffset < totalItems) {
                    url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
                        new String[] {"_include", include, "_sort", "-updated_at", "_sort",
                            "-created_at", "_size", new Integer(pageSize).toString(), "_offset",
                            new Integer(deplPgOffset).toString()});
                    response = restTemplate.exchange(url, HttpMethod.GET, entity,
                        new ParameterizedTypeReference<CloudifyDeploymentList>() {});
                    deplPgOffset += pageSize;
                    deployments.addAll(response.getBody().items);
                }
            } else {
                deployments.addAll(itemList);
            }
        }
        // apply user role based auth
        Set<String> userRoleSet = (Set<String>) request.getAttribute("userRoles");
        Set<String> userApps = (Set<String>) request.getAttribute("userApps");

        Predicate<String> adminPred =
            p -> p.contains("System_Administrator") || p.contains("Write_Access");

        Predicate<String> ecompSuperPred =
            p -> p.contains("ECOMPC_WRITE") || p.contains("ECOMPC_READ");

        if (userRoleSet.stream().noneMatch(adminPred)) {
            if (userRoleSet.stream().noneMatch(ecompSuperPred)) {
                deployments = (List<CloudifyDeployment>) deployments.stream()
                    .filter(s -> userApps.stream()
                        .anyMatch(appFilter -> (((CloudifyDeployment) s).id.toLowerCase()
                            .indexOf(appFilter) == 0)))
                    .collect(Collectors.<CloudifyDeployment>toList());
            } else {
                Predicate<CloudifyDeployment> appFilter =
                    p -> p.id.toLowerCase().indexOf("dcae") == -1;
                deployments = (List<CloudifyDeployment>) deployments.stream().filter(appFilter)
                    .collect(Collectors.toList());
            }
        }

        List<CloudifyDeployment> filteredDeployments = deployments;
        // -------------------ServiceId Filter-------------------//
        if (filterJson.has("serviceId")) {
            String serviceIdFilterString;
            Object serviceIdObject = filterJson.get("serviceId");

            // Check for logic operators
            if (serviceIdObject instanceof JSONObject) {
                JSONObject serviceIdJsonObject = filterJson.getJSONObject("serviceId");
                if (serviceIdJsonObject.has("$not")) {
                    serviceIdFilterString = serviceIdJsonObject.getString("$not");
                    filteredDeployments =
                        serviceIdFilter(serviceIdFilterString, filteredDeployments, true);
                } else {
                    throw new Exception("ERROR: Not a valid logic operator");
                }
            } else if (serviceIdObject instanceof String) {
                serviceIdFilterString = filterJson.getString("serviceId");
                filteredDeployments =
                    serviceIdFilter(serviceIdFilterString, filteredDeployments, false);
            }
        }

        // ------------------Handle Input Filter--------------//
        if (filterJson.has("input")) {
            JSONObject inputFilterObject;
            Object inputObject = filterJson.get("input");

            // Check for logic operators
            if (inputObject instanceof JSONObject) {
                JSONObject inputJsonObject = filterJson.getJSONObject("input");
                if (inputJsonObject.has("$not")) {
                    inputFilterObject = inputJsonObject.getJSONObject("$not");
                    filteredDeployments = inputFilter(inputFilterObject, filteredDeployments, true);
                }
                // If no operators, pass to filter func
                else {
                    inputFilterObject = inputJsonObject;
                    filteredDeployments =
                        inputFilter(inputFilterObject, filteredDeployments, false);
                }
            }
        }

        // -------------------Install Status Filter-------------------//
        if (filterJson.has("installStatus")) {
            String installStatusFilterString;
            Object installStatusObject = filterJson.get("installStatus");

            // Check for logic operators
            if (installStatusObject instanceof JSONObject) {
                JSONObject installStatusJsonObject = filterJson.getJSONObject("installStatus");
                if (installStatusJsonObject.has("$not")) {
                    installStatusFilterString = installStatusJsonObject.getString("$not");
                    filteredDeployments =
                        installStatusFilter(installStatusFilterString, filteredDeployments, true);
                } else {
                    throw new Exception("ERROR: Not a valid logic operator");
                }
            } else if (installStatusObject instanceof String) {
                installStatusFilterString = filterJson.getString("installStatus");
                filteredDeployments =
                    installStatusFilter(installStatusFilterString, filteredDeployments, false);
            }
        }

        // -------------------isHelm Filter-------------------//
        if (filterJson.has("isHelm")) {
            String helmFilterString;
            Object helmObject = filterJson.get("isHelm");

            // Check for logic operators
            if (helmObject instanceof JSONObject) {
                JSONObject helmJsonObject = filterJson.getJSONObject("isHelm");
                if (helmJsonObject.has("$not")) {
                    helmFilterString = helmJsonObject.getString("$not");
                    filteredDeployments = helmFilter(helmFilterString, filteredDeployments, true);
                } else {
                    throw new Exception("ERROR: Not a valid logic operator");
                }
            } else if (helmObject instanceof String) {
                helmFilterString = filterJson.getString("isHelm");
                filteredDeployments = helmFilter(helmFilterString, filteredDeployments, false);
            }
        }
        return filteredDeployments;
    }

    /*
     * Helper function to handle the tenant filter
     */
    private List<CloudifyTenant> tenantFilter(String filterString, boolean isNot) throws Exception {
        CloudifyTenantList availableTenants = getTenants();
        List<CloudifyTenant> selectedTenants = new ArrayList<CloudifyTenant>();

        // If using tenant filter, verify its valid tenant name
        if (filterString != null && !filterString.isEmpty()) {
            for (CloudifyTenant tenant : availableTenants.items) {
                if (!isNot && tenant.name.equals(filterString)) {
                    selectedTenants.add(tenant);
                } else if (isNot && !tenant.name.equals(filterString)) {
                    selectedTenants.add(tenant);
                }
            }
            if (selectedTenants.isEmpty()) {
                throw new Exception(
                    "ERROR: Tenant filter was used but resulted in no selected tenants");
            }
        }
        // If no proper tenants given
        else {
            throw new Exception("ERROR: Tenant filter was used but no tenants were given");
        }
        return selectedTenants;
    }

    /*
     * Helper function to filter deployments by serviceId
     */
    private List<CloudifyDeployment> serviceIdFilter(String filterString,
        List<CloudifyDeployment> deployments, boolean isNot) throws Exception {
        List<CloudifyDeployment> newFilteredDeployments = new ArrayList<CloudifyDeployment>();
        if (filterString != null && !filterString.isEmpty()) {
            for (CloudifyDeployment dep : deployments) {
                if (!isNot && dep.id.contains(filterString))
                    newFilteredDeployments.add(dep);
                else if (isNot && !dep.id.contains(filterString))
                    newFilteredDeployments.add(dep);
            }
        } else {
            throw new Exception(
                "ERROR: Service ID filter was used but a valid serviceId String was not provided");
        }
        return newFilteredDeployments;
    }

    /*
     * Helper function to filter deployments by input
     */
    private List<CloudifyDeployment> inputFilter(JSONObject filterJson,
        List<CloudifyDeployment> deployments, boolean isNot) throws Exception {
        List<CloudifyDeployment> newFilteredDeployments = new ArrayList<CloudifyDeployment>();
        if (filterJson != null && filterJson.has("inputKey") && filterJson.has("inputValue")
            && !filterJson.isNull("inputKey") && !filterJson.isNull("inputValue")) {
            String inputKey = filterJson.getString("inputKey");
            String inputValue = filterJson.getString("inputValue");

            /// For now, only allow the use of aaf_username and dcaeTargetType input key
            if (!inputKey.equals("aaf_username") && !inputKey.equals("dcae_target_type"))
                throw new Exception("ERROR: This input key is NOT supported");

            // For each deployment, get the input keys that contain <inputKey>
            // then check their value to see if it contains the desired <inputValue>
            for (CloudifyDeployment dep : deployments) {
                if (dep.inputs == null)
                    throw new Exception(
                        "ERROR: Deployment inputs not found, 'inputs' must be in the include filter for input filtering");
                Set<String> filteredDepInputKeys = dep.inputs.keySet().stream()
                    .filter(s -> s.contains(inputKey)).collect(Collectors.toSet());
                for (String filteredKey : filteredDepInputKeys) {
                    String value = dep.inputs.get(filteredKey).toString();
                    if (!isNot && value.equals(inputValue)) {
                        newFilteredDeployments.add(dep);
                        break;
                    } else if (isNot && !value.equals(inputValue)) {
                        newFilteredDeployments.add(dep);
                        break;
                    }
                }
            }
        } else { // If filter used but no valid KV found
            throw new Exception(
                "ERROR: Input filter was used but a valid inputKey and inputValue was not provided");
        }
        return newFilteredDeployments;
    }

    /*
     * Helper function to filter deployments by install status
     */
    private List<CloudifyDeployment> installStatusFilter(String filterString,
        List<CloudifyDeployment> deployments, boolean isNot) throws Exception {
        List<CloudifyDeployment> newFilteredDeployments = new ArrayList<CloudifyDeployment>();
        if (filterString != null && !filterString.isEmpty()) {

            // For each deployment, get execution status and compare to filter
            for (CloudifyDeployment dep : deployments) {
                List<CloudifyExecution> executions =
                    getInstallExecutionSummary(dep.id, dep.tenant_name).items;
                if (executions.size() > 0) {
                    String status = executions.get(0).status;
                    if (!isNot && status.equals(filterString)) {
                        newFilteredDeployments.add(dep);
                    } else if (isNot && !status.equals(filterString)) {
                        newFilteredDeployments.add(dep);
                    }
                }
            }
        } else { // If using filter but invalid install status given
            throw new Exception(
                "ERROR: Install Status filter was used but a valid installStatus String was not provided");
        }
        return newFilteredDeployments;
    }

    /*
     * Helper function to filter by isHelm
     */
    private List<CloudifyDeployment> helmFilter(String filterJson,
        List<CloudifyDeployment> deployments, boolean isNot)
        throws HttpStatusCodeException, Exception {
        List<CloudifyDeployment> newFilteredDeployments = new ArrayList<CloudifyDeployment>();
        if (filterJson != null && !filterJson.isEmpty()) {

            // For each deployment, get blueprint and see if it has helm plugin and compare to
            // filter
            for (CloudifyDeployment dep : deployments) {
                CloudifyBlueprintList bpList = getBlueprint(dep.blueprint_id, dep.tenant_name);
                Map<String, Object> bpPlan = bpList.items.get(0).plan;
                Map<String, String> workflows = (Map<String, String>) bpPlan.get("workflows");
                Map<String, String> pluginInfo =
                    ((List<Map<String, String>>) bpPlan.get("deployment_plugins_to_install"))
                        .get(0);
                if (pluginInfo.get("name").equals("helm-plugin")) {
                    if (!isNot && (filterJson.equals("true") || filterJson.equals("True")
                        || filterJson.equals("TRUE")))
                        newFilteredDeployments.add(dep);
                    else if (isNot && (filterJson.equals("false") || filterJson.equals("False")
                        || filterJson.equals("FALSE")))
                        newFilteredDeployments.add(dep);
                } else if (!isNot && (filterJson.equals("false") || filterJson.equals("False")
                    || filterJson.equals("FALSE")))
                    newFilteredDeployments.add(dep);
                else if (isNot && (filterJson.equals("true") || filterJson.equals("True")
                    || filterJson.equals("TRUE")))
                    newFilteredDeployments.add(dep);
            }
        } else { // If not using filter, just return original deployments
            newFilteredDeployments = deployments;
        }
        return newFilteredDeployments;
    }

    @Override
    public CloudifyDeploymentList getDeployment(final String id)
        throws HttpStatusCodeException, Exception {
        String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
            new String[] {"id", id, "_all_tenants", "true"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET,
            null, new ParameterizedTypeReference<CloudifyDeploymentList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeployment(final String id, final String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS}, new String[] {"id", id});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyDeploymentList>() {});
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeploymentInputs(final String id, final String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, DEPLOYMENTS},
            new String[] {"id", id, "_include", "inputs"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyDeploymentList>() {});
        return response.getBody();
    }

    /**
     * Get a cloudify secret
     * 
     * @return CloudifySecret
     */
    @Override
    public CloudifySecret getSecret(String secretName, String tenant) {
        String url = buildUrl(new String[] {baseUrl, SECRETS, secretName}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getSecrets: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifySecret> response = restTemplate.exchange(url, HttpMethod.GET, entity,
            new ParameterizedTypeReference<CloudifySecret>() {});
        return response.getBody();
    }

    /**
     * Get the list of cloudify plugins
     * 
     * @return List<CloudifyPlugin>
     */
    public CloudifyPluginList getPlugins() throws Exception {
        String url = buildUrl(new String[] {baseUrl, PLUGINS}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getPlugins: url {}", url);
        HttpEntity<String> entity = getTenantHeader("default_tenant");
        ResponseEntity<CloudifyPluginList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyPluginList>() {});
        CloudifyPluginList result = response.getBody();
        return result;
    }

    @Override
    public void deleteBlueprint(String bpName, String tenant)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, BLUEPRINTS, bpName}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "deleteBlueprint: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        restTemplate.exchange(url, HttpMethod.DELETE, entity,
            new ParameterizedTypeReference<CloudifyBlueprint>() {});
    }
}
