package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;
import webserver.http.HttpMime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String NEW_LINE = "\r\n";
    private static final String EMPTY_STRING = "";
    private final BufferedReader bufferedReader;
    private final HttpRequestStartLine startLine;
    private final HttpRequestHeaders headers;
    private final HttpRequestBody body;

    public HttpRequest(BufferedReader bufferedReader, HttpRequestStartLine startLine, HttpRequestHeaders headers, HttpRequestBody body) {
        this.bufferedReader = bufferedReader;
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpRequest(InputStream in) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(in));
        this.startLine = new HttpRequestStartLine(readStartLine());
        this.headers = new HttpRequestHeaders(readHeaderString());
        this.body = new HttpRequestBody(readBodyString());
    }

    private String readStartLine() throws IOException {
        return bufferedReader.readLine();
    }

    private String readHeaderString() throws IOException {
        StringBuilder headers = new StringBuilder();
        String line;
        while(true) {
            line = bufferedReader.readLine();
            if (line.equals(EMPTY_STRING)) {
                break;
            }
            headers.append(line).append(NEW_LINE);
        }

        return headers.toString();
    }

    private String readBodyString() throws IOException {
        if (headers.getParamValue("Content-Length") != null) {
            int contentLength = Integer.parseInt(headers.getParamValue("Content-Length"));
            logger.debug("content length: {}", contentLength);
            char[] bodyString = new char[contentLength];
            bufferedReader.read(bodyString, 0, contentLength);
            return new String(bodyString);
        }

        return "";
    }

    public String getRequestPath() {
        return startLine.getRequestPath();
    }

    public HttpMime getMime() {
        return startLine.getMime();
    }

    public HttpMethod getHttpMethod() {
        return startLine.getHttpMethod();
    }

    public Map<String, String> getHeadersMap() {
        return headers.getHeader();
    }

    public Map<String, String> getBodyMap() {
        return body.getBody();
    }

    public String getCookie() {
        return headers.getParamValue("Cookie");
    }
}
