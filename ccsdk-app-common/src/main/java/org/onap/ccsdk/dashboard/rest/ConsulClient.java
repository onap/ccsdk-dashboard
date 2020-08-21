/*******************************************************************************
 * =============LICENSE_START=========================================================
 *
 * =================================================================================
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 *******************************************************************************/

package org.onap.ccsdk.dashboard.rest;

import java.util.List;

import org.onap.ccsdk.dashboard.model.consul.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.consul.ConsulDeploymentHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.consul.ConsulServiceInfo;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Defines the interface of the Consul REST client.
 */
public interface ConsulClient {

    /**
     * Gets all the services that are monitored by Consul.
     * 
     * @return List of ConsulServiceHealth
     */
    public List<ConsulServiceInfo> getServices(String datacenter) throws Exception;

    /**
     * Gets the status for the specified service on all nodes.
     * 
     * @param serviceName Service name
     * @return List of ConsulServiceHealth
     */
    public List<ConsulServiceHealth> getServiceHealth(String datacenter, String srvcName)
        throws Exception;

    /**
     * Gets the status for the service which corresponds to deployment Id on all nodes.
     * Filters services on Consul to find services that contain service tag that
     * matches the given deployment id
     * 
     * @param deploymentId Deployment Id
     * @return List of ConsulServiceHealth
     */
    public ConsulDeploymentHealth getServiceHealthByDeploymentId(String deploymentId)
        throws Exception;

    /**
     * Gets all the nodes that are monitored by Consul.
     * 
     * @return List of ConsulNodeHealth
     */
    public List<ConsulNodeInfo> getNodes(String datacenter) throws Exception;

    /**
     * Gets the status for all registered services running on the specified node.
     * 
     * @param nodeId Node ID
     * @return List of ConsulServiceHealth
     */
    public List<ConsulServiceHealth> getNodeServicesHealth(String datacenter, String nodeId)
        throws Exception;

    /**
     * Gets all the data centers that are monitored by Consul.
     * 
     * @return List of ConsulDatacenter objects
     */
    public List<ConsulDatacenter> getDatacenters();

}
