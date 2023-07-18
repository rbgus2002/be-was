package utils;

import common.ContentType;
import common.HttpRequest;
import common.Method;
import common.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static common.ContentType.*;

public class HttpRequestUtils {

    private HttpRequestUtils() {}

    public static HttpRequest createRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();

        RequestLine requestLine = parseRequestLine(line);

        Map<String, String> headers = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            parseHeader(line, headers);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        while (br.ready()) {
            parseBody(br.readLine(), bodyBuilder);
        }

        return new HttpRequest(requestLine, headers, bodyBuilder.toString());
    }

    private static RequestLine parseRequestLine(String line) {
        String[] requestLine = line.split("\\s");
        String method = requestLine[0];
        String uri = requestLine[1];
        String version = requestLine[2];

        if (!uri.matches("^/([a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=%]+)?$")) {
            throw new RuntimeException("잘못된 URI 형식");
        }

        ContentType contentType = parseContentType(uri);

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

        return new RequestLine(Method.valueOf(method), path, version, contentType, params);
    }

    private static ContentType parseContentType(String uri) {
        if (uri.endsWith(".html")) {
            return HTML;
        }
        if (uri.endsWith(".css")) {
            return CSS;
        }
        if (uri.endsWith(".js")) {
            return JS;
        }
        if (uri.endsWith(".png")) {
            return PNG;
        }
        if (uri.endsWith(".jpg")) {
            return JPG;
        }
        if (uri.endsWith(".ico")) {
            return ICO;
        }
        return NONE;
    }

    private static void parseHeader(String line, Map<String, String> headers) {
        String[] headerForm = line.split(":(\\s?)");
        headers.put(headerForm[0], headerForm[1]);
    }

    private static void parseBody(String line, StringBuilder bodyBuilder) {
        bodyBuilder.append(line);
    }
}