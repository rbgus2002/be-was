package http;

public enum StatusCode {
	OK("200 OK"),
	BAD_REQUEST("400 Bad Request"),
	FORBIDDEN("403 Forbidden"),
	NOT_FOUND("404 Not Found");

	public final String message;

	StatusCode(String message) {
		this.message = message;
	}
}
