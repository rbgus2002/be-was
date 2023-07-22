package global.constant;

import exception.NotFoundExtensionException;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    ICO("ico", "image/vnd.microsoft.icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/png"),
    EOT("eot", "application/vnd.ms-fontobject"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    WOFF2("woff2", "font/woff2"),
    NONE("", "");

    private final String fileType;
    private final String contentType;

    ContentType(String fileType, String contentType) {
        this.fileType = fileType;
        this.contentType = contentType;
    }

    public static ContentType findContentType(String fileType) {
        return Arrays.stream(ContentType.values())
                .filter(type -> type.fileType.equalsIgnoreCase(fileType))
                .findFirst()
                .orElseThrow(NotFoundExtensionException::new);
    }

    public static boolean existContentType(String extension) {
        return Arrays.stream(ContentType.values())
                .anyMatch(type -> type.fileType.equalsIgnoreCase(extension));
    }


    public String getContentType() {
        return this.contentType;
    }
}
