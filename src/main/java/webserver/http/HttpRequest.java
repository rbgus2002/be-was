package webserver.http;

import webserver.utils.HttpConstants;
import webserver.utils.HttpField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class HttpRequest {
    private final HttpHeaders httpHeaders;
    private final HttpParameters httpParameters;
    private String path;

    public HttpRequest(InputStream in) throws IOException {
        httpHeaders = new HttpHeaders();
        httpParameters = new HttpParameters();

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

        httpHeaders.put(HttpField.METHOD, stringTokenizer.nextToken());
        httpHeaders.put(HttpField.URI, URLDecoder.decode(stringTokenizer.nextToken()));
        httpHeaders.put(HttpField.VERSION, stringTokenizer.nextToken());

        parsePathAndParameters(httpHeaders.get(HttpField.URI));
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
            httpParameters.put(name, value);
        }
    }

    private void parseOtherHeaders(BufferedReader bufferedReader) throws IOException {
        String line;
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            int colonIndex = line.indexOf(":");
            String field = line.substring(0, colonIndex);
            String value = line.substring(colonIndex + 1).trim();
            httpHeaders.put(field, value);
        }
    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader.ready()) {
            String body = bufferedReader.lines().collect(Collectors.joining(HttpConstants.CRLF));
            httpHeaders.put("body", body);
            return;
        }
        httpHeaders.put("body", "");
    }

    public String get(String fieldName) {
        return httpHeaders.get(fieldName);
    }

    public String getBody() {
        return httpHeaders.get("body");
    }

    public String getURI() {
        return httpHeaders.get(HttpField.URI);
    }

    public String getPath() {
        return path;
    }

    public HttpParameters getParameters() {
        return httpParameters;
    }
}
