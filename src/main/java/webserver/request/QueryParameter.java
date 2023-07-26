package webserver.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryParameter {

    private final Map<String, String> keyValues = new HashMap<>();

    public QueryParameter() {

    }

    public QueryParameter(String parameterString) {
        String[] queries = parameterString.split("&");
        Arrays.stream(queries)
                .forEach(keyValue -> {
                    String[] keyAndValue = keyValue.split("=", 2);
                    append(keyAndValue[0], keyAndValue[1]);
                });
    }

    private void append(String key, String value) {
        keyValues.put(key, value);
    }

    public String getValue(String key) {
        return keyValues.get(key);
    }

    public int size() {
        return keyValues.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        keyValues.forEach(
                (key, value) -> stringBuilder.append(key)
                        .append("=")
                        .append(value)
                        .append("&")
        );

        return stringBuilder.toString().replaceAll(".$", "");
    }

}
