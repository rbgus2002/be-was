package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;
import webserver.http.HttpMime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestStartLine {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestStartLine.class);
    private static final String START_LINE_SEPARATOR = " ";
    private static final String QUERY_STRING_SEPARATOR = "\\?";
    private static final String EXTENSION_SEPARATOR = "\\.";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private final HttpMethod httpMethod;
    private final String requestPath;
    private final HttpMime mime;
    private final Map<String, String> params;

    public HttpRequestStartLine(String startLine) {
        String[] splitStartLine = startLine.split(START_LINE_SEPARATOR);

        this.httpMethod = extractHttpMethod(splitStartLine[0]);
        this.requestPath = extractRequestPath(splitStartLine[1]);
        this.mime = extractMime(requestPath);
        this.params = parseQueryString(splitStartLine[1]);
    }

    private HttpMethod extractHttpMethod(String method) {
        HttpMethod httpMethod =  Arrays.stream(HttpMethod.values())
                .filter(m -> m.getMethod().equals(method))
                .findFirst()
                .orElse(null);
        logger.debug("http method: {}", httpMethod);
        return httpMethod;
    }

    private String extractRequestPath(String uri) {
        String requestPath = uri;

        if (uri.contains("?")) {
            requestPath = uri.split(QUERY_STRING_SEPARATOR)[0];
        }

        logger.debug("request path: {}", requestPath);
        return requestPath;
    }

    private HttpMime extractMime(String requestPath) {
        String[] tokens = requestPath.split(EXTENSION_SEPARATOR);
        String extension = tokens[tokens.length - 1];
        logger.debug("extension: {}", extension);
        return Arrays.stream(HttpMime.values())
                .filter(mime -> mime.getExtension().equals(extension))
                .findFirst()
                .orElse(null);
    }

    private Map<String, String> parseQueryString(String uri) {
        if (!uri.contains("?")) {
            return new HashMap<>();
        }

        String params = uri.split(QUERY_STRING_SEPARATOR)[1];
        Map<String, String> paramsMap = new HashMap<>();
        for (String param : params.split(PARAMETER_SEPARATOR)) {
            String key = param.split(KEY_VALUE_SEPARATOR)[0];
            String value = param.split(KEY_VALUE_SEPARATOR)[1];
            paramsMap.put(key, value);
        }

        return paramsMap;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public HttpMime getMime() {
        return mime;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
