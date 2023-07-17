package webserver.http;

import java.util.HashMap;

public class HttpContentType {

    private static final HttpContentType httpContentType = new HttpContentType();
    private final HashMap<String, String> extension = new HashMap<>();

    private HttpContentType() {
        for(MimeType mimeType : MimeType.values()) {
            extension.put(mimeType.getExtension(), mimeType.getMimeType());
        }
    }

    public static HttpContentType createHttpContentType() {
        return httpContentType;
    }

    public String getContentType(String s) {
        if(s == null) {
            return null;
        }
        return extension.get(s);
    }

}
