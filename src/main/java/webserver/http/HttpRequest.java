package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String QUERY_STRING_SEPARATOR = "\\?";
    private static final String EMPTY_SPACE_SEPARATOR = " ";
    private static final String EXTENSION_SEPARATOR = "\\.";
    private static final String QUERY_STRING_PARAM_SEPARATOR = "=";
    private static final String HEADER_PARAM_SEPARATOR = ":";
    private final HttpMethod httpMethod;
    private final String requestPath;
    private final String version;
    private final HttpMime mime;
    private Map<String, String> headers;

    private Map<String, String> params;

    public HttpRequest(HttpMethod httpMethod, String requestPath, String version, HttpMime mime,
                       Map<String, String> headers, Map<String, String> params) {
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.version = version;
        this.mime = mime;
        this.headers = headers;
        this.params = params;
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String firstLine = bufferedReader.readLine();
        this.httpMethod = httpMethod(firstLine);
        this.requestPath = requestPath(firstLine);
        this.version = version(firstLine);
        this.params = parseQueryString(firstLine);
        this.headers = headers(bufferedReader);
        this.mime = mime(requestPath);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public HttpMime getMime() {
        return mime;
    }

    public HttpMime mime(String requestPath) {
        String[] tokens = requestPath.split(EXTENSION_SEPARATOR);
        String extension = tokens[tokens.length - 1];
        logger.debug("extension: {}", extension);
        return Arrays.stream(HttpMime.values())
                .filter(mime -> mime.getExtension().equals(extension))
                .findFirst()
                .orElse(null);
    }

    public static HttpMethod httpMethod(String firstLine) {
        String[] tokens = firstLine.split(EMPTY_SPACE_SEPARATOR);
        HttpMethod method = Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.getMethod().equals(tokens[0]))
                .findFirst()
                .orElse(null);
        logger.debug("request method: {}", method.getMethod());

        return method;
    }

    public static String requestPath(String firstLine) {
        String[] tokens = firstLine.split(EMPTY_SPACE_SEPARATOR);

        String requestPath = tokens[1];
        if (requestPath.contains("?")) {
            requestPath = tokens[1].split(QUERY_STRING_SEPARATOR)[0];
        }
        logger.debug("request url: {}", requestPath);

        return requestPath;
    }

    public static String version(String firstLine) {
        String[] tokens = firstLine.split(EMPTY_SPACE_SEPARATOR);
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
            String key = line.split(HEADER_PARAM_SEPARATOR)[0].trim();
            String value = line.split(HEADER_PARAM_SEPARATOR)[1].trim();
            headers.put(key, value);
        }

        return headers;
    }

    public static Map<String, String> parseQueryString(String firstLine) {
        String[] tokens = firstLine.split(EMPTY_SPACE_SEPARATOR);

        String queryString = tokens[1];
        if (!queryString.contains("?")) {
            return new HashMap<>();
        }

        String params = queryString.split(QUERY_STRING_SEPARATOR)[1];
        Map<String, String> paramsMap = new HashMap<>();
        for (String param : params.split("&")) {
            String key = param.split(QUERY_STRING_PARAM_SEPARATOR)[0];
            String value = param.split(QUERY_STRING_PARAM_SEPARATOR)[1];
            paramsMap.put(key, value);
        }

        return paramsMap;
    }
}
