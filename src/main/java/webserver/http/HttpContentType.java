package webserver.http;

import java.util.concurrent.ConcurrentHashMap;

public class HttpContentType {

    private final ConcurrentHashMap<String, String> extension = new ConcurrentHashMap<>();

    public HttpContentType() {

        for(MimeType mimeType : MimeType.values()) {
            extension.put(mimeType.getExtension(), mimeType.getMimeType());
        }

    }

    public String getContentType(String s) {
        return extension.get(s);
    }


}
