package org.onap.ccsdk.dashboard.exceptions.inventory;

public class ServiceAlreadyDeactivatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7268552618026889672L;

	public ServiceAlreadyDeactivatedException (String message) {
		super(message);
	}
}
