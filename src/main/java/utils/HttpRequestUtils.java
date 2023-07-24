package utils;

import common.enums.RequestMethod;
import common.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestUtils {

    private HttpRequestUtils() {}

    public static HttpRequest createRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String line = br.readLine();
        String firstLine = line;

        StringBuilder headerBuilder = new StringBuilder();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            parseHeader(line, headerBuilder);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        while (br.ready()) {
            parseBody((char) br.read(), bodyBuilder);
        }

        RequestLine requestLine = parseRequestLine(firstLine);
        Headers headers = new Headers(headerBuilder.toString());
        RequestBody requestBody = new RequestBody(bodyBuilder.toString());

        return new HttpRequest(requestLine, headers, requestBody);
    }

    private static RequestLine parseRequestLine(String line) {
        String[] requestLine = line.split("\\s");
        String method = requestLine[0];
        String uri = requestLine[1];

        if (!uri.matches("^/([a-zA-Z0-9\\-._~:/?#\\[\\]@!$&'()*+,;=%]+)?$")) {
            throw new RuntimeException("잘못된 URI 형식");
        }

        return new RequestLine(RequestMethod.of(method), new Uri(uri));
    }

    private static void parseHeader(String line, StringBuilder headerBuilder) {
        headerBuilder.append(line).append("\r\n");
    }

    private static void parseBody(char text, StringBuilder bodyBuilder) {
        bodyBuilder.append(text);
    }
}
