package webserver.http.message;

public enum HttpStatus {

	OK("200 OK"),
	CREATED("201 Created"),
	FOUND("302 Found"),
	BAD_REQUEST("400 Bad Request"),
	NOT_FOUND("404 Not Found"),
	METHOD_NOT_ALLOWED("405 Method Not Allowed"),
	SERVER_ERROR("500 Internal Server Error");

	private final String message;

	HttpStatus(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
