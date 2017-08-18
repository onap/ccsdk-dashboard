package org.onap.oom.dashboard.exception;

/**
 * A little something to placate the Sonar code-analysis tool.
 */
public class OOMDashboardException extends Exception {

	private static final long serialVersionUID = -1373841666122351816L;

	public OOMDashboardException() {
		super();
	}

	public OOMDashboardException(String message) {
		super(message);
	}

	public OOMDashboardException(String message, Throwable cause) {
		super(message, cause);
	}

	public OOMDashboardException(Throwable cause) {
		super(cause);
	}

}
