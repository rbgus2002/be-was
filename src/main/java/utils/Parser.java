package utils;

import http.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//TODO: 추상화, 파싱해서 HttpRequest 객체 반환하도록 수정해보기
public class Parser {
    public static Map<String, String> parseRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        Map<String, String> requestLineMap = new HashMap<>();
        requestLineMap.put("method", tokens[0]);
        requestLineMap.put("uri", tokens[1]);
        requestLineMap.put("version", tokens[2]);
        return requestLineMap;
    }

    public static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        String line;
        Map<String, String> requestHeaders = new HashMap<>();

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(":");
            requestHeaders.put(header[0].trim(), header[1].trim());
        }

        return requestHeaders;
    }

    public static Map<String, String> parseQueryParameters(String queryString) {
        Map<String, String> queryParams = new HashMap<>();
        if (queryString.contains("?")) {
            queryString = queryString.substring(queryString.indexOf("?") + 1);
        }

        String[] params = queryString.split("&");
        for (String param : params) {
            int splitIndex = param.indexOf("=");
            queryParams.put(param.substring(0, splitIndex), URLDecoder.decode(param.substring(splitIndex + 1), StandardCharsets.UTF_8));
        }

        return queryParams;
    }

    public static String parseExtension(String requestUri) {
        String extension = requestUri.substring(requestUri.lastIndexOf(".") + 1);
        return extension.toUpperCase();
    }

    public static String parsePath(String requestUri) {
        String extension = parseExtension(requestUri);
        if (extension.equals("HTML")) {
            return Path.TEMPLATES.getPath() + requestUri;
        }
        return Path.STATIC.getPath() + requestUri;
    }

    public static String parseCookie(String requestCookie) {
        return requestCookie.substring(4);
    }
    public static String parseRequestCookie(String requestCookie) {
        return requestCookie.substring(6);
    }

    public static String parseRedirectViewPath(String viewPath) {
        return viewPath.substring(9);
    }
}
