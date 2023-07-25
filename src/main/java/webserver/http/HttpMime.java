package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum HttpMime {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "text/js"),
    ICO("ico", "image/x-icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpg"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "application/font-woff"),
    WOFF2("woff2", "application/font-woff2"),
    SVG("svg", "image/svg"),
    EOT("eot", "application/vnd.ms-fontobject");

    private static final Logger logger = LoggerFactory.getLogger(HttpMime.class);
    private final String extension;
    private final String contentType;

    HttpMime(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }
}
