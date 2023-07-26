package was.webserver.utils;

public enum HttpHeader {

	CONTENT_TYPE("Content-Type"),
	CONTENT_LENGTH("Content-Length"),
	LOCATION("Location");

	private final String type;
	HttpHeader(final String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
