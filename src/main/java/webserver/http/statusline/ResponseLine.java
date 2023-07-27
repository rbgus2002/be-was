package webserver.http.statusline;

public class ResponseLine {
	public static final String STATUS_LINE_DELIMETER = " ";
	private HttpVersion version;
	private StatusCode statusCode;

	public ResponseLine() {
		version = HttpVersion.HTTP_1_1;
		statusCode = StatusCode.OK;
	}

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
