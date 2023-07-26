package webserver.http.message;

public enum HttpStatus {

	OK("200 OK"),
	CREATED("201 Created"),
	BAD_REQUEST("400 Bad Request"),
	NOT_FOUND("404 Not Found"),
	METHOD_NOT_ALLOWED("405 Method Not Allowed");

	private final String message;

	HttpStatus(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
