package model;

import java.util.HashMap;
import java.util.Map;

import static util.StringUtils.*;

public class RequestUri {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private String uri;
    private Map<String, Object> params;

    public RequestUri(String uri, Map<String, Object> params) {
        this.uri = uri;
        this.params = params;
    }

    public static RequestUri of(String text) {
        String[] splitByAmpersand = splitBy(text, AMPERSAND_MARK);
        String uri = splitByAmpersand[0];

        HashMap<String, Object> params = new HashMap<>();
        for (int i = 1; i < splitByAmpersand.length; i++) {
            String[] splitByEqualMark = splitBy(splitByAmpersand[i], EQUAL_MARK);
            String key = splitByEqualMark[KEY_INDEX];
            String value = splitByEqualMark[VALUE_INDEX];
            params.put(key, value);
        }

        return new RequestUri(uri, params);
    }

    public boolean match(String uri) {
        return this.uri.equals(uri);
    }
}
