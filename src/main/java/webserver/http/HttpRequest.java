package webserver.http;

import webserver.utils.HttpField;
import webserver.utils.HttpMethod;
import webserver.utils.UrlEncodedParameterParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequest {
    private final BufferedReader bufferedReader;

    private String method;
    private final HttpHeaders httpHeaders;
    private HttpParameters httpParameters;
    private final Cookie cookie;
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
        parsePath();
        parseParameters();
    }

    private void parseHeader() throws IOException {
        parseRequestLine();
        parseHeaderFields();
        parseCookie();
    }

    private void parseRequestLine() throws IOException {
        String[] requestLineTokens = bufferedReader.readLine().split(" ");
        method = requestLineTokens[0];
        httpHeaders.put(HttpField.URI, URLDecoder.decode(requestLineTokens[1], StandardCharsets.UTF_8));
        httpHeaders.put(HttpField.VERSION, requestLineTokens[2]);
    }

    private void parsePath() {
        String URI = httpHeaders.get(HttpField.URI);
        String path = URI.split("\\?")[0];
        httpHeaders.put(HttpField.PATH, path);
    }

    private void parseParameters() {
        if (method.equals(HttpMethod.GET)) {
            parseURIParameters();
        }
        if (method.equals(HttpMethod.POST)) {
            parseBodyParameters();
        }
    }

    private void parseURIParameters() {
        String URI = httpHeaders.get(HttpField.URI);
        String[] uriTokens = URI.split("\\?");
        if (uriTokens.length == 2) {
            httpParameters = UrlEncodedParameterParser.parse(uriTokens[1]);
        }
    }

    private void parseBodyParameters() {
        String contentType = httpHeaders.get(HttpField.CONTENT_TYPE);
        if (contentType.equals("application/x-www-form-urlencoded")) {
            httpParameters = UrlEncodedParameterParser.parse(body);
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
        if (httpHeaders.contains(HttpField.CONTENT_LENGTH)) {
            int bodyLength = Integer.parseInt(httpHeaders.get(HttpField.CONTENT_LENGTH));
            char[] requestBody = new char[bodyLength];

            bufferedReader.read(requestBody, 0, bodyLength);
            this.body = URLDecoder.decode(new String(requestBody), StandardCharsets.UTF_8);
        }
    }

    public String getHeader(String field) {
        return httpHeaders.get(field);
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

    public String getMethod() {
        return method;
    }
}
