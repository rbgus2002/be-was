package webserver.request;

public class RequestLine {
    private final String requestLine;
    private final String method;
    private final String path;
    private final String version;

    public RequestLine(String line) {
        this.requestLine = line;
        String[] tokens = line.split(" ");
        this.method = tokens[0];
        this.path = tokens[1];
        this.version = tokens[2];
    }

    public String getRequestLine() {
        return requestLine;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }
}
