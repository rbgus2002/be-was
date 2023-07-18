package common.enums;

public enum ContentType {
    JS("text/javascript; charset=utf-8"),
    PLAIN("text/plain; charset=utf-8"),
    HTML("text/html; charset=utf-8"),
    CSS("text/css; charset=utf-8"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpg"),
    NONE(""),
    ;

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHtmlContent() {
        return this == HTML;
    }

    public boolean isStaticContent() {
        return this == JS || this == CSS || this == ICO || this == PNG || this == JPG;
    }

    public boolean isNoneContent() {
        return this == NONE;
    }
}