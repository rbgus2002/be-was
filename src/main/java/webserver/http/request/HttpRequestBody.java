package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {

    private static final String PARAMETER_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private final Map<String, String> body;

    public HttpRequestBody(String body) {
        this.body = parseBody(body);
    }

    private Map<String, String> parseBody(String bodyString) {
        if (bodyString.equals("")) {
            return new HashMap<>();
        }

        Map<String, String> bodyMap = new HashMap<>();
        String[] params = bodyString.split(PARAMETER_SEPARATOR);
        for (String param : params) {
            String[] splitParam = param.split(KEY_VALUE_SEPARATOR);
            String key = splitParam[0].trim();
            String value = splitParam[1].trim();
            bodyMap.put(key, value);
        }

        return bodyMap;
    }

    public Map<String, String> getBody() {
        return body;
    }
}
