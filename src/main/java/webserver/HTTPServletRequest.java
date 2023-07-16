package webserver;

public class HTTPServletRequest {
    private String method;
    private String uri;

    public HTTPServletRequest(String startLine) {
        method = parseMethod(startLine);
        uri = parseUri(startLine);
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    private String parseUri(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[0];
    }

    private String parseMethod(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[1];
    }
}
