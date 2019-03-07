package org.onap.ccsdk.dashboard.exceptions.inventory;

public class ServiceNotFoundException extends Exception {

	private static final long serialVersionUID = -8183806298586822720L;

	public ServiceNotFoundException() {	super(); }
	public ServiceNotFoundException (String message) { super(message); }
}
