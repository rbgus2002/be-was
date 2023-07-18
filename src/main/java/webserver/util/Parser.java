package webserver.util;


import webserver.http.request.HttpMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static final String START_QUERY = "\\?";
    private static final String QUERY_PARSER = "&";
    private static final String KEY_VALUE_PARSER = "=";

    private static String parseQuery(String url) {
        String[] urlQuery = splitUrl(url);
        if (urlQuery.length == 1) {
            return null;
        }
        return urlQuery[1];
    }

    public static Map<String, String> parseFormData(String formData) {
        String[] queries = formData.split(QUERY_PARSER);
        HashMap<String, String> parseQueries = new HashMap<>();
        for (String query : queries) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
            String[] parse = query.split(KEY_VALUE_PARSER);
            parseQueries.put(parse[0], parse[1]);
        }
        return parseQueries;
    }

    private static String[] splitUrl(String url) {
        return url.split(START_QUERY);
    }

    private static String parseUrl(String url) {
        String[] urlQuery = splitUrl(url);
        return urlQuery[0];
    }

    public static String getUrlExtension(String url) {
        return url.contains(".") ? url.substring(url.lastIndexOf(".")) : null;
    }

    public static String[] parseUrlData(String url, HttpMethod method, String requestBody) {
        String tmpUrl;
        String tmpFormData = null;
        if (url.contains("?")) {
            if (method.equals(HttpMethod.GET)) {
                tmpFormData = parseQuery(url);
            }
            tmpUrl = parseUrl(url);
        } else {
            tmpUrl = url;
        }
        if (method.equals(HttpMethod.POST)) {
            tmpFormData = requestBody;
        }
        return new String[]{tmpFormData, tmpUrl};
    }
}
