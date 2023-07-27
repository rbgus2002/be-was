package model.enums;

public enum Mime {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/js"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpg"),
    JSON("application/json"),
    URL_ENCODED("application/x-www-form-urlencoded"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFF2("application/x-font-woff2"),
    DEFAULT("application/octet-stream"),
    ;

    private final String contentType;

    Mime(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public boolean isHTML() {
        return this == HTML;
    }
}
