package webserver.http;

import utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpHeaders {
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";
    private final Map<String, String> headers;
    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }
    public String getContentLength() {
        return headers.get(CONTENT_LENGTH);
    }
    public String getCookie() {
        return headers.get(SET_COOKIE);
    }
    public String getLocation() {
        return headers.get(LOCATION);
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

    public static HttpHeaders createRedirectStatusHeaders(String viewPath, Cookie cookie) {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put(CONTENT_TYPE, "text/html;charset=utf-8");
        responseHeaders.put(CONTENT_LENGTH, "0");
        responseHeaders.put(LOCATION, viewPath);
        if (cookie != null) {
            responseHeaders.put(SET_COOKIE, cookie.getName() + "=" + cookie.getValue() + ";"
                    + " Path=/;"
                    + "expires=" + cookie.getExpires().atZone(ZoneId.of("GMT")) + ";"
                    + " HttpOnly");
        }
        return new HttpHeaders(responseHeaders);
    }

    private static String createStaticContentType(MIME mime) {
        return mime.getContentType() + ";charset=utf-8";
    }
}
