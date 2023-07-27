package http;

public enum Path {
    STATIC("src/main/resources/static"),
    TEMPLATES("src/main/resources/templates"),
    DYNAMIC("src/main/java/view");

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
