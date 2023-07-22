package webserver;

public enum ContentType {

    HTML("text/html;charset=utf-8");

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
