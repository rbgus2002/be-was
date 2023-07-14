package http;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestParser {

    private HttpRequestParser() {
    }

    public static String parseMethodFromRequestLine(String requestLine) {
        int firstSpaceIndex = requestLine.indexOf(" ");
        return requestLine.substring(0, firstSpaceIndex);
    }

    public static String parseUrlFromRequestLine(String requestLine) {
        int firstSpaceIndex = requestLine.indexOf(" ");
        int secondSpaceIndex = requestLine.indexOf(" ", firstSpaceIndex + 1);

        return requestLine.substring(firstSpaceIndex + 1, secondSpaceIndex);
    }

    public static String parsePathFromRequestLine(String requestLine) {
        String url = parseUrlFromRequestLine(requestLine);
        int queryIndex = url.indexOf("?");
        if(queryIndex == -1) {
            return url;
        }
        return url.substring(0, queryIndex);
    }

    public static String parseVersionFromRequestLine(String requestLine) {
        int firstSpaceIndex = requestLine.indexOf(" ");
        int secondSpaceIndex = requestLine.indexOf(" ", firstSpaceIndex + 1);

        return requestLine.substring(secondSpaceIndex + 1);
    }

    public static Map<String, String> parseParamsFromRequestLine(String requestLine) {
        Map<String, String> params = new HashMap<>();

        String url = parseUrlFromRequestLine(requestLine);
        int queryIndex = url.indexOf("?");
        if(queryIndex == -1) {
            return params;
        }
        String queryString = url.substring(queryIndex + 1);
        StringTokenizer tokenizer = new StringTokenizer(queryString, "&");
        while (tokenizer.hasMoreTokens()) {
            String query = tokenizer.nextToken();
            int equalIndex = query.indexOf("=");

            String key = query.substring(0,equalIndex);
            String value = query.substring(equalIndex + 1);
            params.put(key, value);
        }
        return params;
    }
}
