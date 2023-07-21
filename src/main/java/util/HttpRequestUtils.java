package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static String getFirstLine(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new IOException("유효하지 않은 Request Header 입니다.");
        }

        return line;
    }

    public static String getMethod(String firstLine) {
        String[] tokens = firstLine.split(" ");
        String method = tokens[0];
        logger.debug("request method: {}", method);

        return method;
    }

    public static String getUrl(String firstLine) {
        String[] tokens = firstLine.split(" ");
        String url = tokens[1].split("\\?")[0];
        logger.debug("request url: {}", url);

        return url;
    }

    public static String getVersion(String firstLine) {
        String[] tokens = firstLine.split(" ");
        String version = tokens[2];
        logger.debug("request version: {}", version);

        return version;
    }

    public static boolean isValidPath(String path) {
        String[] splitPath = path.split("\\.");
        return splitPath[0].startsWith("/") && splitPath[1].equals("html");
    }

    public static Map<String, String> parseQueryString(String parameters) {
        Map<String, String> paramsMap = new HashMap<>();
        if (parameters == null || parameters.isEmpty()) {
            return new HashMap<>();
        }

        for (String param : parameters.split("&")) {
            String key = param.split("=")[0];
            String value = param.split("=")[1];
            paramsMap.put(key, value);
        }

        return paramsMap;
    }
}
