package webserver.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class KeyValue {

    private final Map<String, String> query = new HashMap<>();
    private final boolean isQuery;

    protected KeyValue(boolean isQuery) {
        this.isQuery = isQuery;
    }

    protected KeyValue(String queryString, boolean isQuery) {
        String[] queries = queryString.split("&");
        Arrays.stream(queries)
                .forEach(queryComponent -> {
                    String[] keyAndValue = queryComponent.split("=");
                    appendQuery(keyAndValue[0], keyAndValue[1]);
                });
        this.isQuery = isQuery;
    }

    private void appendQuery(String key, String value) {
        this.query.put(key, value);
    }

    public String getValue(String key) {
        return this.query.get(key);
    }

    public int size() {
        return this.query.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isQuery) {
            stringBuilder.append('?');
        }
        query.forEach(
                (s, s2) -> stringBuilder.append(s)
                        .append("=")
                        .append(s2)
                        .append("&")
        );
        return stringBuilder.toString().replaceAll(".$", "");
    }

}
