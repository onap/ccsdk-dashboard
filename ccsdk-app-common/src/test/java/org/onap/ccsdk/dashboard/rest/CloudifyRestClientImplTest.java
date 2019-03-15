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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.model.CloudifyErrorCause;
import org.onap.ccsdk.dashboard.model.CloudifyEvent;
import org.onap.ccsdk.dashboard.model.CloudifyEventList;
import org.onap.ccsdk.dashboard.model.CloudifyEventList.Metadata;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class CloudifyRestClientImplTest {

	@Mock
	RestTemplate mockRest;

	@InjectMocks
	CloudifyRestClientImpl subject = 
		new CloudifyRestClientImpl("https://www.orcl.com/v3.1", "", ""); 

	protected final ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		objectMapper.registerModule(new Jdk8Module());
	}

	@Test
	public final void getEventlogsTest() throws JsonParseException, JsonMappingException, IOException {
		String executionId = "123a123a";
		String tenant = "thisTenant";
		List<CloudifyEvent> items = new ArrayList<CloudifyEvent>();
		CloudifyEvent aMockEvent = new CloudifyEvent("dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02", "dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02", 
				null, "workflow_succeeded", "5f8a2e05-e187-4925-90de-ece9160aa517", "warning", "ctx.7a10e191-f12b-4142-aa5d-6e5766ebb1d4",
				"install workflow execution succeeded", "publish_l36bhr", "publish", "cloudify.interfaces.lifecycle.create",
				"2019-02-28T23:17:49.228Z", "2019-02-28T23:17:49.700Z", "cloudify_event", "install");
		items.add(aMockEvent);
		items.add(
		new CloudifyEvent("dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02", "dcae_dtieventproc_idns-k8s-svc-blueprint_02_28_02", 
				null, "workflow_node_event", "5f8a2e05-e187-4925-90de-ece9160aa517", "warning", "ctx.7a10e191-f12b-4142-aa5d-6e5766ebb1d4",
				"Starting node", "publish_l36bhr", "publish", "cloudify.interfaces.lifecycle.create",
				"2019-02-28T23:17:48.391Z", "2019-02-28T23:17:48.516Z", "cloudify_event", "install"));
		
		String metaInfo = "metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}";
		Metadata metadata = null;
		//metadata = objectMapper.readValue(metaInfo, Metadata.class);

		CloudifyEventList expected = new CloudifyEventList(items, metadata);
		
		ResponseEntity<CloudifyEventList> response = new ResponseEntity<CloudifyEventList>(expected, HttpStatus.OK);
		Mockito.when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET), Matchers.<HttpEntity<?>>any(),
				Matchers.<ParameterizedTypeReference<CloudifyEventList>>any())).thenReturn(response);
		CloudifyEventList actual = subject.getEventlogs(executionId, tenant);
		
		assertTrue(actual.items.size() == 2);

	}
	@Test
	public final void testGetTenants_GetData() {
		// define the entity you want the exchange to return
		String tenantsList = "{\"items\": [{\"id\": 1, \"dName\": null, \"name\": \"default_tenant\"}, {\"id\": 2, \"dName\": null, \"name\": \"dyh1b1902\"}], \"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
		CloudifyTenantList sampleData = null;
		try {
			sampleData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
		} catch (Exception e) {
		}
		
		ResponseEntity<CloudifyTenantList> response = new ResponseEntity<CloudifyTenantList>(sampleData, HttpStatus.OK);
		Mockito.when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET), Matchers.<HttpEntity<?>>any(),
				Matchers.<ParameterizedTypeReference<CloudifyTenantList>>any())).thenReturn(response);

		CloudifyTenantList res = subject.getTenants();
		assertNotNull(res);
		assertThat(res.items.get(1).name, is("dyh1b1902"));
		// Assert.assertEquals(myobjectA, res.get(0));
	}

	@Test(expected = RestClientException.class)
	public final void testGetTenants_withException() {
		// define the entity you want the exchange to return
		Mockito.when(mockRest.exchange(Matchers.anyString(), Matchers.eq(HttpMethod.GET), Matchers.<HttpEntity<?>>any(),
				Matchers.<ParameterizedTypeReference<CloudifyTenantList>>any())).thenThrow(RestClientException.class);
	
		subject.getTenants();
		

	}
}
