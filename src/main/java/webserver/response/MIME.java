package webserver.response;

import java.util.HashMap;
import java.util.Map;

public abstract class MIME {

    public static final Map<String, String> MIME = new HashMap<>();

    static {
        MIME.put(".html", "text/html");
        MIME.put(".css", "text/css");
        MIME.put(".js", "application/javascript");
        MIME.put(".json", "application/json");
        MIME.put(".xml", "application/xml");
        MIME.put(".ico", "image/x-icon");
        MIME.put(".png", "image/png");
        MIME.put(".jpg", "image/jpeg");
        MIME.put(".gif", "image/gif");
        MIME.put(".txt", "text/plain");
    }

    public static String getContentType(String extension) {
        return MIME.getOrDefault(extension, "text/plain");
    }

}

