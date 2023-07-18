package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequest {
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpHeaders httpHeaders) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaders = httpHeaders;
    }

    public String getRequestUri() {
        return httpRequestLine.getUri();
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public static HttpRequest create(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        HttpRequestLine requestLine = HttpRequestLine.create(br.readLine());
        HttpHeaders headers = HttpHeaders.create(br);

        return new HttpRequest(requestLine, headers);
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        httpRequestLine.show(sb);
        httpHeaders.show(sb);

        return sb.toString();
    }
}
