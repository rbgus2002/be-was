package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public String getContentType() {
        return headers.get("Content-Type");
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
        responseHeaders.put("Content-Type", createStaticContentType(mime));
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

    private static String createStaticContentType(MIME mime) {
        return mime.getContentType() + ";charset=utf-8";
    }
}
