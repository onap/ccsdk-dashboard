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
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.onap.ccsdk.dashboard.exceptions.DashboardControllerException;
import org.onap.ccsdk.dashboard.model.cloudify.CloudifySecret;
import org.onap.ccsdk.dashboard.model.consul.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.consul.ConsulDeploymentHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceInfo;
import org.onap.ccsdk.dashboard.util.DashboardProperties;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@org.springframework.stereotype.Service
public class ConsulRestClientImpl extends RestClientBase implements ConsulClient {

    private static EELFLoggerDelegate logger =
        EELFLoggerDelegate.getLogger(ConsulRestClientImpl.class);
    private String baseUrl;
    private String consulAclToken;
    private HttpEntity<String> tokenEntity;

    private static final String API_VER = "v1";
    private static final String CATALOG = "catalog";
    private static final String SERVICES = "services";
    private static final String HEALTH = "health";
    private static final String CHECKS = "checks";
    private static final String STATE = "state";
    private static final String ANY = "any";
    private static final String APP_PROP_PREFIX = "site.primary";

    @PostConstruct
    public void init() throws DashboardControllerException {
        if (consulAclToken == null || consulAclToken.isEmpty()) {
            consulAclToken = getConsulAcl();
        }
        if (consulAclToken != null && !consulAclToken.isEmpty()) {
            tokenEntity = getConsulTokenHeader(consulAclToken);
        }
        String webapiUrl = DashboardProperties.getControllerProperty(APP_PROP_PREFIX,
            DashboardProperties.SITE_SUBKEY_CONSUL_URL);
        if (webapiUrl == null)
            throw new IllegalArgumentException("Null URL not permitted");
        URL url = null;
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new DashboardControllerException("Failed to parse URL", ex);
        }
        String urlScheme = webapiUrl.split(":")[0];
        if (restTemplate == null) {
            createRestTemplate(url, null, null, urlScheme);
        }
    }

    protected String getConsulAcl() {
        return getConsulAcl(null);
    }

    protected String getConsulAcl(RestTemplate cfyRest) {
        String aclToken = null;
        String webapiUrl = DashboardProperties.getControllerProperty(APP_PROP_PREFIX,
            DashboardProperties.SITE_SUBKEY_CLOUDIFY_URL);
        String cfyBaseUrl = "";
        if (webapiUrl != null) {
            String user = DashboardProperties.getControllerProperty(APP_PROP_PREFIX,
                DashboardProperties.SITE_SUBKEY_CLOUDIFY_USERNAME);
            String pass = DashboardProperties.getControllerProperty(APP_PROP_PREFIX,
                DashboardProperties.SITE_SUBKEY_CLOUDIFY_PASS);
            URL url = null;
            try {
                url = new URL(webapiUrl);
                cfyBaseUrl = url.toExternalForm();
                String urlScheme = webapiUrl.split(":")[0];
                if (cfyRest == null) {
                    cfyRest = createCfyRestTemplate(url, user, pass, urlScheme);
                }
                String urlStr =
                    buildUrl(new String[] {cfyBaseUrl, "secrets", "eom-dashboard-acl-token"}, null);
                logger.debug(EELFLoggerDelegate.debugLogger, "getAclSecret: url {}", urlStr);

                ResponseEntity<CloudifySecret> response = cfyRest.exchange(urlStr, HttpMethod.GET,
                    null, new ParameterizedTypeReference<CloudifySecret>() {});
                aclToken = response.getBody().getValue();
            } catch (MalformedURLException me) {
                logger.error(EELFLoggerDelegate.errorLogger,
                    "Failed to parse URL - malformed" + me.getMessage());
            } catch (Exception e) {
                logger.error(EELFLoggerDelegate.errorLogger, e.getMessage());
                aclToken = "";
            }
        }
        return aclToken;
    }

    protected RestTemplate createCfyRestTemplate(URL url, String user, String pass,
        String urlScheme) throws Exception {
        RestTemplate restTempl = null;
        final HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), urlScheme);
        // Build a client with a credentials provider
        CloseableHttpClient httpClient = null;

        if (user != null && pass != null) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(httpHost),
                new UsernamePasswordCredentials(user, pass));
            httpClient =
                HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();
        } else {
            httpClient = HttpClientBuilder.create().build();
        }
        // Create request factory
        HttpComponentsClientHttpRequestFactoryBasicAuth requestFactory =
            new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost);
        requestFactory.setHttpClient(httpClient);
        restTempl = new RestTemplate();
        restTempl.setRequestFactory(requestFactory);
        return restTempl;
    }

    /**
     * @param consul_acl_token the consul_acl_token to set
     */
    public void setConsulAclToken(String consulAclToken) {
        this.consulAclToken = consulAclToken;
    }

    @Override
    public List<ConsulServiceHealth> getServiceHealth(String dc, String srvc) throws Exception {
        String url = buildUrl(new String[] {baseUrl, API_VER, HEALTH, CHECKS, srvc},
            new String[] {"dc", dc});
        logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealth: url {}", url);
        ResponseEntity<List<ConsulServiceHealth>> response = null;
        List<ConsulServiceHealth> result = new ArrayList<>();
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                new ParameterizedTypeReference<List<ConsulServiceHealth>>() {});
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                // update consul ACL token header and retry
                consulAclToken = getConsulAcl();
                if (consulAclToken != null && !consulAclToken.isEmpty()) {
                    tokenEntity = getConsulTokenHeader(consulAclToken);
                    response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                        new ParameterizedTypeReference<List<ConsulServiceHealth>>() {});
                } else {
                    throw e;
                }
            }
        }
        if (response != null && !response.getBody().isEmpty()) {
            result = response.getBody();
        }
        return result;
    }

    @Override
    public ConsulDeploymentHealth getServiceHealthByDeploymentId(String deploymentId)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, API_VER, HEALTH, STATE, ANY},
            new String[] {"filter", "ServiceTags contains " + "\"" + deploymentId + "\""});
        logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealthByDeploymentId: url {}", url);
        ResponseEntity<List<ConsulServiceHealth>> response = null;
        ConsulDeploymentHealth result = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                new ParameterizedTypeReference<List<ConsulServiceHealth>>() {});
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                // update consul ACL token header and retry
                consulAclToken = getConsulAcl();
                if (consulAclToken != null && !consulAclToken.isEmpty()) {
                    tokenEntity = getConsulTokenHeader(consulAclToken);
                    response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                        new ParameterizedTypeReference<List<ConsulServiceHealth>>() {});
                } else {
                    throw e;
                }
            }
        }
        if (response != null && !response.getBody().isEmpty()) {
            result = new ConsulDeploymentHealth.Builder(response.getBody().get(0)).build();
        }
        return result;
    }

    @Override
    public List<ConsulServiceInfo> getServices(String dc) throws Exception {
        String url =
            buildUrl(new String[] {baseUrl, API_VER, CATALOG, SERVICES}, new String[] {"dc", dc});
        logger.debug(EELFLoggerDelegate.debugLogger, "getServices: url {}", url);
        ResponseEntity<Map<String, Object>> response = null;
        List<ConsulServiceInfo> result = new ArrayList<>();
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {});
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                // update consul ACL token header and retry
                consulAclToken = getConsulAcl();
                if (consulAclToken != null && !consulAclToken.isEmpty()) {
                    tokenEntity = getConsulTokenHeader(consulAclToken);
                    response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                        new ParameterizedTypeReference<Map<String, Object>>() {});
                } else {
                    throw e;
                }
            }
        }
        if (response != null && !response.getBody().isEmpty()) {
            Map<String, Object> serviceInfo = response.getBody();
            for (Map.Entry<String, Object> entry : serviceInfo.entrySet()) {
                // Be defensive
                List<String> addrs = null;
                if (entry.getValue() instanceof List<?>) {
                    addrs = (List<String>) entry.getValue();
                }
                else {
                    addrs = new ArrayList<>();
                }
                result.add(new ConsulServiceInfo(entry.getKey(), addrs));
            }
        }
        return result;
    }

    @Override
    public List<ConsulNodeInfo> getNodes(String dc) throws Exception {
        String url =
            buildUrl(new String[] {baseUrl, API_VER, CATALOG, "nodes"}, new String[] {"dc", dc});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodesHealth: url {}", url);
        ResponseEntity<List<ConsulNodeInfo>> response = null;
        List<ConsulNodeInfo> result = new ArrayList<>();
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                new ParameterizedTypeReference<List<ConsulNodeInfo>>() {});
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                // update consul ACL token header and retry
                consulAclToken = getConsulAcl();
                if (consulAclToken != null && !consulAclToken.isEmpty()) {
                    tokenEntity = getConsulTokenHeader(consulAclToken);
                    response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                        new ParameterizedTypeReference<List<ConsulNodeInfo>>() {});
                } else {
                    throw e;
                }
            }
        }
        if (response != null && !response.getBody().isEmpty()) {
            result = response.getBody();
        } 
        return result;
    }

    @Override
    public List<ConsulServiceHealth> getNodeServicesHealth(String dc, String nodeId)
        throws Exception {
        String url = buildUrl(new String[] {baseUrl, API_VER, HEALTH, "node", nodeId},
            new String[] {"dc", dc});
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeServicesHealth: url {}", url);
        ResponseEntity<List<ConsulServiceHealth>> response = null;
        List<ConsulServiceHealth> result = new ArrayList<>();
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                new ParameterizedTypeReference<List<ConsulServiceHealth>>() {});
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                // update consul ACL token header and retry
                consulAclToken = getConsulAcl();
                if (consulAclToken != null && !consulAclToken.isEmpty()) {
                    tokenEntity = getConsulTokenHeader(consulAclToken);
                    response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                        new ParameterizedTypeReference<List<ConsulServiceHealth>>() {});
                } else {
                    throw e;
                }
            }
        }
        if (response != null && !response.getBody().isEmpty()) {
            result = response.getBody();
        }
        return result;
    }

    @Override
    public List<ConsulDatacenter> getDatacenters() {
        String url = buildUrl(new String[] {baseUrl, API_VER, CATALOG, "datacenters"}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getDatacentersHealth: url {}", url);
        ResponseEntity<List<String>> response = null;
        List<ConsulDatacenter> result = new ArrayList<>();
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                new ParameterizedTypeReference<List<String>>() {});
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                // update consul ACL token header and retry
                consulAclToken = getConsulAcl();
                if (consulAclToken != null && !consulAclToken.isEmpty()) {
                    tokenEntity = getConsulTokenHeader(consulAclToken);
                    response = restTemplate.exchange(url, HttpMethod.GET, tokenEntity,
                        new ParameterizedTypeReference<List<String>>() {});
                } else {
                    throw e;
                }
            }
        }
        if (response != null && !response.getBody().isEmpty()) {
            for (String dc : response.getBody())
                result.add(new ConsulDatacenter(dc));    
        } 
        return result;
    }

}
