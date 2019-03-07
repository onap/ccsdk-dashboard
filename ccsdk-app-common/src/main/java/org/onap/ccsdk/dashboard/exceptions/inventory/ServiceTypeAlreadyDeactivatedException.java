package org.onap.ccsdk.dashboard.exceptions.inventory;

public class ServiceTypeAlreadyDeactivatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4359544421618429774L;

	public ServiceTypeAlreadyDeactivatedException (String message) {
		super(message);
	}
}
