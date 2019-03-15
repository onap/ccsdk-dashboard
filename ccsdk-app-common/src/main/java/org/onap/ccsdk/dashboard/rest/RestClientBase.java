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

import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Base class for all the Rest client implementations
 * 
 * @author rp5662
 *
 */
public class RestClientBase {
    protected RestTemplate restTemplate = null;

    protected void createRestTemplate(URL url, String user, String pass, String urlScheme) {
        RestTemplate restTempl = null;
        final HttpHost httpHost = new HttpHost(url.getHost(), url.getPort(), urlScheme);

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
        restTempl = new RestTemplate();
        restTempl.setRequestFactory(requestFactory);
        this.restTemplate = restTempl;
    }

    /**
     * Builds URL ensuring appropriate separators. The base comes from properties
     * file so could have many problems.
     * 
     * @param base
     * @param suffix
     * @param queryParams key-value pairs; i.e. must have an even number of entries.
     *                    Ignored if null.
     * @return
     */
    protected String buildUrl(final String[] path, final String[] queryParams) {
        StringBuilder sb = new StringBuilder(path[0]);
        for (int p = 1; p < path.length; ++p) {
            if (!path[p - 1].endsWith("/") && !path[p].startsWith("/"))
                sb.append('/');
            sb.append(path[p]);
        }
        if (queryParams != null && queryParams.length > 0) {
            sb.append('?');
            int i = 0;
            while (i < queryParams.length) {
                if (i > 0)
                    sb.append('&');
                sb.append(queryParams[i]);
                sb.append('=');
                sb.append(queryParams[i + 1]);
                i += 2;
            }
        }
        return sb.toString();
    }

    /**
     * Create Http Entity for the tenant header
     * 
     * @param tenant
     * @return
     */
    protected HttpEntity<String> getTenantHeader(String tenant) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Tenant", tenant);
        return new HttpEntity<String>("parameters", headers);
    }
}
