package webserver.http;

import webserver.exception.WeirdRequestException;
import webserver.http.message.*;
import webserver.http.message.HttpRequest.HttpRequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequestParser {
    public static final String SPACE = " ";
    public static final String BLANK = "";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String INVALID_REQUEST_LINE_FORMAT_MESSAGE = "요청 메세지 형식이 잘못됨";
    public static final String EMPTY_REQUEST_LINE_FORMAT_MESSAGE = "요청 메세지가 없는 Connection 발생";
    public static final int NUM_OF_REQUEST_LINE_SEGMENT = 3;
    public static final int NOTHING_VALUE = -1;

    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();

        updateRequestLine(bufferedReader, httpRequestBuilder);
        updateRequestHeaders(bufferedReader, httpRequestBuilder);

        int contentLength;
        if ((contentLength = getContentLength(httpRequestBuilder)) != NOTHING_VALUE) {
            updateBody(bufferedReader, contentLength, httpRequestBuilder);
        }

        return httpRequestBuilder.build();
    }

    private int getContentLength(HttpRequestBuilder httpRequestBuilder) {
        return httpRequestBuilder.httpHeaders.getContentLength();
    }

    private void updateBody(BufferedReader bufferedReader, int contentLength, HttpRequestBuilder httpRequestBuilder) throws IOException {
        char[] body = new char[contentLength];
        bufferedReader.read(body, 0, contentLength);
        httpRequestBuilder.body(body);
    }

    private void updateRequestHeaders(BufferedReader bufferedReader, HttpRequestBuilder httpRequestBuilder) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();

        String header;
        while (!(header = bufferedReader.readLine()).equals(BLANK)) {
            String[] tokens = header.replaceAll(SPACE, BLANK).split(COLON, 2);
            String key = tokens[0];
            String[] values = tokens[1].split(COMMA);
            httpHeaders.addHeader(key, values);
        }

        httpRequestBuilder.httpHeader(httpHeaders);
    }

    private void updateRequestLine(BufferedReader bufferedReader, HttpRequestBuilder httpRequestBuilder) throws IOException {
        Map<Class<?>, Object> requestLineSegments = readRequestLineSegments(bufferedReader);

        httpRequestBuilder
                .httpMethod((HttpMethod) requestLineSegments.get(HttpMethod.class))
                .url((URL) requestLineSegments.get(URL.class))
                .httpVersion((HttpVersion) requestLineSegments.get(HttpVersion.class));
    }

    private static Map<Class<?>, Object> readRequestLineSegments(BufferedReader bufferedReader) throws IOException {
        String[] requestLineTokens = getVerifiedRequestLine(bufferedReader);

        HttpMethod method = HttpMethod.from(requestLineTokens[0]);
        URL url = URL.from(requestLineTokens[1]);
        HttpVersion httpVersion = HttpVersion.from(requestLineTokens[2]);

        return Map.of(
                HttpMethod.class, method,
                URL.class, url,
                HttpVersion.class, httpVersion);
    }

    private static String[] getVerifiedRequestLine(BufferedReader bufferedReader) throws IOException {
        String requestLine;
        if ((requestLine = bufferedReader.readLine()) == null || requestLine.equals("")) {
            throw new WeirdRequestException(EMPTY_REQUEST_LINE_FORMAT_MESSAGE);
        }

        String[] requestLineTokens = requestLine.split(SPACE);
        if (requestLineTokens.length != NUM_OF_REQUEST_LINE_SEGMENT) {
            throw new WeirdRequestException(INVALID_REQUEST_LINE_FORMAT_MESSAGE);
        }
        return requestLineTokens;
    }
}
