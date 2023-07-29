package model;

import java.util.HashMap;
import java.util.Map;

import static util.StringUtils.*;

public class RequestUri {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int URI_INDEX = 0;
    private static final int PARAMETERS_INDEX = 1;
    private static final int LENGTH_WHEN_HAS_NO_VALUE = 1;
    private static final String COMMA = ".";

    private final String uri;
    private final Map<String, Object> params;

    private RequestUri(String uri, Map<String, Object> params) {
        this.uri = uri;
        this.params = params;
    }

    private RequestUri(String uri) {
        this.uri = uri;
        this.params = new HashMap<>();
    }

    public static RequestUri of(String text) {
        String[] splitByQuestionMark = splitBy(text, QUESTION_MARK); // /uri?a=a&b=2&c=
        String uri = splitByQuestionMark[URI_INDEX];

        if (isArrayLengthOne(splitByQuestionMark)) return new RequestUri(uri);

        HashMap<String, Object> params = new HashMap<>();
        String[] paramArray = splitBy(splitByQuestionMark[PARAMETERS_INDEX], AMPERSAND_MARK);
        for (int i = 0; i < paramArray.length; i++) {
            String[] paramMap = splitBy(paramArray[i], EQUAL_MARK);

            if (isArrayLengthOne(paramMap)) continue;

            String key = paramMap[KEY_INDEX];
            String value = paramMap[VALUE_INDEX];
            params.put(key, value);
        }

        return new RequestUri(uri, params);
    }

    private static boolean isArrayLengthOne(String[] array) {
        return array.length == LENGTH_WHEN_HAS_NO_VALUE;
    }

    public boolean match(String uri) {
        return this.uri.equals(uri);
    }

    public boolean isUriStaticFile() {
        return uri.contains(COMMA);
    }

    public String getUri() {
        return this.uri;
    }

    @Override
    public String toString() {
        String s = "path uri = " + uri + "\n";
        for (var entry : params.entrySet()) {
            s += "[key = " + entry.getKey() + " / value = " + entry.getValue() + "]\n";
        }
        return s;
    }
}
