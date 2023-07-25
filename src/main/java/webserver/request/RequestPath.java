package webserver.request;

import exception.InvalidPathException;
import webserver.Constants.ContentType;

public class RequestPath {

    private final int ROOT_PATH_INDEX = 1;
    private final String SLASH = "/";
    private final String fullPath;
    private final String[] paths;

    private RequestPath(String path) {
        this.fullPath = path;
        this.paths = path.split("/");
    }

    public static RequestPath of(final String path) {
        return new RequestPath(path);
    }

    public String getRootPath() {
        return SLASH + paths[ROOT_PATH_INDEX];
    }

    public String getFullPath() {
        return this.fullPath;
    }

    public ContentType getContentType() {
        int idx = fullPath.lastIndexOf(".");
        if(idx == -1) throw new InvalidPathException();
        return ContentType.valueOf(fullPath.substring(idx + 1).toUpperCase());
    }
}
