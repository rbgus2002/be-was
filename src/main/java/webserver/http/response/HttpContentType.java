package webserver.http.response;

import com.google.common.collect.ImmutableMap;
import webserver.http.MimeType;

import java.util.HashMap;
import java.util.Map;

public class HttpContentType {

    private static final HttpContentType httpContentType = new HttpContentType();
    private final ImmutableMap<String, String> extension;

    private HttpContentType() {
        Map<String, String> tmpMap = new HashMap<>();
        for(MimeType mimeType : MimeType.values()) {
            tmpMap.put(mimeType.getExtension(), mimeType.getMimeType());
        }

        extension = ImmutableMap.<String, String>builder()
                .putAll(tmpMap)
                .build();
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
