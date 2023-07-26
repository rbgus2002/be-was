package http.response;

import static http.StatusCode.FOUND;
import static http.StatusCode.OK;

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
        return new HttpStatusLine("HTTP/1.1", OK.getValue(), OK.getDescription());
    }

    public static HttpStatusLine createRedirectStatusLine() {
        return new HttpStatusLine("HTTP/1.1", FOUND.getValue(), FOUND.getDescription());
    }
}
