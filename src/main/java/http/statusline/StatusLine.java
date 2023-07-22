package http.statusline;

public class StatusLine {
	private HttpVersion version;
	private StatusCode statusCode;

	public String getStatusLineForHeader() {
		return version.representation + " " + statusCode.code + " " + statusCode.message;
	}

	public void setVersion(final HttpVersion version) {
		this.version = version;
	}

	public void setStatusCode(final StatusCode statusCode) {
		this.statusCode = statusCode;
	}
}
