package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Parser {

    private Parser() {
    }

    public static String parseUrlFromRequestLine(String requestLine) {
        int firstSpaceIndex = requestLine.indexOf(" ");
        int secondSpaceIndex = requestLine.indexOf(" ", firstSpaceIndex + 1);

        return requestLine.substring(firstSpaceIndex + 1, secondSpaceIndex);
    }

    public static String parsePathFromUrl(String url) {
        if(url.equals("/")) {
            return "/index.html";
        }

        int queryIndex = url.indexOf("?");
        if(queryIndex == -1) {
            return url;
        }
        return url.substring(0, queryIndex);
    }

    public static Map<String, String> parseParamsFromUrl(String url) {
        Map<String, String> params = new HashMap<>();

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

    public static Map<String, String> parseParamsFromBody(String body) {
        Map<String, String> params = new HashMap<>();

        StringTokenizer tokenizer = new StringTokenizer(body, "&");
        while (tokenizer.hasMoreTokens()) {
            String query = tokenizer.nextToken();
            int equalIndex = query.indexOf("=");

            String key = query.substring(0,equalIndex);
            String value = query.substring(equalIndex + 1);
            params.put(key, value);
        }
        return params;
    }

    public static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String[] tokens;
        String requestLine = br.readLine();

        while(!"".equals(requestLine) && requestLine != null){
            tokens = requestLine.split(": " );
            String key = tokens[0];
            String value = tokens[1];
            headers.put(key, value);
            requestLine = br.readLine();
        }

        return headers;
    }

    public static String parseBody(BufferedReader br, int bodyLength) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[bodyLength];
        br.read(buffer, 0, bodyLength);
        sb.append(buffer);

        return sb.toString();
    }

    public static String getExtension(String path){
        String[] tokens = path.split("\\.");
        return tokens[tokens.length - 1];
    }
}
