package webserver.http;

import utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpHeaders {

    private final Map<String, String> headers;
    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
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

    public static HttpHeaders createStaticStatusHeaders(int bodyLength) {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "text/html;charset=utf-8");
        responseHeaders.put("Content-Length", String.valueOf(bodyLength));
        return new HttpHeaders(responseHeaders);
    }

    public static HttpHeaders createRedirectStatusHeaders() {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "text/html;charset=utf-8");
        responseHeaders.put("Content-Length", "0");
        responseHeaders.put("Location", "http://localhost:8080/index.html");
        return new HttpHeaders(responseHeaders);
    }
}
