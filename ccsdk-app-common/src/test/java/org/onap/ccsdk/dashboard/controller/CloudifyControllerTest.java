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
package org.onap.ccsdk.dashboard.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.onap.ccsdk.dashboard.core.MockUser;
import org.onap.ccsdk.dashboard.model.CloudifyTenantList;
import org.onap.ccsdk.dashboard.rest.CloudifyClient;
import org.onap.ccsdk.dashboard.core.MockitoTestSuite;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.web.support.UserUtils;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.http.MediaType;


public class CloudifyControllerTest extends MockitoTestSuite {

	@Mock
	private CloudifyClient restClient;
	
	@InjectMocks
	private CloudifyController subject = new CloudifyController();
	
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	@Mock
	UserUtils userUtils = new UserUtils();

	@Mock
	User epuser;
	
	MockUser mockUser = new MockUser();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		objectMapper.registerModule(new Jdk8Module());
	}
	
	@Test
	public final void testGetControllerEndpoints_stubbed() {
		
	}

	@Test
	public final void testGetTenants_stubbed() throws Exception {
		
		String tenantsList = 
				"{\"items\": [{\"id\": 1, \"name\": \"default_tenant\", \"dName\": \"default_tenant\" }, "
				+ "{\"id\": 2, \"name\": \"dyh1b1902\", \"dName\": \"dyh1b1902\"}], "
				+ "\"metadata\": {\"pagination\": {\"total\": 2, \"offset\": 0, \"size\": 0}}}";
		CloudifyTenantList sampleData = null;
		try {
			sampleData = objectMapper.readValue(tenantsList, CloudifyTenantList.class);
		} catch (Exception e) {
		}

		User user = mockUser.mockUser();
		user.setLoginId("tester");
		MockHttpServletRequestWrapper mockedRequest = getMockedRequest();
        
		Mockito.when(UserUtils.getUserSession(mockedRequest)).thenReturn(user);
		Mockito.when(restClient.getTenants()).thenReturn(sampleData);
		
		RequestBuilder request = MockMvcRequestBuilders.
				get("/tenants").
				accept(MediaType.APPLICATION_JSON);
		
		String tenantStr = 
				subject.getTenants(mockedRequest);

		assertNotNull(tenantStr);
		assertTrue(tenantStr.contains("dyh1b"));


	}

}
