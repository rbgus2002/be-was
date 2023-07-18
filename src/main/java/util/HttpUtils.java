package util;

import http.Mime;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpUtils {
    public enum Method {
        GET, POST, DELETE, PUT, PATCH, OPTIONS;

        public static Method of(String method) {
            return Arrays.stream(Method.values())
                    .filter(value -> value.name().equalsIgnoreCase(method))
                    .findAny().orElseThrow(() -> new IllegalArgumentException("유효하지 않은 HTTP 메소드입니다."));
        }
    }

    public static Optional<HttpClient.Version> getHttpVersion(String version) {
        if (version.equals("HTTP/1.1")) {
            return Optional.of(HttpClient.Version.HTTP_1_1);
        } else if (version.equals("HTTP/2.0")) {
            return Optional.of(HttpClient.Version.HTTP_2);
        }
        return Optional.empty();
    }


    public static Map<String, String> parseHeader(BufferedReader reader) throws IOException {
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex != -1) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                map.put(headerName, headerValue);
            }
        }
        return map;
    }

    public static String parseBody(BufferedReader reader, Map<String, String> headers, Method method) throws IOException {
        int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
        if (method.equals(HttpUtils.Method.GET) || contentLength == 0) {
            return null;
        }
        char[] buffer = new char[contentLength];
        reader.read(buffer);
        return URLDecoder.decode(new String(buffer), StandardCharsets.UTF_8);
    }

    public static URI constructUri(String file, Map<String, String> headers) throws URISyntaxException {
        String host = headers.get("Host");
        return new URI("http://" + host + URLDecoder.decode(file, StandardCharsets.UTF_8));
    }

    public static Mime decideMime(String path) {
        String extension = StringUtils.getExtension(path);

        return Arrays.stream(Mime.values())
                .filter(mime -> mime.getExtension().equals(extension))
                .findFirst()
                .orElse(Mime.DEFAULT);
    }
}
