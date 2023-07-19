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

    public static HttpStatusLine createStaticStatusLine() {
        return new HttpStatusLine("HTTP/1.1", 200, "OK");
    }

    public static HttpStatusLine createRedirectStatusLine() {
        return new HttpStatusLine("HTTP/1.1", 302, "FOUND");
    }
}
