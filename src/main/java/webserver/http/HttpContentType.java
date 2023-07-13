package webserver.http;

import java.util.concurrent.ConcurrentHashMap;

public class HttpContentType {

    private final ConcurrentHashMap<String, String> extension = new ConcurrentHashMap<>();

    public HttpContentType() {
        extension.put(".html", "text/html");
        extension.put(".css", "text/css");
        extension.put(".js", "text/javascript");
        extension.put(".woff", "application/x-font-woff");
        extension.put(".ttf", "application/x-font-ttf");
    }

    public String getContentType(String s) {
        return extension.get(s);
    }


}
