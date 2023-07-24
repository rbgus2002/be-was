package webserver.request;

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
}
