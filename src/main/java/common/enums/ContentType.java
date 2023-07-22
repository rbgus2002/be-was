package common.enums;

public enum ContentType {
    JS("text/javascript; charset=utf-8"),
    PLAIN("text/plain; charset=utf-8"),
    HTML("text/html; charset=utf-8"),
    CSS("text/css; charset=utf-8"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpg"),
    EOT("application/vnd.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFF2("font/woff2"),
    NONE(""),
    ;

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStaticContent() {
        return this == JS || this == CSS || this == ICO || this == PNG || this == JPG ||
                this == EOT || this == SVG || this == TTF || this == WOFF || this == WOFF2;
    }

    public boolean isHtmlContent() {
        return this == HTML;
    }

    public boolean isNoneContent() {
        return this == NONE;
    }

}
