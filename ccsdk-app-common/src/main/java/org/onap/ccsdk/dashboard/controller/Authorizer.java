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

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.onap.portalsdk.core.logging.logic.EELFLoggerDelegate;
import org.onap.portalsdk.core.web.support.AppUtils;

public class Authorizer {

	private static Authorizer authorizer = new Authorizer();
	private static final EELFLoggerDelegate LOGGER = EELFLoggerDelegate.getLogger(Authorizer.class);
	private static final String AUTH_PROP_FILE_NAME = "authorizer.properties";
	private static final String DCAE_ROLES_KEY = "dcae_roles";

	public static Authorizer getAuthorizer() {
		return authorizer;
	}
	
	public boolean isAuthorized(HttpServletRequest request) {
		final String method = request.getMethod();
		final String resource = request.getRequestURI();

		final Set<Authorizer.Role> authorizedRoles = getAuthorizedRoles(method, resource);

		// Anybody can access this page, no need to check
		if (authorizedRoles.contains(Role.ANY)) {
			return true;
		}

		final Set<Authorizer.Role> roles = getRoles(request);
		final Set<Authorizer.Role> intersection = new HashSet<> (roles);

		intersection.retainAll(authorizedRoles); // Removes all roles in roles that aren't contained in authorizedRoles.

		return !intersection.isEmpty(); //If the intersection is not empty, then this user is authorized
	}

	// Helper method to set roles
	public void putRoles(HttpServletRequest request, Set<Role> roles) {
		request.getSession().setAttribute(DCAE_ROLES_KEY, roles);
	}

	// Returns roles for the current user making the request
	@SuppressWarnings("unchecked")
	private Set<Authorizer.Role> getRoles(HttpServletRequest request) {

		// If roles is empty, then write the user's roles to the session
		if (request.getSession().getAttribute(DCAE_ROLES_KEY) == null) {

			// HashSet to be used to for putRoles
			HashSet<Role> roles = new HashSet<>();
			roles.add(Role.READER);
			
			// Get roles and turn into list of role objects
			HttpSession session = AppUtils.getSession(request);
			String roleType = (String)session.getAttribute("auth_role");
			if (roleType != null) {
				switch (roleType) {
					case "ADMIN": 	roles.add(Role.ADMIN);
									break;
					case "WRITE":	roles.add(Role.WRITER);
									break;
					case "READ": 	roles.add(Role.READER);
									break;
					default:		roles.add(Role.READER);
									break;
				}
			}
			// Write user roles
			putRoles(request, roles);
		}
		
		// Check if attribute DCAE_ROLES_KEY is valid
		final Object rawRoles = request.getSession().getAttribute(DCAE_ROLES_KEY);
		
		if (!(rawRoles instanceof Set<?>)) {
			throw new RuntimeException("Unrecognized object found in session for key=" + DCAE_ROLES_KEY);
		}

		return (Set<Authorizer.Role>) request.getSession().getAttribute(DCAE_ROLES_KEY);
	}

	// Returns roles authorized to perform the requested method (i.e. getAuthorizedRoles("POST", "/ecd-app-att/deployments"))
	private Set<Authorizer.Role> getAuthorizedRoles(String method, String resource) {
		final Properties resourceRoles = new Properties();

		try {
			resourceRoles.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(AUTH_PROP_FILE_NAME));

			final String[] splitMethodResourceKey = (method + resource.replace("/", ".")).split("\\.",0);
			final String methodResourceKey = splitMethodResourceKey[0] + "." + splitMethodResourceKey[2];
	
			if (!resourceRoles.containsKey(methodResourceKey)) {
				LOGGER.warn(AUTH_PROP_FILE_NAME + " does not contain roles for " + methodResourceKey + "; defaulting " + Authorizer.Role.ANY);
				return new HashSet<> (Collections.singleton(Role.ANY));
			}
	
			final String[] rawAuthorizedRoles = ((String) resourceRoles.get(methodResourceKey)).split(",");
			final Set<Authorizer.Role> authorizedRoles = new HashSet<> ();
	
			for (String rawAuthorizedRole : rawAuthorizedRoles) {
				authorizedRoles.add(Authorizer.Role.valueOf(rawAuthorizedRole));
			}
	
			return authorizedRoles;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public enum Role {
		ADMIN,
		READER,
		WRITER,
		ANY,
		NONE;
	}
	
	
}