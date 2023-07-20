package webserver.http.request;


import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;

import static utils.StringUtils.SPACE;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private Uri uri;
    private HttpMethod method;
    private String version;

    private RequestLine(Uri uri, HttpMethod method, String version) {
        this.uri = uri;
        this.method = method;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        logger.debug(requestLine);
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
}
