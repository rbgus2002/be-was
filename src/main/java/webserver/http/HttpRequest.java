package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static utils.StringUtils.*;

public class HttpRequest {
    private final HttpRequestLine requestLine;
    private final Header header;
    private final Body body;

    private HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.requestLine = HttpRequestLine.from(br.readLine());
        this.header = Header.from(br);
        this.body = Body.of(br, header.getContentLength());
    }

    public static HttpRequest from(InputStream in) throws IOException {
        return new HttpRequest(in);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<URI>").append(appendNewLine(requestLine.toString()));
        sb.append(appendNewLine("<HEADER>")).append(header).append(appendNewLine("</HEADER>"));
        sb.append(appendNewLine("<BODY>")).append(body).append(appendNewLine("</BODY>"));
        return sb.toString();
    }

    public String getPath() {
        return requestLine.getUri().getPath();
    }

    public Map<String, String> getQuery(){
        return requestLine.getUri().getQuery();
    }

    public Map<String, String> getBody() {
        return body.getBody();
    }


    public boolean isPostMethod() {
        return "POST".equals(requestLine.getMethod());
    }

    public boolean isGetMethod() {
        return "GET".equals(requestLine.getMethod());
    }
}