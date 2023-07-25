package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;
import webserver.http.HttpMime;

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
    private static final String PARAM_SEPARATOR = ":";
    private final HttpMethod httpMethod;
    private final String requestPath;
    private final String version;
    private final HttpMime mime;
    private final Map<String, String> headers;

    private final Map<String, String> params;
    private final Map<String, String> body;

    public HttpRequest(HttpMethod httpMethod, String requestPath, String version, HttpMime mime,
                       Map<String, String> headers, Map<String, String> params, Map<String, String> body) {
        this.httpMethod = httpMethod;
        this.requestPath = requestPath;
        this.version = version;
        this.mime = mime;
        this.headers = headers;
        this.params = params;
        this.body = body;
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String firstLine = bufferedReader.readLine();
        this.httpMethod = httpMethod(firstLine);
        this.requestPath = requestPath(firstLine);
        this.version = version(firstLine);
        this.params = parseQueryString(firstLine);
        this.mime = mime(requestPath);
        this.headers = headers(readHeaders(bufferedReader));
        if (headers.get("Content-Length") != null) {
            this.body = body(readBody(bufferedReader, Integer.parseInt(headers.get("Content-Length"))));
        } else {
            this.body = new HashMap<>();
        };
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

    public Map<String, String> getBody() {
        return body;
    }

    private String readHeaders(BufferedReader bufferedReader) throws IOException {
        StringBuilder headers = new StringBuilder();
        String line;
        while(true) {
            line = bufferedReader.readLine();
            if (line.equals("")) {
                break;
            }
            headers.append(line).append("\r\n");
        }

        return headers.toString();
    }

    private String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        for (int length = 0; length < contentLength; length++) {
            body.append((char) bufferedReader.read());
        }
        logger.debug("body: {}", body);
        return body.toString();
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

    private Map<String, String> headers(String headers) throws IOException {
        Map<String, String> headerMap = new HashMap<>();

        String[] tokens = headers.split("\r\n");
        for (String token : tokens) {
            String[] params = token.split(PARAM_SEPARATOR);
            String key = params[0].trim();
            String value = params[1].trim();
            headerMap.put(key, value);
        }

        return headerMap;
    }

    private static Map<String, String> body(String body) {
        if (body.equals("")) {
            return new HashMap<>();
        }

        Map<String, String> bodyMap = new HashMap<>();
        String[] params = body.split("&");
        for (String param : params) {
            String[] splitParam = param.split(QUERY_STRING_PARAM_SEPARATOR);
            String key = splitParam[0].trim();
            String value = splitParam[1].trim();
            bodyMap.put(key, value);
        }

        return bodyMap;
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
