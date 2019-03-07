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

package org.onap.ccsdk.dashboard.domain;

import org.onap.portalsdk.core.domain.support.DomainVo;

/**
 * Model for controller endpoint information stored in database. A single row
 * for a user represents a selected endpoint.
 */
public class ControllerEndpoint extends DomainVo {

	private static final long serialVersionUID = 8785223545128054402L;

	private long userId;
	private String name;
	private String url;
	private String inventoryUrl;
	private String dhandlerUrl;

	public ControllerEndpoint() {
	}

	public ControllerEndpoint(long userId, String name, String url, String inventoryUrl, String dhandlerUrl) {
		this.userId = userId;
		this.name = name;
		this.url = url;
		this.inventoryUrl = inventoryUrl;
		this.dhandlerUrl = dhandlerUrl;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInventoryUrl() {
		return inventoryUrl;
	}

	public void setInventoryUrl(String inventoryUrl) {
		this.inventoryUrl = inventoryUrl;
	}

	public String getDhandlerUrl() {
		return dhandlerUrl;
	}

	public void setDhandlerUrl(String dhandlerUrl) {
		this.dhandlerUrl = dhandlerUrl;
	}
}
