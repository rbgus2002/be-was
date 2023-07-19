package webserver.http.message;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final HttpVersion httpVersion;
    private final HttpStatus httpStatus;
    private final Map<String, List<String>> metaData;
    private final byte[] body;

    public HttpResponse(HttpVersion httpVersion, HttpStatus httpStatus, Map<String, List<String>> metaData, byte[] body) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.metaData = metaData;
        this.body = body;
    }

    public static HttpResponse okWithFile(byte[] file) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("text/html;charset=utf-8"));
        headers.put("Content-Length", List.of(String.valueOf(file.length)));
        return new HttpResponse(HttpVersion.V1_1, HttpStatus.OK, headers, file);
    }

    public static HttpResponse notFound() {
        byte[] notFoundHtml = "<html><body>NOT FOUND</body><html>".getBytes(StandardCharsets.UTF_8);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("text/html;charset=utf-8"));
        headers.put("Content-Length", List.of(String.valueOf(notFoundHtml.length)));
        return new HttpResponse(HttpVersion.V1_1, HttpStatus.NOT_FOUND, headers, notFoundHtml);
    }

    public static HttpResponse created() {
        byte[] notFoundHtml = "<html><body>Created</body><html>".getBytes(StandardCharsets.UTF_8);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("text/html;charset=utf-8"));
        headers.put("Content-Length", List.of(String.valueOf(notFoundHtml.length)));
        return new HttpResponse(HttpVersion.V1_1, HttpStatus.NOT_FOUND, headers, notFoundHtml);
    }

    public static HttpResponse badRequest() {
        byte[] notFoundHtml = "<html><body>badRequest</body><html>".getBytes(StandardCharsets.UTF_8);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("text/html;charset=utf-8"));
        headers.put("Content-Length", List.of(String.valueOf(notFoundHtml.length)));
        return new HttpResponse(HttpVersion.V1_1, HttpStatus.BAD_REQUEST, headers, notFoundHtml);
    }

    public boolean hasBody() {
        return body != null;
    }

    public Map<String, List<String>> getMetaData() {
        return metaData;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
