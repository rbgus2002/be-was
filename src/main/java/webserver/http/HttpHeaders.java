package webserver.http;

import utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpHeaders {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private final Map<String, String> headers;
    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }
    public int getContentLength() {
        return Integer.parseInt(headers.get(CONTENT_LENGTH));
    }

    public void show(StringBuilder sb) {
        headers.forEach((key, value) -> {
            sb.append(key);
            sb.append(" : ");
            sb.append(value);
            sb.append(NEW_LINE);
        });
    }

    public static HttpHeaders create(BufferedReader br) throws IOException {
        return new HttpHeaders(Parser.parseHeaders(br));
    }

    public static HttpHeaders createStaticStatusHeaders(int bodyLength, String requestUri) {
        Map<String, String> responseHeaders = new HashMap<>();
        MIME mime = MIME.findMIME(requestUri);
        responseHeaders.put(CONTENT_TYPE, createStaticContentType(mime));
        responseHeaders.put(CONTENT_LENGTH, String.valueOf(bodyLength));
        return new HttpHeaders(responseHeaders);
    }

    public static HttpHeaders createRedirectStatusHeaders() {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put(CONTENT_TYPE, "text/html;charset=utf-8");
        responseHeaders.put(CONTENT_LENGTH, "0");
        responseHeaders.put("Location", "http://localhost:8080/index.html");
        return new HttpHeaders(responseHeaders);
    }

    private static String createStaticContentType(MIME mime) {
        return mime.getContentType() + ";charset=utf-8";
    }
}
