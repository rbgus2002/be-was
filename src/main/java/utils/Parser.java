package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Parser {
    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    public static Map<String, String> parseRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        Map<String, String> requestLineMap = new ConcurrentHashMap<>();
        requestLineMap.put("method", tokens[0]);
        requestLineMap.put("uri", tokens[1]);
        requestLineMap.put("version", tokens[2]);
        return requestLineMap;
    }

    public static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        String line;
        Map<String, String> requestHeaders = new ConcurrentHashMap<>();

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(":");
            requestHeaders.put(header[0], header[1]);
        }

        return requestHeaders;
    }

    public static Map<String, String> parseQueryParameters(String requestUri) {
        log.debug("requestUri = {}", requestUri);
        Map<String, String> queryParams = new ConcurrentHashMap<>();
        String queryString = requestUri.substring(requestUri.indexOf("?") + 1);
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
}
