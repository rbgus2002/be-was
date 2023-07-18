package webserver.http.request;

import static webserver.http.Http.HEADER_SEPARATOR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import webserver.http.Headers;
import webserver.http.Http;
import webserver.http.Http.MIME;
import webserver.http.Http.Method;
import webserver.http.Http.Version;

public class HttpRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final RequestLine requestLine;
    private final Headers headers;
    private final String body;

    private HttpRequest(final RequestLine requestLine, final Headers headers, final String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequest from(final InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String requestLineText = bufferedReader.readLine();
        if (requestLineText == null) {
            return new HttpRequest(null, null, null);
        }
        RequestLine requestLine = extractRequestLine(requestLineText);
        Headers headers = extractHeader(bufferedReader);
        String body = extractBody(bufferedReader, headers);
        return new HttpRequest(requestLine, headers, body);
    }

    private static RequestLine extractRequestLine(final String text) {
        String[] strings = text.split(" ");
        Method method = Method.valueOf(strings[0]);
        RequestTarget requestTarget = new RequestTarget(strings[1]);
        Version version = Version.findBy(strings[2]);
        return new RequestLine(method, requestTarget, version);
    }

    private static Headers extractHeader(final BufferedReader bufferedReader) throws IOException {
        Map<String, String> result = new HashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            String[] strings = line.split(HEADER_SEPARATOR);
            result.put(strings[0], strings[1]);
        }
        return new Headers(result);
    }

    private static String extractBody(final BufferedReader bufferedReader, final Headers headers)
            throws IOException {
        if (!headers.containsKey(Http.Headers.CONTENT_LENGTH.getName())) {
            return null;
        }
        int contentLength = Integer.parseInt(headers.get(Http.Headers.CONTENT_LENGTH));
        char[] chars = new char[contentLength];
        return String.valueOf(bufferedReader.read(chars, 0, contentLength));
    }

    public boolean is(final MIME mime) {
        return this.requestLine.getTarget()
                .getMIME()
                .equals(mime);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
