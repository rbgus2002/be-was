package common;

public enum ContentType {
    HTML("text/html; charset=utf-8"),
    CSS("text/css; charset=utf-8"),
    JS("text/javascript; charset=utf-8"),
    PNG("image/png"),
    JPG("image/jpg"),
    ICO("image/x-icon"),
    NONE(""),
    ;

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}