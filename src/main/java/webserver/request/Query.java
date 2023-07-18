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

}
