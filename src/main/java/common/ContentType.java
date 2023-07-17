package common;

public enum ContentType {
    HTML("text/html;charset=utf-8"),
    CSS("text/css"),
    PNG("image/png"),
    JS("text/javascript");

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}