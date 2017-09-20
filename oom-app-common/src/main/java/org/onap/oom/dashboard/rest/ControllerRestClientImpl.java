/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 *  Copyright (c) 2017 AT&T Intellectual Property. All rights reserved.
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
package org.onap.oom.dashboard.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.onap.oom.dashboard.model.CloudifyBlueprintContent;
import org.onap.oom.dashboard.model.CloudifyBlueprintList;
import org.onap.oom.dashboard.model.CloudifyBlueprintUpload;
import org.onap.oom.dashboard.model.CloudifyDeploymentList;
import org.onap.oom.dashboard.model.CloudifyDeploymentRequest;
import org.onap.oom.dashboard.model.CloudifyExecution;
import org.onap.oom.dashboard.model.CloudifyExecutionList;
import org.onap.oom.dashboard.model.CloudifyExecutionRequest;
import org.onap.oom.dashboard.model.ConsulDatacenter;
import org.onap.oom.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.oom.dashboard.model.ConsulNodeInfo;
import org.onap.oom.dashboard.model.ConsulServiceHealth;
import org.onap.oom.dashboard.model.ConsulServiceHealthHistory;
import org.onap.oom.dashboard.model.ConsulServiceInfo;
import org.openecomp.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Provides methods for accessing the ECOMP Controller API via REST. Most
 * methods are just simple proxies. Only the methods that fetch one page of data
 * have to do any real work.
 *
 * Implemented using Spring RestTemplate. Supports basic HTTP authentication.
 *
 */
public class ControllerRestClientImpl implements IControllerRestClient {

    private static EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(ControllerRestClientImpl.class);
    private static final String DEPLOYMENT_ID = "deployment_id";

    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Builds a restTemplate. If username and password are supplied, uses basic
     * HTTP authentication.
     *
     * @param webapiUrl
     *            URL of the web endpoint
     * @param user
     *            user name; ignored if null
     * @param pass
     *            password
     */
    public ControllerRestClientImpl(String webapiUrl, String user, String pass) {
        if (webapiUrl == null) {
            throw new IllegalArgumentException("Null URL not permitted");
        }

        URL url = null;
        try {
            url = new URL(webapiUrl);
            baseUrl = url.toExternalForm();
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Failed to parse URL", ex);
        }
        final HttpHost httpHost = new HttpHost(url.getHost(), url.getPort());

        // Build a client with a credentials provider
        CloseableHttpClient httpClient = null;
        if (user != null && pass != null) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(httpHost), new UsernamePasswordCredentials(user, pass));
            httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();
        } else {
            httpClient = HttpClientBuilder.create().build();
        }
        // Create request factory
        HttpComponentsClientHttpRequestFactoryBasicAuth requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(
            httpHost);
        requestFactory.setHttpClient(httpClient);

        // Put the factory in the template
        this.restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
    }

    /**
     * Builds URL ensuring appropriate separators. The base comes from
     * properties file so could have many problems.
     *
     * @param queryParams
     *            key-value pairs; i.e. must have an even number of entries.
     *            Ignored if null.
     * @return url
     */
    private String buildUrl(final String[] path, final String[] queryParams) {
        StringBuilder sb = new StringBuilder(path[0]);
        for (int p = 1; p < path.length; ++p) {
            if (!path[p - 1].endsWith("/") && !path[p].startsWith("/")) {
                sb.append('/');
            }
            sb.append(path[p]);
        }
        if (queryParams != null && queryParams.length > 0) {
            sb.append('?');
            int i = 0;
            while (i < queryParams.length) {
                if (i > 0) {
                    sb.append('&');
                }
                sb.append(queryParams[i]);
                sb.append('=');
                sb.append(queryParams[i + 1]);
                i += 2;
            }
        }
        return sb.toString();
    }

    @Override
    public CloudifyBlueprintList getBlueprints() {
        String url = buildUrl(new String[]{baseUrl, blueprintsPath}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getBlueprints: url {}", url);
        ResponseEntity<CloudifyBlueprintList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<CloudifyBlueprintList>() {
            });
        return response.getBody();
    }

    @Override
    public CloudifyBlueprintList getBlueprint(final String id) {
        String url = buildUrl(new String[]{baseUrl, blueprintsPath}, new String[]{"id", id});
        logger.debug(EELFLoggerDelegate.debugLogger, "getBlueprint: url {}", url);
        ResponseEntity<CloudifyBlueprintList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<CloudifyBlueprintList>() {
            });
        return response.getBody();
    }

    @Override
    public CloudifyBlueprintContent viewBlueprint(final String id) {
        String url = buildUrl(new String[]{baseUrl, viewBlueprintsPath}, new String[]{"id", id});
        logger.debug(EELFLoggerDelegate.debugLogger, "viewBlueprint: url {}", url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String yaml = response.getBody();
        return new CloudifyBlueprintContent(id, yaml);
    }

    @Override
    public CloudifyBlueprintList uploadBlueprint(CloudifyBlueprintUpload blueprint) {
        String url = buildUrl(new String[]{baseUrl, blueprintsPath}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "uploadBlueprint: url {}", url);
        return restTemplate.postForObject(url, blueprint, CloudifyBlueprintList.class);
    }

    @Override
    public int deleteBlueprint(final String id) {
        String url = buildUrl(new String[]{baseUrl, blueprintsPath, id}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "deleteBlueprint: url {}", url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null,
            new ParameterizedTypeReference<String>() {
            });
        return response.getStatusCode().value();
    }

    @Override
    public CloudifyDeploymentList getDeployments() {
        String url = buildUrl(new String[]{baseUrl, deploymentsPath}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployments: url {}", url);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<CloudifyDeploymentList>() {
            });
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList getDeployment(final String id) {
        String url = buildUrl(new String[]{baseUrl, deploymentsPath}, new String[]{"id", id});
        logger.debug(EELFLoggerDelegate.debugLogger, "getDeployment: url {}", url);
        ResponseEntity<CloudifyDeploymentList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<CloudifyDeploymentList>() {
            });
        return response.getBody();
    }

    @Override
    public CloudifyDeploymentList createDeployment(CloudifyDeploymentRequest deployment) {
        String url = buildUrl(new String[]{baseUrl, deploymentsPath}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "createDeployment: url {}", url);
        return restTemplate.postForObject(url, deployment, CloudifyDeploymentList.class);
    }

    @Override
    public int deleteDeployment(final String id, boolean ignoreLiveNodes) {
        String url = buildUrl(new String[]{baseUrl, deploymentsPath, id},
            new String[]{"ignore_live_nodes", Boolean.toString(ignoreLiveNodes)});
        logger.debug(EELFLoggerDelegate.debugLogger, "deleteDeployment: url {}", url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null,
            new ParameterizedTypeReference<String>() {
            });
        return response.getStatusCode().value();
    }

    @Override
    public CloudifyExecutionList getExecutions(final String deploymentId) {
        String url = buildUrl(new String[]{baseUrl, executionsPath}, new String[]{DEPLOYMENT_ID, deploymentId});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecutions: url {}", url);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<CloudifyExecutionList>() {
            });
        return response.getBody();
    }

    @Override
    public CloudifyExecutionList getExecution(String executionId, String deploymentId) {
        String url = buildUrl(new String[]{baseUrl, executionsPath, executionId},
            new String[]{DEPLOYMENT_ID, deploymentId});
        logger.debug(EELFLoggerDelegate.debugLogger, "getExecution: url {}", url);
        ResponseEntity<CloudifyExecutionList> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<CloudifyExecutionList>() {
            });
        return response.getBody();
    }

    @Override
    public CloudifyExecution startExecution(CloudifyExecutionRequest execution) {
        String url = buildUrl(new String[]{baseUrl, executionsPath}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "startExecution: url {}", url);
        return restTemplate.postForObject(url, execution, CloudifyExecution.class);
    }

    @Override
    public int cancelExecution(final String executionId, final String deploymentId, final String action) {
        String url = buildUrl(new String[]{baseUrl, executionsPath, executionId},
            new String[]{DEPLOYMENT_ID, deploymentId, "action", action});
        logger.debug(EELFLoggerDelegate.debugLogger, "deleteExecution: url {}", url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null,
            new ParameterizedTypeReference<String>() {
            });
        return response.getStatusCode().value();
    }

    @Override
    public URI registerService(ConsulHealthServiceRegistration registration) {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "register"}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "registerService: url {}", url);
        return restTemplate.postForLocation(url, registration);
    }

    @Override
    public int deregisterService(String serviceName) {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "deregister", serviceName}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "deregisterService: url {}", url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null,
            new ParameterizedTypeReference<String>() {
            });
        return response.getStatusCode().value();
    }

    /**
     * Translates the awkward map of String-to-List of IP into a list of
     * ConsulServiceInfo objects
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConsulServiceInfo> getServices() {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "services"}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getServicesHealth: url {}", url);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<Map<String, Object>>() {
            });
        Map<String, Object> serviceInfo = response.getBody();
        List<ConsulServiceInfo> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : serviceInfo.entrySet()) {
            // Be defensive
            List<String> addrs;
            if (entry.getValue() instanceof List<?>) {
                addrs = (List<String>) entry.getValue();
            } else {
                addrs = new ArrayList<>();
            }
            list.add(new ConsulServiceInfo(entry.getKey(), addrs));
        }
        return list;
    }

    @Override
    public List<ConsulServiceHealth> getServiceHealth(String serviceName) {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "services", serviceName}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealth: url {}", url);
        ResponseEntity<List<ConsulServiceHealth>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ConsulServiceHealth>>() {
            });
        return response.getBody();
    }

    @Override
    public List<ConsulServiceHealthHistory> getServiceHealthHistory(String serviceName, Instant start,
        Instant end) {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "svchist", serviceName},
            new String[]{"start", start.toString(), "end", end.toString()});
        logger.debug(EELFLoggerDelegate.debugLogger, "getServiceHealthHistory: url {}", url);
        // Hack around an odd interface that returns non-JSON on error:
        // "No health Data found for the selected dates or service"
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<String>() {
            });
        if (response.getBody().startsWith("No health")) {
            throw new RuntimeException(response.getBody());
        }
        List<ConsulServiceHealthHistory> result = null;
        try {
            TypeReference<List<ConsulServiceHealthHistory>> typeRef = new TypeReference<List<ConsulServiceHealthHistory>>() {
            };
            result = objectMapper.readValue(response.getBody(), typeRef);
        } catch (Exception ex) {
            logger.error(EELFLoggerDelegate.errorLogger, "getServiceHealthHistory failed to parse response body", ex);
        }
        return result;
    }

    @Override
    public List<ConsulNodeInfo> getNodes() {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "nodes"}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodesHealth: url {}", url);
        ResponseEntity<List<ConsulNodeInfo>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ConsulNodeInfo>>() {
            });
        return response.getBody();
    }

    @Override
    public List<ConsulServiceHealth> getNodeServicesHealth(String nodeId) {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "nodes", nodeId}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getNodeServicesHealth: url {}", url);
        ResponseEntity<List<ConsulServiceHealth>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ConsulServiceHealth>>() {
            });
        return response.getBody();
    }

    @Override
    public List<ConsulDatacenter> getDatacenters() {
        String url = buildUrl(new String[]{baseUrl, healthServicesPath, "datacenters"}, null);
        logger.debug(EELFLoggerDelegate.debugLogger, "getDatacentersHealth: url {}", url);
        ResponseEntity<List<String>> response = restTemplate.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<String>>() {
            });
        List<String> list = response.getBody();
        List<ConsulDatacenter> result = new ArrayList<>();
        for (String dc : list) {
            result.add(new ConsulDatacenter(dc));
        }
        return result;
    }

    /**
     * Simple test
     *
     * @param args
     *            blueprint ID
     * @throws IllegalArgumentException
     *             On bad arguments
     */
    public static void main(String[] args) throws IllegalArgumentException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Single argument expected: blueprint-id");
        }
        ControllerRestClientImpl client = new ControllerRestClientImpl("http://localhost:8081/controller", "dbus_user",
            "dbus_pass");
        final String id = args[0];
        logger.info("Requesting blueprint for " + id);
        CloudifyBlueprintList list = client.getBlueprint(id);
        if (list == null) {
            logger.error("Received null");
        } else {
            for (int i = 0; i < list.items.size(); ++i) {
                logger.info("Blueprint " + Integer.toString(i));
                logger.info(list.items.get(i).toString());
            }
        }
    }
}
