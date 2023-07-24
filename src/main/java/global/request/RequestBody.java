package global.request;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBody {
    private static final String AND_PERCENT_SEPARATOR = "&";
    private static final String EQUAL_SEPARATOR = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final String body;

    public RequestBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getParams() {
        final Map<String, String> params = new LinkedHashMap<>();
        String[] queries = body.split(AND_PERCENT_SEPARATOR);
        for (String query : queries) {
            String[] split = query.split(EQUAL_SEPARATOR);
            params.put(split[KEY].trim(), split[VALUE].trim());
        }
        return params;
    }
}