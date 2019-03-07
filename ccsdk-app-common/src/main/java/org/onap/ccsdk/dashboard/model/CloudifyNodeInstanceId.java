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
package org.onap.ccsdk.dashboard.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model with fields only for the top-level attributes. All complex child
 * structures are represented as generic collections.
 */
public final class CloudifyNodeInstanceId extends ECTransportModel {

	/** The id of the node instance. */
	public final String id;
	


	/** The name of the user that created the node instance */
	//public final String created_by;
	/** The id of the deployment the node instance belongs to. */
	//public final String deployment_id;
	/** The Compute node instance id the node is contained within. */
	//public final String host_id;
	/** The relationships the node has with other nodes. */
	//public final List relationships;
	/** The runtime properties of the node instance. */
	//public final String runtime_properties;
	/** The node instance state. */
	//public final String state;
	/** The name of the tenant that owns the node instance. */
	//public final String tenant_name;
	/** A version attribute used for optimistic locking when updating the node instance. */
	//public final String version;


	@JsonCreator
	public CloudifyNodeInstanceId(@JsonProperty("id") String id) {
		this.id = id;
	}

}
