/*-
 * ================================================================================
 * ECOMP Portal SDK
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ================================================================================
 */
package org.onap.portalapp.service;

import java.util.Set;

import org.onap.portalapp.service.IAdminAuthExtension;
import org.onap.portalsdk.core.domain.Role;
import org.onap.portalsdk.core.domain.User;
import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("adminAuthExtension")
@Transactional
/**
 * Provides empty implementations of the methods in IAdminAuthExtension.
 */
public class AdminAuthExtension implements IAdminAuthExtension {

	EELFLoggerDelegate logger = EELFLoggerDelegate.getLogger(AdminAuthExtension.class);

	/*
	 * (non-Javadoc)
	 * @see org.onap.portalapp.service.IAdminAuthExtension#saveUserExtension(org.onap.portalsdk.core.domain.User)
	 */
	@Override
	public void saveUserExtension(User user) {
		logger.debug("saveUserExtension");
	}

	/*
	 * (non-Javadoc)
	 * @see org.onap.portalapp.service.IAdminAuthExtension#editUserExtension(org.onap.portalsdk.core.domain.User)
	 */
	@Override
	public void editUserExtension(User user) {
		logger.debug("editUserExtension");
	}

	/*
	 * (non-Javadoc)
	 * @see org.onap.portalapp.service.IAdminAuthExtension#saveUserRoleExtension(java.util.Set, org.onap.portalsdk.core.domain.User)
	 */
	@Override
	public void saveUserRoleExtension(Set<Role> roles, User user) {
		logger.debug("saveUserRoleExtension");
	}

}
