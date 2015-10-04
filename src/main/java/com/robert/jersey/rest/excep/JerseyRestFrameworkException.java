package com.robert.jersey.rest.excep;

public class JerseyRestFrameworkException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	// Error Code

	public static final int JERSEY_REST_FRAMEWORK_EXCEP_COMM_ERROR_CODE = 10500;

	public static final int JERSEY_REST_FRAMEWORK_EXCEP_NOT_AUTHORIZED_CODE = 10403;

	// Exception Constants

	public static final JerseyRestFrameworkException JERSEY_REST_FRAMEWORK_EXCEP_COMM_ERROR = new JerseyRestFrameworkException(
			JERSEY_REST_FRAMEWORK_EXCEP_COMM_ERROR_CODE,
			"Jersey Rest Framework Exception: Common Error.");

	public static final JerseyRestFrameworkException JERSEY_REST_FRAMEWORK_EXCEP_NOT_AUTHORIZED = new JerseyRestFrameworkException(
			JERSEY_REST_FRAMEWORK_EXCEP_NOT_AUTHORIZED_CODE,
			"Jersey Rest Framework Exception: Not authorized.");

	// Fields

	protected int code = JERSEY_REST_FRAMEWORK_EXCEP_COMM_ERROR_CODE;

	// Constructs

	public JerseyRestFrameworkException(int code) {
		this.code = code;
	}

	public JerseyRestFrameworkException(int code, String message,
			Object... args) {
		super(String.format(message, args));
		this.code = code;
	}

	public JerseyRestFrameworkException(int code, String message, Throwable t,
			Object... args) {
		super(String.format(message, args));
		this.code = code;
	}
}
