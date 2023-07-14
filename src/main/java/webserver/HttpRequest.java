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
        String[] startLineTokens = startLine.split(" ");
        try {
            method = startLineTokens[0];
            parseTarget(startLineTokens[1]);
            version = startLineTokens[2];
        } catch (Exception ignored) {
        }
        while ((input = br.readLine()) != null && !input.isEmpty()) {
            String[] tokens = input.split(":");
            headers.put(tokens[0], tokens[1]);
        }
    }

    private void parseTarget(String target) {
        if (target.contains("?")) {
            String[] targetTonkens = target.split("\\?");
            url = targetTonkens[0];
            String[] split1 = targetTonkens[1].split("&");
            for (String s : split1) {
                String[] paramTokens = s.split("=");
                params.put(paramTokens[0], paramTokens[1]);
            }
        } else {
            url = target;
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
