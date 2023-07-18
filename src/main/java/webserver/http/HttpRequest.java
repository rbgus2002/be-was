package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final Map<String, String> headers;

    public HttpRequest(InputStream in) throws IOException {
        headers = new HashMap<>();
        parseMessage(in);
    }

    public void parseMessage(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        parseHeader(bufferedReader);
        parseBody(bufferedReader);
    }

    private void parseHeader(BufferedReader bufferedReader) throws IOException {
        parseRequestLine(bufferedReader);
        parseOtherHeaders(bufferedReader);
    }

    private void parseRequestLine(BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

        headers.put(HttpConstant.METHOD, stringTokenizer.nextToken());
        headers.put(HttpConstant.URI, stringTokenizer.nextToken());
        headers.put(HttpConstant.VERSION, stringTokenizer.nextToken());
    }

    private void parseOtherHeaders(BufferedReader bufferedReader) throws IOException {
        String line;
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            int colonIndex = line.indexOf(":");
            String field = line.substring(0, colonIndex);
            String value = line.substring(colonIndex + 1).trim();
            headers.put(field, value);
        }
    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader.ready()) {
            String body = bufferedReader.lines().collect(Collectors.joining(HttpConstant.CRLF));
            headers.put("body", body);
            return;
        }
        headers.put("body", "");
    }

    public String get(String fieldName) {
        return headers.get(fieldName);
    }

    public String getBody() {
        return headers.get("body");
    }

    public String getURI() {
        return headers.get(HttpConstant.URI);
    }
}
