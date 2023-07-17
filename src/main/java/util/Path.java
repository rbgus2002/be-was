package util;

public enum Path {
    STATIC_PATH("src/main/resources/static"), TEMPLATE_PATH("src/main/resources/template"),
    HOME_PATH("http://localhost:8080/index.html");

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
