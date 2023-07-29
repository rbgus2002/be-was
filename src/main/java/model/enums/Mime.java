package model.enums;

import static util.StringUtils.COMMA_MARK;
import static util.StringUtils.splitBy;

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

    public static Mime getValueOf(String uri) {
        String[] extension = splitBy(uri, COMMA_MARK);
        int extensionIndex = extension.length - 1;
        return Mime.valueOf(extension[extensionIndex].toUpperCase());
    }

    public String getContentType() {
        return this.contentType;
    }

    public boolean isHTML() {
        return this == HTML;
    }
}
