package org.onap.ccsdk.dashboard.exception;

/**
 * A little something to placate the Sonar code-analysis tool.
 */
public class DashboardControllerException extends Exception {

	private static final long serialVersionUID = -1373841666122351816L;

	public DashboardControllerException() {
		super();
	}

	public DashboardControllerException(String message) {
		super(message);
	}

	public DashboardControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public DashboardControllerException(Throwable cause) {
		super(cause);
	}

}
