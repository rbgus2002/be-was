package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class Query {

    private final Map<String, String> query = new HashMap<>();

    public void appendQuery(String key, String value) {
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
        stringBuilder.append('?');
        query.forEach(
                (s, s2) -> stringBuilder.append(s)
                        .append("=")
                        .append(s2)
                        .append("&")
        );
        return stringBuilder.toString().replaceAll(".$", "");
    }
}
