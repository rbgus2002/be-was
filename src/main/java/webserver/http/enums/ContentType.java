package webserver.http.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/javascript"),
    ICON("image/x-icon"),
    TTF("application/x-font-ttf"),
    WOFF("application/x-font-woff"),
    WOFF2("application/font-woff2"),
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

    public String getTypeString() {
        return contentType;
    }

    public static ContentType getContentTypeByExtension(String extension) {
        if(extension == null) return contentTypeMap.get("default");
        return contentTypeMap.getOrDefault(extension, contentTypeMap.get("default"));
    }
}
