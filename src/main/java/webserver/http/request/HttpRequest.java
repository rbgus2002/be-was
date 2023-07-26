package webserver.http.request;

import model.User;
import webserver.http.Headers;
import webserver.http.HttpMethod;
import webserver.http.MIME;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class HttpRequest {
    private RequestLine requestLine;
    private Headers headers;
    private String body;

    private HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest from(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        try {
            RequestLine requestLine = RequestLine.from(bufferedReader.readLine());
            Headers headers = Headers.from(bufferedReader);
            String body = buildBody(bufferedReader, headers.getContentLength());
            return new HttpRequest(requestLine, headers, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        return new String(buffer);
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
        stringBuilder.append(body);
        return stringBuilder.toString();
    }

    public boolean isMatchHandler(HttpMethod method, String path) {
        return requestLine.isMatchHandler(method, path);
    }

    public MIME getMime() {
        return requestLine.getMime();
    }

    public User createUserFromBody() {
        Map<String, String> params = new HashMap<>();
        String[] pairs = body.split(AMPERSAND);
        for (String pair : pairs) {
            String[] keyValue = pair.split(EQUAL);
            params.put(keyValue[0], keyValue[1]);
        }
        return User.from(params);
    }
}
