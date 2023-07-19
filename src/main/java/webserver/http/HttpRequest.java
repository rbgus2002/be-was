package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class HttpRequest {
    private final Map<String, String> headers;
    private String path;
    private final Map<String, String> parametersMap;

    public HttpRequest(InputStream in) throws IOException {
        headers = new HashMap<>();
        parametersMap = new HashMap<>();

        parseRequestMessage(in);
    }

    public void parseRequestMessage(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        if (!bufferedReader.ready()) {
            throw new IOException("Received Empty Request Message");
        }

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
        headers.put(HttpConstant.URI, URLDecoder.decode(stringTokenizer.nextToken()));
        headers.put(HttpConstant.VERSION, stringTokenizer.nextToken());

        parsePathAndParameters(headers.get(HttpConstant.URI));
    }

    private void parsePathAndParameters(String URI) {
        if (!URI.contains("?")) {
            path = URI;
            return;
        }

        int separatorIndex = URI.indexOf("?");

        path = URI.substring(0, separatorIndex);
        String parametersString = URI.substring(separatorIndex + 1);

        StringTokenizer stringTokenizer = new StringTokenizer(parametersString, "&");

        while (stringTokenizer.hasMoreTokens()) {
            String parameter = stringTokenizer.nextToken();
            separatorIndex = parameter.indexOf("=");
            String name = parameter.substring(0, separatorIndex);
            String value = parameter.substring(separatorIndex + 1);
            parametersMap.put(name, value);
        }
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

    public String getPath() {
        return path;
    }

    public Map<String, String> getParametersMap() {
        return parametersMap;
    }
}
