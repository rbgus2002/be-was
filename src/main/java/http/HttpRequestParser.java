package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);

    private HttpRequestParser() {
    }

    public static HttpRequest parseRequest(BufferedReader br) throws IOException {
        String method;
        String url;
        String version;
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();

        String input;
        String startLine = br.readLine();
        logger.debug(startLine);
        String[] statusLineTokens = startLine.split(" ");
        method = statusLineTokens[0];
        url = parseTarget(statusLineTokens[1], params);
        version = statusLineTokens[2];
        while ((input = br.readLine()) != null && !input.isEmpty()) {
            parseHeader(input, headers);
        }
        parseCookie(headers.get("Cookie"), cookies);
        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            int length = Integer.parseInt(contentLength);
            char[] buffer = new char[length];
            int bytesRead = br.read(buffer, 0, length);
            input = String.valueOf(buffer, 0, bytesRead);
            parseParam(input, params);
        }

        return new HttpRequest(method, url, version, headers, params, cookies);
    }

    private static void parseHeader(String input, Map<String, String> headers) {
        if (input.contains(":")) {
            String[] tokens = input.split("\\s*:\\s*");
            headers.put(tokens[0], tokens[1]);
        }
    }

    private static String parseTarget(String target, Map<String, String> params) {
        if (target.contains("?")) {
            String[] targetTokens = target.split("\\?");
            parseParam(targetTokens[1], params);
            return targetTokens[0];
        } else {
            return target;
        }
    }

    private static void parseParam(String query, Map<String, String> params) {
        String[] keyValues = query.split("&");
        for (String keyValue : keyValues) {
            String[] paramTokens = keyValue.split("=");
            params.put(paramTokens[0], paramTokens[1]);
        }
        logger.debug("{}", params);
    }

    private static void parseCookie(String cookie, Map<String, String> cookies) {
        for (String cookieLine : cookie.split("; ")) {
            String[] keyAndValue = cookieLine.split("=");
            cookies.put(keyAndValue[0], keyAndValue[1]);
        }
    }
}
