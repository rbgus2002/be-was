package util;

public enum PathList {
    STATIC_PATH("./src/main/resources/static"), TEMPLATE_PATH("./src/main/resources/templates"),
    HOME_PATH("/index.html"), FAILED_PATH("/user/login_failed.html");

    private final String path;

    PathList(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
