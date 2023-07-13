package webserver;

public class HttpRequestLine {
    private String method;
    private String uri;
    private String version;

    private HttpRequestLine(String requestLine) {
        String[] token = requestLine.split(" ");
        this.method = token[0];
        this.uri = token[1];
        this.version = token[2];
    }

    public static HttpRequestLine from(String requestLine){
        return new HttpRequestLine(requestLine);
    }

    String getMethod() {
        return method;
    }

    String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return method + " " + uri + " " + version;
    }
}
