package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {

    private final Map<String, String> body;

    public HttpRequestBody(String body) {
        this.body = parseBody(body);
    }

    private Map<String, String> parseBody(String bodyString) {
        if (bodyString.equals("")) {
            return new HashMap<>();
        }

        Map<String, String> bodyMap = new HashMap<>();
        String[] params = bodyString.split("&");
        for (String param : params) {
            String[] splitParam = param.split("=");
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
