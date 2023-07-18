package webserver.http.response;

public class HttpStatusLine {
    private final String version;
    private final int statusCode;
    private final String statusText;

    public HttpStatusLine(String version, int statusCode, String statusText) {
        this.version = version;
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public String getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }
}
