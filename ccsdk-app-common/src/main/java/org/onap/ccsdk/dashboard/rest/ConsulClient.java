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

import java.util.List;

import org.onap.ccsdk.dashboard.model.ConsulDatacenter;
import org.onap.ccsdk.dashboard.model.ConsulHealthServiceRegistration;
import org.onap.ccsdk.dashboard.model.ConsulNodeInfo;
import org.onap.ccsdk.dashboard.model.ConsulServiceHealth;
import org.onap.ccsdk.dashboard.model.ConsulServiceInfo;

/**
 * Defines the interface of the Consul REST client.
 */
public interface ConsulClient {

    /**
     * Gets all the services that are monitored by Consul.
     * 
     * @return List of ConsulServiceHealth
     */
    public List<ConsulServiceInfo> getServices(String datacenter);

    /**
     * Gets the status for the specified service on all nodes.
     * 
     * @param serviceName Service name
     * @return List of ConsulServiceHealth
     */
    public List<ConsulServiceHealth> getServiceHealth(String datacenter, String srvcName);

    /**
     * Gets all the nodes that are monitored by Consul.
     * 
     * @return List of ConsulNodeHealth
     */
    public List<ConsulNodeInfo> getNodes(String datacenter);

    /**
     * Gets the status for all registered services running on the specified node.
     * 
     * @param nodeId Node ID
     * @return List of ConsulServiceHealth
     */
    public List<ConsulServiceHealth> getNodeServicesHealth(String datacenter, String nodeId);

    /**
     * Gets all the data centers that are monitored by Consul.
     * 
     * @return List of ConsulDatacenter objects
     */
    public List<ConsulDatacenter> getDatacenters();

    /**
     * Registers a service with Consul for health check.
     * 
     * @param registration Details about the service to be registered.
     * @return Result of registering a service
     */
    public String registerService(ConsulHealthServiceRegistration registration);

    /**
     * Deregisters a service with Consul for health check.
     * 
     * @param serviceName Name of the service to be deregistered.
     * @return Response code
     */
    public int deregisterService(String serviceName);

}
