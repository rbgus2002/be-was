package webserver.http;

public enum Path {
    STATIC("src/main/resources/static"),
    TEMPLATES("src/main/resources/templates");

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
