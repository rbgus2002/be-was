package http.request;

import http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpHeaders.class);
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;
    private final String httpRequestBody;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpHeaders httpHeaders, String httpRequestBody) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaders = httpHeaders;
        this.httpRequestBody = httpRequestBody;
    }

    public String getRequestUri() {
        return httpRequestLine.getUri();
    }
    public RequestMethod getRequestMethod() {
        return httpRequestLine.getMethod();
    }
    public String getHttpRequestBody() {
        return httpRequestBody;
    }
    public String getSessionId() {
        String requestCookie = httpHeaders.getRequestCookie();
        if (requestCookie == null) {
            return null;
        }
        return Parser.parseCookie(requestCookie);
    }

    public static HttpRequest create(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        HttpRequestLine requestLine = HttpRequestLine.create(br.readLine());
        HttpHeaders headers = HttpHeaders.create(br);
        String contentLength = headers.getContentLength();

        //TODO : Parser를 통해 파싱하도록 변경 필요!
        if (contentLength != null) {
            int length = Integer.parseInt(contentLength);
            char[] buffer = new char[length];
            int bytesSize = br.read(buffer, 0, length);
            String requestBody = String.valueOf(buffer, 0, bytesSize);
            log.info("requestBody = {}", requestBody);
            return new HttpRequest(requestLine, headers, requestBody);
        }

        return new HttpRequest(requestLine, headers, null);
    }
}
