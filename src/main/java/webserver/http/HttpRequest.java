package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final String method;
    private final String requestPath;
    private final String version;
    private Map<String, String> headers;

    private Map<String, String> params;

    public HttpRequest(String method, String requestPath, String version, String queryString) {
        this.method = method;
        this.requestPath = requestPath;
        this.version = version;
        this.params = parseQueryString(queryString);
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String firstLine = bufferedReader.readLine();
        this.method = method(firstLine);
        this.requestPath = requestPath(firstLine);
        this.version = version(firstLine);
        this.params = parseQueryString(firstLine);
        this.headers = headers(bufferedReader);
    }

    public String getMethod() {
        return method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public static String method(String firstLine) {
        String[] tokens = firstLine.split(" ");
        String method = tokens[0];
        logger.debug("request method: {}", method);

        return method;
    }

    public static String requestPath(String firstLine) {
        String[] tokens = firstLine.split(" ");

        String requestPath = tokens[1];
        if (requestPath.contains("?")) {
            requestPath = tokens[1].split("\\?")[0];
        }
        logger.debug("request url: {}", requestPath);

        return requestPath;
    }

    public static String version(String firstLine) {
        String[] tokens = firstLine.split(" ");
        String version = tokens[2];
        logger.debug("request version: {}", version);

        return version;
    }

    private Map<String, String> headers(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headers = new HashMap<>();

        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line.equals("")) {
                break;
            }
            String key = line.split(":")[0].trim();
            String value = line.split(":")[1].trim();
            headers.put(key, value);
        }

        return headers;
    }

    private static Map<String, String> parseQueryString(String firstLine) {
        String[] tokens = firstLine.split(" ");

        String queryString = tokens[1];
        if (!queryString.contains("?")) {
            return new HashMap<>();
        }

        String params = queryString.split("\\?")[1];
        Map<String, String> paramsMap = new HashMap<>();
        for (String param : params.split("&")) {
            String key = param.split("=")[0];
            String value = param.split("=")[1];
            paramsMap.put(key, value);
        }

        return paramsMap;
    }
}
