package webserver.http.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript"),
    ICON("image/x-icon"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFF2("font/woff2"),
    PNG("image/png"),
    JPEG("image/jpeg"),
    SVG("image/svg+xml"),
    PLAIN("text/plain");

    private String contentType;
    private static Map<String, ContentType> contentTypeMap = Maps.newHashMap();

    static {
        contentTypeMap.put(".html", HTML);
        contentTypeMap.put(".css", CSS);
        contentTypeMap.put(".js", JS);
        contentTypeMap.put(".ico", ICON);
        contentTypeMap.put(".ttf", TTF);
        contentTypeMap.put(".woff", WOFF);
        contentTypeMap.put(".woff2", WOFF2);
        contentTypeMap.put(".png", PNG);
        contentTypeMap.put(".jpeg", JPEG);
        contentTypeMap.put(".svg", SVG);
        contentTypeMap.put("default", PLAIN);
    }

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMIMEString() {
        if(this == HTML)
            return contentType + ";charset=utf-8";
        return contentType;
    }

    public static ContentType getContentTypeOfFile(String fileName) {
        if ("".equals(fileName) || !fileName.contains("."))
            return PLAIN;

        String extension = fileName.substring(fileName.lastIndexOf("."));

        return contentTypeMap.getOrDefault(extension, PLAIN);
    }
}
