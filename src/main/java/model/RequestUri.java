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

    private final String uri;
    private final Map<String, Object> params;

    public RequestUri(String uri, Map<String, Object> params) {
        this.uri = uri;
        this.params = params;
    }

    public static RequestUri of(String text) {
        String[] splitByQuestionMark = splitBy(text, QUESTION_MARK); // /uri?a=a&b=2&c=
        String uri = splitByQuestionMark[URI_INDEX];

        HashMap<String, Object> params = new HashMap<>();
        if(splitByQuestionMark.length == LENGTH_WHEN_HAS_NO_VALUE) return new RequestUri(uri, params);

        String[] paramArray = splitBy(splitByQuestionMark[PARAMETERS_INDEX], AMPERSAND_MARK);
        for (int i = 0; i < paramArray.length; i++) {
            String[] splitByEqualMark = splitBy(paramArray[i], EQUAL_MARK);

            if (splitByEqualMark.length == LENGTH_WHEN_HAS_NO_VALUE) {
                continue;
            }
            String key = splitByEqualMark[KEY_INDEX];
            String value = splitByEqualMark[VALUE_INDEX];
            params.put(key, value);
        }

        return new RequestUri(uri, params);
    }

    public boolean match(String uri) {
        return this.uri.equals(uri);
    }

    public boolean uriEndsWith(String s) {
        return uri.endsWith(s);
    }

}
