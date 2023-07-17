package webserver.http;

public class HttpRequest {
    private final String uri;

    public HttpRequest(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
