package org.onap.ccsdk.dashboard.exceptions.inventory;

public class ServiceTypeAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 8146558049192514157L;

	public ServiceTypeAlreadyExistsException() { super(); }
	public ServiceTypeAlreadyExistsException(String msg) { super(msg); }
	public ServiceTypeAlreadyExistsException(Throwable cause) { super(cause); }
	public ServiceTypeAlreadyExistsException(String msg, Throwable cause) { super(msg, cause); }
}
