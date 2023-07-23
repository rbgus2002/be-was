package webserver.http.message;

import java.util.Arrays;

public enum Mime {
    HTML("html", "text/html"),
    JAVA_SCRIPT("js", "application/javascript"),
    CSS("css", "text/css"),
    ICO("ico", "image/x-icon"),
    SVG("svg", "image/svg+xml"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    EOT("eot", "application/vnd.ms-fontobject"),
    TTF("ttf", "application/x-font-ttf"),
    WOFF("woff","application/x-font-woff"),
    WOFF2("woff2", "font/woff2"),
    DEFAULT("", "application/octet-stream");

    private final String ext;
    private final String contentType;

    Mime(String ext, String contentType) {
        this.ext = ext;
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static Mime findByExt(String ext) {
        return Arrays.stream(Mime.values())
                .filter(mime -> ext.equals(mime.ext))
                .findFirst()
                .orElse(Mime.DEFAULT);
    }

    public static Mime findByContentType(String contentType) {
        return Arrays.stream(Mime.values())
                .filter(mime -> contentType.equals(mime.contentType))
                .findFirst()
                .orElse(Mime.DEFAULT);
    }
}
