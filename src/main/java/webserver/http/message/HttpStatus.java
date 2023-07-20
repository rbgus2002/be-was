package webserver.http.message;

public enum HttpStatus {

	OK("200", "OK"),
	NOT_FOUND("404", "Not Found");

	private final String code;
	private final String message;

	HttpStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
