package http.statusline;

public class StatusLine {
	private static final String STATUS_LINE_DELIMETER = " ";
	private HttpVersion version;
	private StatusCode statusCode;

	public String getStatusLineForHeader() {
		return version.representation + STATUS_LINE_DELIMETER + statusCode.code + STATUS_LINE_DELIMETER
			+ statusCode.message;
	}

	public void setVersion(final HttpVersion version) {
		this.version = version;
	}

	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
}
