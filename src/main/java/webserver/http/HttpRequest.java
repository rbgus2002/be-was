package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static utils.StringUtils.NEW_LINE;

public class HttpRequest {
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaders httpHeaders;

    public HttpRequest(HttpRequestLine httpRequestLine, HttpHeaders httpHeaders) {
        this.httpRequestLine = httpRequestLine;
        this.httpHeaders = httpHeaders;
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
        sb.append("METHOD : ").append(httpRequestLine.getMethod()).append(NEW_LINE);
        sb.append("URI : ").append(httpRequestLine.getUri()).append(NEW_LINE);
        sb.append("VERSION : ").append(httpRequestLine.getVersion()).append(NEW_LINE);
        sb.append(httpHeaders.show());

        return sb.toString();
    }
}
