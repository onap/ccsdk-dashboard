package org.onap.ccsdk.dashboard.exceptions.inventory;

public class ServiceTypeNotFoundException extends Exception {

	private static final long serialVersionUID = 1218738334353236154L;

	public ServiceTypeNotFoundException() { super(); }

	public ServiceTypeNotFoundException (String message) {
		super(message);
	}	
}
