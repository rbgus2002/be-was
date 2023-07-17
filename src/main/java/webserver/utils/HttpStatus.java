package webserver.utils;

public enum HttpStatus {
	OK(200, "OK"),
	NOT_FOUND(404, "NOT FOUND"),
	FOUND(302, "FOUND"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed");

	private final int statusCode;
	private final String name;

	HttpStatus(final int statusCode, final String name) {
		this.statusCode = statusCode;
		this.name = name;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getName() {
		return name;
	}
}
