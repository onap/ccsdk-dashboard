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
package org.onap.ccsdk.dashboard.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.onap.ccsdk.dashboard.model.CloudifyBlueprintList;
import org.onap.ccsdk.dashboard.model.CloudifyDeployedTenantList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentList;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateRequest;
import org.onap.ccsdk.dashboard.model.CloudifyDeploymentUpdateResponse;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyExecution;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionList;
import org.onap.ccsdk.dashboard.model.CloudifyExecutionRequest;
import org.onap.ccsdk.dashboard.model.CloudifyNodeIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceIdList;
import org.onap.ccsdk.dashboard.model.CloudifyNodeInstanceList;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
    private static final String TENANT = "tenant_name";

    @PostConstruct
    public void init() {
        String webapiUrl = DashboardProperties.getControllerProperty("dev",
            DashboardProperties.CONTROLLER_SUBKEY_URL);
        if (webapiUrl == null) {
            throw new IllegalArgumentException("Null URL not permitted");
        }
        String user = DashboardProperties.getControllerProperty("dev",
            DashboardProperties.CONTROLLER_SUBKEY_USERNAME);
        String pass = DashboardProperties.getControllerProperty("dev",
            DashboardProperties.CONTROLLER_SUBKEY_PASS);
        URL url = null;
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Failed to parse URL", ex);
        }
        String urlScheme = webapiUrl.split(":")[0];
        if (restTemplate == null) {
            createRestTemplate(url, user, pass, urlScheme);
        }
    }

    @Override
    public CloudifyTenantList getTenants() {
        String url = buildUrl(new String[] { baseUrl, TENANTS }, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getTenants: url {}", url);
        ResponseEntity<CloudifyTenantList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<CloudifyTenantList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyEventList getEventlogs(String executionId, String tenant) {
        String url = buildUrl(new String[] { baseUrl, EVENTS }, new String[] { "execution_id", executionId });
        logger.debug(EELFLoggerDelegate.debugLogger, "getEventlogs: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyEventList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyEventList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceIdList getNodeInstanceId(String deploymentId, String nodeId, String tenant) {
        String url = buildUrl(new String[] { baseUrl, NODE_INSTANCES },
                new String[] { "deployment_id", deploymentId, "node_id", nodeId, "_include", "id" });
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeInstanceIdList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyNodeInstanceIdList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceList getNodeInstanceVersion(String deploymentId, String nodeId, String tenant) {
        String url = buildUrl(new String[] { baseUrl, NODE_INSTANCES },
                new String[] { "deployment_id", deploymentId, "node_id", nodeId, "_include", "runtime_properties,id" });
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceVersion: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeInstanceList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyNodeInstanceList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyNodeInstanceList getNodeInstanceVersion(String bpId, String tenant) {
        String url = buildUrl(new String[] { baseUrl, NODES },
                new String[] { "deployment_id", bpId, "type", "onap.nodes.component", "_include", "id" });
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeIdList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyNodeIdList>() {
                });
        CloudifyNodeIdList result = response.getBody();
        String nodeId = result.items.get(0).id;
        return getNodeInstanceVersion(bpId, nodeId, tenant);
    }

    @Override
    public CloudifyNodeInstanceIdList getNodeInstanceId(final String bpId, String tenant) {
        String url = buildUrl(new String[] {baseUrl, NODES},
            new String[] {"deployment_id", bpId, "type", "onap.nodes.component", "_include", "id"});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeInstanceId: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyNodeIdList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyNodeIdList>() {
                });
        CloudifyNodeIdList result = response.getBody();
        String nodeId = result.items.get(0).id;
        return getNodeInstanceId(bpId, nodeId, tenant);
    }

    @Override
    public CloudifyDeployedTenantList getTenantInfoFromDeploy(String tenant) {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS },
                new String[] { "_include", "id,blueprint_id,tenant_name" });
        logger.debug(EELFLoggerDelegate.debugLogger, "getTenantInfoFromDeploy: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);

        ResponseEntity<CloudifyDeployedTenantList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyDeployedTenantList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyExecutionList getExecutions(final String deploymentId, final String tenant) {
        String url = buildUrl(new String[] { baseUrl, EXECUTIONS }, new String[] { "deployment_id", deploymentId });
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutions: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyExecutionList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyExecutionList getExecutionsSummary(final String deploymentId, final String tenant) {
        String url = buildUrl(new String[] { baseUrl, EXECUTIONS }, new String[] { "deployment_id", deploymentId,
                "_include", "deployment_id,id,status,workflow_id,tenant_name,created_at" });
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutions: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyExecutionList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyExecution startExecution(CloudifyExecutionRequest execution) {
        String url = buildUrl(new String[] { baseUrl, EXECUTIONS }, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "startExecution: url {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Tenant", execution.tenant);
        HttpEntity<CloudifyExecutionRequest> entity = new HttpEntity<CloudifyExecutionRequest>(execution, headers);
        return restTemplate.postForObject(url, entity, CloudifyExecution.class);
    }

    @Override
    public CloudifyDeploymentUpdateResponse updateDeployment(CloudifyDeploymentUpdateRequest execution) {
        String url = buildUrl(new String[] { baseUrl, UPDATE_DEPLOYMENT }, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "updateDeployment: url {}", url);
        return restTemplate.postForObject(url, execution, CloudifyDeploymentUpdateResponse.class);
    }

    @Override
    public CloudifyExecution cancelExecution(final String executionId, Map<String, String> parameters,
            final String tenant) {
        String url = buildUrl(new String[] { baseUrl, EXECUTIONS, executionId }, null);
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
    public CloudifyBlueprintList getBlueprint(final String id, String tenant) {
        String url = buildUrl(new String[] { baseUrl, BLUEPRINTS }, new String[] { "id", id });
        logger.debug(EELFLoggerDelegate.debugLogger, "getBlueprint: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyBlueprintList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyBlueprintList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeployments() {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS }, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployments: url {}", url);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<CloudifyDeploymentList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeployment(final String id) {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS }, new String[] { "id", id });
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<CloudifyDeploymentList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeployment(final String id, final String tenant) {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS }, new String[] { "id", id, TENANT, tenant });
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<CloudifyDeploymentList>() {
                });
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeploymentInputs(final String id, final String tenant) {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS }, new String[] { "id", id, "_include", "inputs" });
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        HttpEntity<String> entity = getTenantHeader(tenant);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, new ParameterizedTypeReference<CloudifyDeploymentList>() {});
        return response.getBody();
    }
}
