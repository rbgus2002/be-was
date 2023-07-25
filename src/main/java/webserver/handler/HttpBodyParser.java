package webserver.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpBodyParser {
    public static Map<String, String> parseBodyByContentType(String contentType, String body) {
        if (contentType.equals("application/x-www-form-urlencoded")) {
            return parseUrlEncoded(body);
        }
        throw new IllegalArgumentException("파싱할 수 없는 콘테츠 타입입니다.");
    }

    private static Map<String, String> parseUrlEncoded(String body) {
        Map<String, String> parsedData = new HashMap<>();
        String[] data = body.split("&");
        for (String datum : data) {
            String[] tokens = datum.split("=");
            if (tokens.length != 2) {
                throw new IllegalArgumentException("키 & 벨류 형태가 잘못되었습니다.");
            }
            parsedData.put(tokens[0], tokens[1]);
        }
        return Collections.unmodifiableMap(parsedData);
    }
}
