package http;

public class HttpRequest {

    private final String method;
    private final String requestPath;
    private final String version;

    public HttpRequest(String method, String requestPath, String version) {
        this.method = method;
        this.requestPath = requestPath;
        this.version = version;
    }
}
