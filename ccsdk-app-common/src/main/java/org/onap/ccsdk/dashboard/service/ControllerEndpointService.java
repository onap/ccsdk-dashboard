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

package org.onap.ccsdk.dashboard.service;

import java.util.List;

import org.onap.ccsdk.dashboard.domain.ControllerEndpoint;
import org.onap.ccsdk.dashboard.domain.EcdComponent;

/**
 * Provides methods for managing the user's selection of controller endpoint.
 * 
 * No method throws a checked exception, in keeping with the Spring philosophy
 * of throwing unchecked exceptions.
 */
public interface ControllerEndpointService {

	/**
	 * Gets the object for the specified user ID.
	 * 
	 * @param userId
	 *            Application user ID
	 * @return ControllerEndpointCredentials instance; null if none exists.
	 */
	ControllerEndpoint getControllerEndpointSelection(long userId);

	/**
	 * Creates or updates an entry for the user ID specified within the object.
	 * 
	 * @param endpoint
	 *            info to store.
	 */
	void updateControllerEndpointSelection(ControllerEndpoint endpoint);

	/**
	 * Deletes the object for the specified user ID.
	 * 
	 * @param userId
	 *            Application user ID
	 */
	void deleteControllerEndpointSelection(long userId);

	/**
	 * Gets all component names that are currently supported through
	 * ECOMPC dashboard
	 * 
	 * @return Component instance list;
	 */
	public List<EcdComponent> getComponents();

	/**
	 * 
	 * Add a new component to support in ECOMPC platform
	 * 
	 * @param component
	 */
	void insertComponent(EcdComponent component);
}
