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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.onap.ccsdk.dashboard.exceptions.BadRequestException;
import org.onap.ccsdk.dashboard.exceptions.DeploymentNotFoundException;
import org.onap.ccsdk.dashboard.exceptions.DownstreamException;
import org.onap.ccsdk.dashboard.exceptions.ServerErrorException;
import org.onap.ccsdk.dashboard.exceptions.ServiceAlreadyExistsException;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployedTenant;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeployment;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentExt;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifyDeploymentHelm;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentErrorResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentLink;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentRequest;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentResponse;
import org.onap.ccsdk.dashboard.model.deploymenthandler.DeploymentsListResponse;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.objectcache.AbstractCacheManager;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class DeploymentHandlerClientImpl extends RestClientBase implements DeploymentHandlerClient {

    private String baseUrl;

    private static final String DEPLOYMENTS = "dcae-deployments";
    private static final String UPDATE_PATH = "dcae-deployment-update";
    private static final String HEALTH_CHECK = "healthcheck";
    private static final String SERVICE_ID = "service-list";

    @Autowired
    CloudifyClient cloudifyClient;
    
    private AbstractCacheManager cacheManager;   
    
    @Autowired
    public void setCacheManager(AbstractCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public AbstractCacheManager getCacheManager() {
        return cacheManager;
    }
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        String webapiUrl = DashboardProperties.getControllerProperty("site.primary",
            DashboardProperties.SITE_SUBKEY_DHANDLER_URL);
        if (webapiUrl == null)
            throw new IllegalArgumentException("Null URL not permitted");
        URL url = null;
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Failed to parse URL", ex);
        }
        String urlScheme = webapiUrl.split(":")[0];
        if (restTemplate == null) {
            createRestTemplate(url, null, null, urlScheme);
        }

    }

    public String checkHealth() {
        String url = buildUrl(new String[] { baseUrl, HEALTH_CHECK }, null);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                });
        return response.getBody();
    }
    
    public Stream<DeploymentLink> getDeployments() {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS }, null);
        ResponseEntity<DeploymentsListResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<DeploymentsListResponse>() {
                });
        DeploymentsListResponse result = response.getBody();
        return result.getDeployments().stream();
    }

    @Override
    public Stream<DeploymentLink> getDeployments(String serviceTypeId) {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS }, new String[] { "serviceTypeId", serviceTypeId });
        ResponseEntity<DeploymentsListResponse> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<DeploymentsListResponse>() {
                });
        DeploymentsListResponse result = response.getBody();
        return result.getDeployments().stream();
    }

    @Override
    public DeploymentResponse putDeployment(String deploymentId, String tenant, DeploymentRequest deploymentRequest)
            throws BadRequestException, ServiceAlreadyExistsException, ServerErrorException, DownstreamException {
        return putDeployment(deploymentId, tenant, deploymentRequest, null);
    }
    
    @Override
    public DeploymentResponse putDeployment(String deploymentId, String tenant, DeploymentRequest deploymentRequest,
        HttpServletRequest request)
            throws BadRequestException, ServiceAlreadyExistsException, ServerErrorException, DownstreamException {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS, deploymentId },
                new String[] { "cfy_tenant_name", tenant });
        String user = "";
        if (request != null) {
            user = UserUtils.getUserSession(request).getLoginId();
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<DeploymentResponse> result = restTemplate.exchange(url, HttpMethod.PUT,
                    new HttpEntity<DeploymentRequest>(deploymentRequest, headers),
                    new ParameterizedTypeReference<DeploymentResponse>() {
                    });
            //cache handling
            ReadWriteLock lock = new ReentrantReadWriteLock();
            lock.readLock().lock();
            List itemList = 
                (List<CloudifyDeployment>)getCacheManager().getObject(SERVICE_ID + ":" + tenant);           
            List itemExecList = (List<CloudifyDeploymentExt>)getCacheManager().getObject(
                SERVICE_ID + ":" + tenant + ":ext");  
            List itemHelmList = (List<CloudifyDeploymentExt>)getCacheManager().getObject(
                SERVICE_ID + ":" + tenant + ":helm"); 
            lock.readLock().unlock();
            if (itemList != null) {
                // add the new resource into app cache
                CloudifyDeployment cfyDepl = 
                    cloudifyClient.getDeploymentResource(deploymentId, tenant);
                lock.writeLock().lock();
                itemList.add(0, cfyDepl);
                lock.writeLock().unlock();
                if (itemExecList != null) {
                    List<CloudifyDeployment> thisDep = new ArrayList<CloudifyDeployment>();
                    thisDep.add(cfyDepl);
                    List<CloudifyDeploymentExt> thisDepExec = 
                        cloudifyClient.updateWorkflowStatus(thisDep);
                    List<CloudifyDeploymentHelm> thisDepHelm = 
                        cloudifyClient.updateHelmInfo(thisDep);
                    lock.writeLock().lock();
                    itemExecList.add(0, thisDepExec.get(0));
                    itemHelmList.add(0, thisDepHelm.get(0));
                    lock.writeLock().unlock();
                }
                // handle the owner deployment map cache
                if (!user.isEmpty()) {
                    CloudifyDeployedTenant updDepl = 
                        new CloudifyDeployedTenant(cfyDepl.id, cfyDepl.tenant_name, 
                            cfyDepl.created_at, cfyDepl.updated_at);
                    lock.readLock().lock();
                    Map<String, List<CloudifyDeployedTenant>> deplPerOwner = 
                        (Map<String, List<CloudifyDeployedTenant>>) 
                        getCacheManager().getObject("owner_deploy_map");
                    lock.readLock().unlock();
                    if (deplPerOwner != null) {
                        List<CloudifyDeployedTenant> currOwnedDepls = deplPerOwner.get(user); 
                        if (currOwnedDepls != null) {
                            currOwnedDepls.add(0, updDepl);
                        } else {
                            currOwnedDepls = 
                                new ArrayList<CloudifyDeployedTenant>();
                            currOwnedDepls.add(updDepl);                    
                        }
                        lock.writeLock().lock();
                        deplPerOwner.put(user, currOwnedDepls);
                        lock.writeLock().unlock();
                    } else {
                        deplPerOwner = new HashMap<String, List<CloudifyDeployedTenant>>();
                        List<CloudifyDeployedTenant> deplForBpAggr = new ArrayList<CloudifyDeployedTenant>();
                        deplForBpAggr.add(updDepl);
                        deplPerOwner.put(user, deplForBpAggr);
                    }
                }
            }
            return result.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            String errBody = null;
            errBody = e.getResponseBodyAsString();
            StringBuilder errDetails = new StringBuilder();
            errDetails.append(e.getMessage()).append("  ").append(errBody);
            if (e.getStatusCode().value() == 400 || e.getStatusCode().value() == 415
                    || e.getStatusCode().value() == 404) {
                throw new BadRequestException(errDetails.toString());
            } else if (e.getStatusCode().value() == 409) {
                throw new ServiceAlreadyExistsException(errDetails.toString());
            } else if (e.getStatusCode().value() == 500) {
                throw new ServerErrorException(errDetails.toString());
            } else if (e.getStatusCode().value() == 502 || e.getStatusCode().value() == 504) {
                throw new DownstreamException(errDetails.toString());
            }
        }
        return null;
    }

    @Override
    public void deleteDeployment(String deploymentId, String tenant)
            throws BadRequestException, ServerErrorException, DownstreamException, DeploymentNotFoundException {
        deleteDeployment(deploymentId, tenant, null);
    }
    @Override
    public void deleteDeployment(String deploymentId, String tenant, HttpServletRequest request)
            throws BadRequestException, ServerErrorException, DownstreamException, DeploymentNotFoundException {
        String url = buildUrl(new String[] { baseUrl, DEPLOYMENTS, deploymentId },
                new String[] { "cfy_tenant_name", tenant, "force_uninstall", "true" });
        try {
            String user = "";
            if (request != null) {
                user = UserUtils.getUserSession(request).getLoginId();
            }
            restTemplate.exchange(url, HttpMethod.DELETE, null, new ParameterizedTypeReference<DeploymentResponse>() {
            });
            CloudifyDeployment cfyDepl = 
                cloudifyClient.getDeploymentResource(deploymentId, tenant);
            // remove resource from app cache
            ReadWriteLock lock = new ReentrantReadWriteLock();
            lock.readLock().lock();
            List itemList = 
                (List<CloudifyDeployment>)getCacheManager().getObject(SERVICE_ID + ":" + tenant);
            lock.readLock().unlock();
            if (itemList != null) {
                lock.writeLock().lock();
                itemList.remove(cfyDepl);
                getCacheManager().removeObject(SERVICE_ID + ":" + tenant);
                // put updated collection back into cache
                getCacheManager().putObject(SERVICE_ID + ":" + tenant, itemList);
                lock.writeLock().unlock();
           }
            // handle the owner deployment map cache
            if (!user.isEmpty()) {
                lock.readLock().lock();
                Map<String, List<CloudifyDeployedTenant>> deplPerOwner = 
                    (Map<String, List<CloudifyDeployedTenant>>) 
                    getCacheManager().getObject("owner_deploy_map");
                lock.readLock().unlock();
                if (deplPerOwner != null) {
                    List<CloudifyDeployedTenant> currOwnedDepls = deplPerOwner.get(user); 
                    CloudifyDeployedTenant updDepl = 
                        new CloudifyDeployedTenant(cfyDepl.id, cfyDepl.tenant_name, 
                            cfyDepl.created_at, cfyDepl.updated_at);
                    if (currOwnedDepls != null) {
                        currOwnedDepls.remove(updDepl);
                        lock.writeLock().lock();
                        deplPerOwner.put(user, currOwnedDepls);
                        lock.writeLock().unlock();
                    }
                }
            }
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            DeploymentErrorResponse errBody = null;
            String errMsg = "";
            try {
                errBody = objectMapper.readValue(e.getResponseBodyAsString(), DeploymentErrorResponse.class);
            } catch (IOException e1) {
                errBody = null;
            }
            if (errBody != null) {
                errMsg = errBody.getMessage();
            }
            StringBuilder errDetails = new StringBuilder();
            errDetails.append(e.getMessage()).append("  ").append(errMsg);
            if (e.getStatusCode().value() == 400 || e.getStatusCode().value() == 415) {
                throw new BadRequestException(errDetails.toString());
            } else if (e.getStatusCode().value() == 404) {
                throw new DeploymentNotFoundException(e.getMessage());
            } else if (e.getStatusCode().value() == 500) {
                throw new ServerErrorException(errDetails.toString());
            } else if (e.getStatusCode().value() == 502 || e.getStatusCode().value() == 504) {
                throw new DownstreamException(errDetails.toString());
            }
        }
    }
}
