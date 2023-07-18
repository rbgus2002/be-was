package webserver.http.request;


import webserver.http.HttpMethod;

public class RequestLine {
    private Uri uri;
    private HttpMethod method;
    private String version;

    public RequestLine(Uri uri, HttpMethod method, String version) {
        this.uri = uri;
        this.method = method;
        this.version = version;
    }

    public String getPath() {
        return uri.getPath();
    }

    public String toString() {
        return method.name() + " " + uri.toString() + " " + version;
    }

    public String getVersion() {
        return version;
    }
}
