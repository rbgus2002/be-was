package webserver.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public static final String START_QUERY = "\\?";
    private static final String QUERY_PARSER = "&";
    private static final String KEY_VALUE_PARSER = "=";

    public static Map<String,String> parseQuery(String url) {
        if(!url.contains("?")) {
            return null;
        }
        String[] urlQuery = splitUrl(url);
        String entireQuery = urlQuery[1];
        return parseData(entireQuery);
    }

    private static Map<String, String> parseData(String entireQuery) {
        String[] queries = entireQuery.split(QUERY_PARSER);
        Map<String, String> parseQueries = new HashMap<>();
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

    public static String parseUrl(String url) {
        String[] urlQuery = splitUrl(url);
        return urlQuery[0];
    }

    public static String getUrlExtension(String url) {
        if(url == null) {
            return null;
        }
        return url.contains(".") ? url.substring(url.lastIndexOf(".")) : null;
    }

    public static Map<String,String> parseBody(String requestBody) {
        if(requestBody == null) {
            return null;
        }
        return parseData(requestBody);

    }
}
