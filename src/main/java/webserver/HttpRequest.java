package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String url;
    private String version;

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String startLine = br.readLine();
        String[] statusLineTokens = startLine.split(" ");
        method = statusLineTokens[0];
        parseTarget(statusLineTokens[1]);
        version = statusLineTokens[2];
        while ((input = br.readLine()) != null && in.available() > 0) {
            if (input.contains(":")) {
                String[] tokens = input.split(":");
                headers.put(tokens[0], tokens[1]);
            } else {
                String[] tokens = input.split(":");
                headers.put(tokens[0], tokens[1]);
                parseParam(br.readLine());
            }
        }
    }

    private void parseTarget(String target) {
        if (target.contains("?")) {
            String[] targetTonkens = target.split("\\?");
            url = targetTonkens[0];
            parseParam(targetTonkens[1]);
        } else {
            url = target;
        }
    }

    private void parseParam(String query) {
        String[] keyValues = query.split("&");
        for (String keyValue : keyValues) {
            String[] paramTokens = keyValue.split("=");
            params.put(paramTokens[0], paramTokens[1]);
        }
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
