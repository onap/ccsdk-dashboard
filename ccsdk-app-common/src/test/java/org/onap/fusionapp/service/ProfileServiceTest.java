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
package org.onap.fusionapp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.onap.fusion.core.MockApplicationContextTestSuite;
import org.onap.portalsdk.core.domain.Profile;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.service.ProfileService;
import org.onap.portalsdk.core.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;


public class ProfileServiceTest extends MockApplicationContextTestSuite {
	
	@Autowired
	ProfileService service;
	
	@Autowired
	UserProfileService userProfileService;
	
	@Test
    public void testFindAll() throws Exception {
		
		List<Profile> profiles = service.findAll();
		Assert.assertTrue(profiles.size() > 0);
	}

	@Test
	public void testFindAllActive() {
				
		List<User> users = userProfileService.findAllActive();
		List<User> activeUsers = userProfileService.findAllActive();
		Assert.assertTrue(users.size() - activeUsers.size() >= 0);
	}
}
