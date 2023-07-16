package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static java.nio.charset.StandardCharsets.UTF_8;
import static utils.StringUtils.NEW_LINE;

public class HttpRequest {
    private Method method;
    private String path;
    private String version;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private String body;

    private HttpRequest() {}

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public static HttpRequest create(InputStream in) throws IOException {
        HttpRequest httpRequest = new HttpRequest();

        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        String line = br.readLine();

        parseRequestLine(line, httpRequest);

        while ((line = br.readLine()) != null && !line.isEmpty()) {
            parseHeader(line, httpRequest);
        }

        while (br.ready()) {
            parseBody(br.readLine(), httpRequest);
        }

        return httpRequest;
    }

    private static void parseRequestLine(String line, HttpRequest httpRequest) {
        String[] requestLine = line.split("\\s");
        String method = requestLine[0];
        String uri = requestLine[1];
        String httpVersion = requestLine[2];

        if (!uri.matches("^/([a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=%]+)?$")) {
            throw new RuntimeException("잘못된 URI 형식");
        }

        Map<String, String> params = new HashMap<>();
        String[] parts = uri.split("\\?");

        String path = parts[0];
        if (parts.length > 1 && !parts[1].isEmpty()) {
            parts = parts[1].split("&");

            for (String param : parts) {
                String[] keyValue = param.split("=");

                if (keyValue.length > 1) {
                    params.put(keyValue[0], keyValue[1]);
                } else {
                    params.put(keyValue[0], null);
                }
            }
        }

        httpRequest.method = Method.valueOf(method);
        httpRequest.path = path;
        httpRequest.version = httpVersion;
        httpRequest.params = params;
    }

    private static void parseHeader(String line, HttpRequest httpRequest) {
        String[] headerForm = line.split(":\\s");
        httpRequest.headers.put(headerForm[0], headerForm[1]);
    }

    private static void parseBody(String line, HttpRequest httpRequest) {
        httpRequest.body += line;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE)
                .append("[Method] ").append(method).append(NEW_LINE)
                .append("[URL] ").append(path).append(NEW_LINE)
                .append("[Version] ").append(version).append(NEW_LINE);

        for (Entry<String, String> entry : headers.entrySet()) {
            sb.append("[Header] ").append(entry.getKey()).append(": ").append(entry.getValue()).append(NEW_LINE);
        }
        for (Entry<String, String> entry : params.entrySet()) {
            sb.append("[Param] ").append(entry.getKey()).append(": ").append(entry.getValue()).append(NEW_LINE);
        }
        sb.append("[Body] ").append(body).append(NEW_LINE);

        return sb.toString();
    }

    public enum Method {
        GET, POST, PUT, DELETE;

        public static boolean isGetMethod(Method method) {
            return method.equals(GET);
        }
    }
}