package org.onap.ccsdk.dashboard.model.inventory;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceRequest {

	/** ID of the associated service type */
	public String typeId;
	/** Id of the associated VNF that this service is monitoring */
	public String vnfId;
	/** The type of the associated VNF that this service is monitoring */
	public String vnfType;
	/** Location identifier of the associated VNF that this service is monitoring */
	public String vnfLocation;
	/** Reference to a Cloudify deployment */
	public String deploymentRef;
	/** Collection of ServiceComponentRequest objects that this service is composed of */
	public Collection<ServiceComponentRequest> components;
	
	@JsonCreator
	public ServiceRequest(@JsonProperty("typeId") String typeId, 
			@JsonProperty("vnfId") String vnfId, 
			@JsonProperty("vnfType") String vnfType, 
			@JsonProperty("vnfLocation") String vnfLocation, 
			@JsonProperty("deploymentRef") String deploymentRef, 
			@JsonProperty("components") Collection<ServiceComponentRequest> components) {
		this.typeId = typeId;
		this.vnfId = vnfId;
		this.vnfType = vnfType;
		this.vnfLocation = vnfLocation;
		this.deploymentRef = deploymentRef;
		this.components = components;
	}

	public static ServiceRequest from(String typeId, Service service) {

		// Convert the Collection<ServiceComponent> in service to Collection<ServiceComponentRequest> for serviceRequest
		final Collection<ServiceComponent> serviceComponents = service.getComponents();
		final Collection<ServiceComponentRequest> serviceComponentRequests = new ArrayList<ServiceComponentRequest> ();

		for (ServiceComponent sc : serviceComponents) {
			serviceComponentRequests.add(ServiceComponentRequest.from(sc));
		}

		return new ServiceRequest(typeId, 
				service.getVnfId(), 
				service.getVnfType(), 
				service.getVnfLocation(), 
				service.getDeploymentRef(), 
				serviceComponentRequests
		);
	}
}
