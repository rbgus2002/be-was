package webserver.request;

import webserver.exception.InvalidPathLengthException;

public class RequestPath {

    private final int INDEX_ADJUSTMENT = 1;
    private final String SLASH = "/";
    private final String[] paths;

    private RequestPath(String path) {
        this.paths = path.split("/");
    }

    public static RequestPath of(String path) {
        return new RequestPath(path);
    }

    public String getPathSegment(int idx) {
        if (idx < 0 || idx >= paths.length - INDEX_ADJUSTMENT) throw new InvalidPathLengthException();
        return SLASH + paths[idx + INDEX_ADJUSTMENT];
    }
}
