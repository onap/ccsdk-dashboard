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
	 * @param serviceName
	 *            Service name
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
	 * Gets the status for all registered services running on the specified
	 * node.
	 * 
	 * @param nodeId
	 *            Node ID
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
	 * @param registration
	 *            Details about the service to be registered.
	 * @return Result of registering a service
	 */
	public String registerService(ConsulHealthServiceRegistration registration);
	
	/**
	 * Deregisters a service with Consul for health check.
	 * 
	 * @param serviceName
	 *            Name of the service to be deregistered.
	 * @return Response code
	 */
	public int deregisterService(String serviceName);

}
