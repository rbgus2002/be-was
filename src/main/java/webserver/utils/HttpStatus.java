package webserver.utils;

public enum HttpStatus {
	OK(200, "OK"),
	NOT_FOUND(400, "NOT FOUND");

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
