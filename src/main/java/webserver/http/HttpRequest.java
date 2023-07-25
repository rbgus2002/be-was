package webserver.http;

import webserver.utils.HttpField;
import webserver.utils.HttpParametersParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
    private final BufferedReader bufferedReader;

    private final HttpHeaders httpHeaders;
    private HttpParameters httpParameters;
    private Cookie cookie;
    private String body = "";


    public HttpRequest(InputStream in) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(in));

        httpHeaders = new HttpHeaders();
        httpParameters = new HttpParameters();
        cookie = new Cookie();

        parseRequestMessage();
    }

    private void parseRequestMessage() throws IOException {
        parseHeader();
        parseBody();
    }

    private void parseHeader() throws IOException {
        parseRequestLine();
        parseHeaderFields();
        parseCookie();
    }

    private void parseRequestLine() throws IOException {
        String[] requestLineTokens = bufferedReader.readLine().split(" ");

        httpHeaders.put(HttpField.METHOD, requestLineTokens[0]);
        httpHeaders.put(HttpField.URI, URLDecoder.decode(requestLineTokens[1], StandardCharsets.UTF_8));
        httpHeaders.put(HttpField.VERSION, requestLineTokens[2]);

        parsePathAndParameters(httpHeaders.get(HttpField.URI));
    }

    private void parsePathAndParameters(String URI) {
        String[] uriTokens = URI.split("\\?");
        parsePath(uriTokens);
        parseParameters(uriTokens);
    }

    private void parsePath(String[] uriTokens) {
        httpHeaders.put(HttpField.PATH, uriTokens[0]);
    }

    private void parseParameters(String[] uriTokens) {
        if (uriTokens.length == 2) {
            httpParameters = HttpParametersParser.parse(uriTokens[1]);
        }
    }

    private void parseHeaderFields() throws IOException {
        String line;
        while (!(line = bufferedReader.readLine()).isEmpty()) {
            int separatorIndex = line.indexOf(":");
            String fieldName = line.substring(0, separatorIndex);
            String fieldValue = line.substring(separatorIndex + 1).trim();
            httpHeaders.put(fieldName, fieldValue);
        }
    }

    private void parseCookie() {
        if (httpHeaders.contains("Cookie")) {
            for (String directive : httpHeaders.get("Cookie").split(";")) {
                cookie.add(directive.trim());
            }
        }
    }

    private void parseBody() throws IOException {
        if(httpHeaders.contains(HttpField.CONTENT_LENGTH)) {
            int bodyLength = Integer.parseInt(httpHeaders.get(HttpField.CONTENT_LENGTH));
            char[] requestBody = new char[bodyLength];

            bufferedReader.read(requestBody, 0, bodyLength);
            this.body = URLDecoder.decode(new String(requestBody), StandardCharsets.UTF_8);
        }
    }

    public String getField(String name) {
        return httpHeaders.get(name);
    }

    public HttpParameters getParameters() {
        return httpParameters;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getBody() {
        return body;
    }
}
