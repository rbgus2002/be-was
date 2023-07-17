package webserver.http;

import java.util.*;

public class URL {
    private final String path;
    private final Map<String, List<String>> queryParameters;

    private URL(String path) {
        this.path = path;
        this.queryParameters = new HashMap<>();
    }
    private URL(String path, Map<String, List<String>> queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public String getPath() {
        return path;
    }

    public Map<String, List<String>> getQueryParameter() {
        return queryParameters;
    }

    public static URL from(String urlString) {
        String[] parts = urlString.split("\\?");
        String path = parts[0];
        if (parts.length < 2) {
            return new URL(path);
        }
        Map<String, List<String>> queryParams = getQueryParams(parts[1]);
        return new URL(path, queryParams);
    }

    private static Map<String, List<String>> getQueryParams(String queryStrings) {
        Map<String, List<String>> queryParams = new HashMap<>();
        String[] params = queryStrings.split("&");
        for (String param : params) {
            String[] token = param.split("=");
            String key = token[0];
            String[] values = token[1].split(",");
            add(queryParams, key, values);
        }
        return queryParams;
    }

    private static void add(Map<String, List<String>> queryParams, String key, String[] valuesStrings) {
        List<String> values = queryParams.getOrDefault(key, new ArrayList<>());
        values.addAll(Arrays.asList(valuesStrings));
        queryParams.put(key, values);
    }
}
