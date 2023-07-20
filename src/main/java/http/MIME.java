package http;

import java.util.HashMap;
import java.util.Map;

public class MIME {
    private static final Map<String, String> MIME = new HashMap<>();

    static {
        MIME.put("html", "text/html");
        MIME.put("css", "text/css");
        MIME.put("js", "application/javascript");
        MIME.put("json", "application/json");
        MIME.put("xml", "application/xml");
        MIME.put("ico", "image/x-icon");
        MIME.put("png", "image/png");
        MIME.put("jpg", "image/jpeg");
        MIME.put("gif", "image/gif");
        MIME.put("txt", "text/plain");
        MIME.put("eot", "application/vnd.ms-fontobject");
        MIME.put("svg", "image/svg+xml");
        MIME.put("ttf", "font/ttf");    // OR application/x-font-ttf
        MIME.put("woff", "font/woff");    // OR application/font-woff
        MIME.put("woff2", "font/woff2");
    }

    public static Map<String, String> getMIME() {
        return MIME;
    }

}
