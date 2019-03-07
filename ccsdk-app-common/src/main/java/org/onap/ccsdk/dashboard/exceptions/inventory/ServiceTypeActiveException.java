package org.onap.ccsdk.dashboard.exceptions.inventory;

public class ServiceTypeActiveException extends Exception {

	private static final long serialVersionUID = 7403567744784579153L;

	public ServiceTypeActiveException() { super(); }
	public ServiceTypeActiveException(String msg) { super(msg); }
	public ServiceTypeActiveException(Throwable cause) { super(cause); }
	public ServiceTypeActiveException(String msg, Throwable cause) { super(msg, cause); }
}
