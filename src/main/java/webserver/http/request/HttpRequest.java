package webserver.http.request;

import webserver.http.Headers;
import webserver.http.HttpMethod;
import webserver.http.MIME;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static utils.StringUtils.*;

public class HttpRequest {
    private RequestLine requestLine;
    private Headers headers;
    private RequestBody body;

    private HttpRequest(RequestLine requestLine, Headers headers, RequestBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest from(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        try {
            RequestLine requestLine = RequestLine.from(bufferedReader.readLine());
            Headers headers = Headers.from(bufferedReader);
            RequestBody body = RequestBody.from(bufferedReader, headers.getContentLength());
            return new HttpRequest(requestLine, headers, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getVersion() {
        return this.requestLine.getVersion();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appendNewLine(requestLine.toString()));
        stringBuilder.append(headers.toString());
        stringBuilder.append(appendNewLine(""));
        body.appendBody(stringBuilder);
        return stringBuilder.toString();
    }

    public boolean isMatchHandler(HttpMethod method, String path) {
        return requestLine.isMatchHandler(method, path);
    }

    public MIME getMime() {
        return requestLine.getMime();
    }

    public Map<String, String> getParams() {
        return this.body.getParams();
    }
}
