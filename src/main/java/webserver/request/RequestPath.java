package webserver.request;

import exception.InvalidPathException;

public class RequestPath {

    private final int INDEX_ADJUSTMENT = 1;
    private final String SLASH = "/";
    private final String[] paths;

    private RequestPath(String path) {
        this.paths = path.split("/");
    }

    public static RequestPath of(final String path) {
        return new RequestPath(path);
    }

    public String getPathSegment(final int idx) {
        if (idx < 0 || idx >= paths.length - INDEX_ADJUSTMENT) throw new InvalidPathException();
        return SLASH + paths[idx + INDEX_ADJUSTMENT];
    }
}
