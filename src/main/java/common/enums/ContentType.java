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

    public boolean isPlainContent() {
        return this == PLAIN;
    }

    public boolean isNoneContent() {
        return this == NONE;
    }

    public static boolean isStaticContent(String viewName) {
        return viewName.endsWith(".css") || viewName.endsWith(".js") ||
                viewName.endsWith(".jpg") || viewName.endsWith(".png") ||
                viewName.endsWith(".ico") || viewName.endsWith(".eot") ||
                viewName.endsWith(".svg") || viewName.endsWith(".ttf") ||
                viewName.endsWith(".woff") || viewName.endsWith(".woff2");
    }

    public static boolean isHtmlContent(String viewName) {
        return viewName.endsWith("html");
    }

}
