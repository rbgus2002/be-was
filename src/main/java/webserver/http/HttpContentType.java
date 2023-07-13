package webserver.http;

import java.util.concurrent.ConcurrentHashMap;

public class HttpContentType {

    private final ConcurrentHashMap<String, String> extension = new ConcurrentHashMap<>();

    public HttpContentType() {
        extension.put(MimeType.HTML.getExtension(), MimeType.HTML.getMimeType());
        extension.put(MimeType.CSS.getExtension(), MimeType.CSS.getMimeType());
        extension.put(MimeType.JS.getExtension(), MimeType.JS.getMimeType());
        extension.put(MimeType.WOFF.getExtension(), MimeType.WOFF.getMimeType());
        extension.put(MimeType.TTF.getExtension(), MimeType.TTF.getMimeType());
    }

    public String getContentType(String s) {
        return extension.get(s);
    }


}
