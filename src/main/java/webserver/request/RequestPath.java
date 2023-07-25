package webserver.request;

import exception.unsupportedMediaType.InvalidContentTypeException;
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
        String fileExtension = fullPath.substring(idx + 1).toUpperCase();

        try {
            return ContentType.valueOf(fileExtension);
        } catch (IllegalArgumentException e) {
            throw new InvalidContentTypeException(fileExtension);
        }
    }
}
