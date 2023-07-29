package model;

import com.google.common.net.HttpHeaders;

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

    public String stringify() {
        return mapToHeaderFormat(contents);
    }

    public int getContentLength() {
        // 10진수 바이트 단위
        if (contents.containsKey(HttpHeaders.CONTENT_TYPE)) {
            return Integer.parseInt(contents.get(HttpHeaders.CONTENT_LENGTH).trim());
        }
        return 0;
    }

    public void put(String key, String value) {
        contents.put(key, value);
    }

    public String get(String key) {
        return contents.get(key);
    }
}
