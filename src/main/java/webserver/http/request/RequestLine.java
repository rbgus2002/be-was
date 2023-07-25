package webserver.http.request;


import model.User;
import webserver.http.HttpMethod;
import webserver.http.MIME;

import static utils.StringUtils.SPACE;

public class RequestLine {
    private Uri uri;
    private HttpMethod method;
    private String version;

    private RequestLine(Uri uri, HttpMethod method, String version) {
        this.uri = uri;
        this.method = method;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        String[] tokens = requestLine.split(SPACE);
        Uri uri = Uri.from(tokens[1]);
        return new RequestLine(uri, HttpMethod.valueOf(tokens[0]), tokens[2]);
    }

    public String getPath() {
        return uri.getPath();
    }

    public String toString() {
        return method.name() + " " + uri.toString() + " " + version;
    }

    public User createUserFromQuery() {
        return uri.createUserFromQuery();
    }

    public String getVersion() {
        return version;
    }

    public boolean isMatchHandler(HttpMethod method, String path) {
        return this.method == method && uri.isSamePath(path);
    }

    public MIME getMime() {
        return uri.getMime();
    }
}
