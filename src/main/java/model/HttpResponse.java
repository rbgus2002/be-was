package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private byte[] body;

    private Map<String, String> headers;

    private final String uri;
    private final HttpRequest request;

    private String version;

    private HttpStatus statusCode;
    private ContentType contentType;

    public HttpResponse(HttpRequest request) {
        this.request = request;

        uri = request.getRequestURI();
        version = request.getVersion();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
    public String body() {
        return body.toString();
    }

    public int getStatus() {
        return statusCode.getValue();
    }

    public void setStatus(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setContentType(ContentType ContentType) {
        this.contentType = ContentType;
    }

    //TODO: sendRedirect 구현

    //TODO: sendError 구현

    public void write(DataOutputStream dos) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(responseStatusLine()).append(responseHeaderLine()).append("\r\n");

        dos.write(stringBuilder.toString().getBytes());
        dos.write(body);
    }

    private StringBuilder responseStatusLine() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(version).append(" ").append(statusCode.getValue()).append(" ").append(statusCode.getReasonPhrase()).append(" \r\n");

        return stringBuilder;
    }

    private StringBuilder responseHeaderLine() {
        int lengthOfBodyContent = body.length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Content-Type:").append(contentType.getContentType()).append("\r\n").append("Content-Length: " + lengthOfBodyContent + "\r\n");

        return stringBuilder;
    }
}
