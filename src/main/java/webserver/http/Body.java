package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.appendNewLine;

public class Body {
    private final Map<String, String> body = new HashMap<>();

    private Body(BufferedReader br, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        br.read(buffer);
        Uri.setQuery(body, String.valueOf(buffer));
    }

    public static Body of(BufferedReader br, int contentLength) throws IOException {
        return new Body(br, contentLength);
    }

    public Map<String, String> getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : body.keySet()) {
            sb.append(key + ": ").append(appendNewLine(body.get(key)));
        }
        return sb.toString();
    }
}
