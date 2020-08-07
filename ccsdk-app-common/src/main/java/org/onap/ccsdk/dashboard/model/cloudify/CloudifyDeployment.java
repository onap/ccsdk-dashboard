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
package org.onap.ccsdk.dashboard.model.cloudify;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.onap.ccsdk.dashboard.model.ECTransportModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model with fields only for the top-level attributes. All complex child
 * structures are represented simply as generic collections.
 */
public final class CloudifyDeployment extends ECTransportModel {

	/** A unique identifier for the deployment. */
	public final String id;
	public final String description;
	/** The id of the blueprint the deployment is based on. */
	public final String blueprint_id;
	/** The time when the deployment was created. */
	public final String created_at;
	/** The time the deployment was last updated at. */
	public final String updated_at;
	/**
	 * A dictionary containing key value pairs which represents a deployment
	 * input and its provided value.
	 */
	public final Map<String, Object> inputs;
	/** A dictionary containing policies of a deployment. */
	public final Map<String, Object> policy_types;
	/** A dictionary containing policy triggers of a deployment. */
	public final Map<String, Object> policy_triggers;
	/** A dictionary containing an outputs definition of a deployment. */
	public final Map<String, Object> outputs;
	/** A dictionary containing the groups definition of deployment. */
	public final Map<String, Object> groups;

	public final Map<String, Object> scaling_groups;
	/** A list of workflows that can be executed on a deployment. */
	public final List<Workflow> workflows;

	public final String tenant_name;
	
    /** internal role based setting */
    public boolean canDeploy;
    
    /** latest execution object */
    public CloudifyExecution lastExecution;
    /** install execution workflow status */
	//public String installStatus;
    /** true if helm plugin is used */
    public Boolean isHelm;
    /** true if helm status is enabled */
    public Boolean helmStatus;
    /** Consul service health status */
    public String healthStatus;
    /** blueprint owner */
    public String owner;

	@JsonCreator
	public CloudifyDeployment(@JsonProperty("description") String description,
			@JsonProperty("blueprint_id") String blueprint_id, @JsonProperty("created_at") String created_at,
			@JsonProperty("updated_at") String updated_at, @JsonProperty("id") String id,
			@JsonProperty("inputs") Map<String, Object> inputs, @JsonProperty("policy_types") Map<String, Object> policy_types,
			@JsonProperty("policy_triggers") Map<String, Object> policy_triggers,
			@JsonProperty("outputs") Map<String, Object> outputs, @JsonProperty("groups") Map<String, Object> groups,
			@JsonProperty("scaling_groups") Map<String, Object> scaling_groups,
			@JsonProperty("workflows") List<Workflow> workflows, @JsonProperty("tenant_name") String tenant_name) {
		this.description = description;
		this.blueprint_id = blueprint_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.id = id;
		this.inputs = inputs;
		this.policy_types = policy_types;
		this.policy_triggers = policy_triggers;
		this.outputs = outputs;
		this.groups = groups;
		this.scaling_groups = scaling_groups;
		this.workflows = workflows;
		this.tenant_name = tenant_name;
	}

	public static final class Inputs {
		public final String openstack_auth_url;
		public final String external_network_name;
		public final String openstack_username;
		public final String instance_image;
		public final String keypair_name;
		public final String instance_name;
		public final String keypair_private_key_path;
		public final String openstack_tenant_name;
		public final String subnet_name;
		public final String openstack_region;
		public final String openstack_password;
		public final String ssh_username;
		public final String instance_flavor;
		public final String network_name;

		@JsonCreator
		public Inputs(@JsonProperty("openstack_auth_url") String openstack_auth_url,
				@JsonProperty("external_network_name") String external_network_name,
				@JsonProperty("openstack_username") String openstack_username,
				@JsonProperty("instance_image") String instance_image,
				@JsonProperty("keypair_name") String keypair_name, @JsonProperty("instance_name") String instance_name,
				@JsonProperty("keypair_private_key_path") String keypair_private_key_path,
				@JsonProperty("openstack_tenant_name") String openstack_tenant_name,
				@JsonProperty("subnet_name") String subnet_name,
				@JsonProperty("openstack_region") String openstack_region,
				@JsonProperty("openstack_password") String openstack_password,
				@JsonProperty("ssh_username") String ssh_username,
				@JsonProperty("instance_flavor") String instance_flavor,
				@JsonProperty("network_name") String network_name) {

			this.openstack_auth_url = openstack_auth_url;
			this.external_network_name = external_network_name;
			this.openstack_username = openstack_username;
			this.instance_image = instance_image;
			this.keypair_name = keypair_name;
			this.instance_name = instance_name;
			this.keypair_private_key_path = keypair_private_key_path;
			this.openstack_tenant_name = openstack_tenant_name;
			this.subnet_name = subnet_name;
			this.openstack_region = openstack_region;
			this.openstack_password = openstack_password;
			this.ssh_username = ssh_username;
			this.instance_flavor = instance_flavor;
			this.network_name = network_name;
		}
	}

	public static final class Workflow {
		public final String name;
		public final String created_at;
		public final Map<String,Parameter> parameters;

		@JsonCreator
		public Workflow(@JsonProperty("name") String name, @JsonProperty("created_at") String created_at,
				@JsonProperty("parameters") Map<String,Parameter> parameters) {
			this.name = name;
			this.created_at = created_at;
			this.parameters = parameters;
		}
	}

	public static final class Parameter {
		
		@JsonProperty("default")
		public final Object xdefault;
		public final String description;

		@JsonCreator
		public Parameter(@JsonProperty("default") Object xdefault, @JsonProperty("description") String description) {
			this.xdefault = xdefault;
			this.description = description;
		}
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tenant_name == null) ? 0 : tenant_name.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CloudifyDeployment other = (CloudifyDeployment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (tenant_name == null) {
            if (other.tenant_name != null)
                return false;
        } else if (!tenant_name.equals(other.tenant_name))
            return false;
        return true;
    }

}
