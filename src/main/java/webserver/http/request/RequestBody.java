package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.AMPERSAND;
import static utils.StringUtils.EQUAL;

public class RequestBody {
    private Map<String, String> params;

    private RequestBody(Map<String, String> params) {
        this.params = params;
    }

    public static RequestBody from(BufferedReader bufferedReader, int contentLength) throws IOException {
        Map<String, String> params = new LinkedHashMap<>();
        String body = readBody(bufferedReader, contentLength);
        if (!body.isEmpty()) {
            String[] pairs = body.split(AMPERSAND);
            for (String pair : pairs) {
                String[] keyValue = pair.split(EQUAL);
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return new RequestBody(params);
    }

    private static String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        return new String(buffer);
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void appendBody(StringBuilder stringBuilder) {
        if (!params.isEmpty()) {
            stringBuilder.append("?");
            params.forEach((key, value) -> stringBuilder.append(key + EQUAL + value + AMPERSAND));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }
}
