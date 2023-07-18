package model;

import java.util.HashMap;
import java.util.Map;

import static util.StringUtils.*;

public class HttpHeader {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private final Map<String, String> contents;

    private HttpHeader(Map<String, String> contents) {
        this.contents = contents;
    }

    public static HttpHeader of(Map<String, String> contents) {
        return new HttpHeader(contents);
    }

    public static HttpHeader of(String[] texts) {
        HashMap<String, String> contents = new HashMap<>();
        for (var text : texts) {
            String[] splited = splitBy(text, COLON_MARK);
            String key = splited[KEY_INDEX];
            String value = splited[VALUE_INDEX];
            contents.put(key, value);
        }
        return new HttpHeader(contents);
    }

    public HttpHeader newInstance() {
        // ??
        return new HttpHeader(new HashMap<String, String>(this.contents));
    }

    public String stringfy() {
        return mapToHeaderFormat(contents);
    }
}
