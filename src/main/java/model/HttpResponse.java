package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
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
    private String Location;

    public HttpResponse(HttpRequest request) {
        this.request = request;

        uri = request.getRequestURI();
        version = request.getVersion();
        headers = new LinkedHashMap<>();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
    public byte[] getBody() {
        return body;
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
//    public void sendRedirect()

    //TODO: sendError 구현

    public String makeResponseHeader() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(responseStatusLine()).append(responseHeaderLine()).append("\r\n");

        return stringBuilder.toString();
    }

    private StringBuilder responseStatusLine() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(version).append(" ").append(statusCode.getValue()).append(" ").append(statusCode.getReasonPhrase()).append(" \r\n");

        return stringBuilder;
    }

    private StringBuilder responseHeaderLine() {
        StringBuilder stringBuilder = new StringBuilder();

        if(contentType != null) {
            stringBuilder.append("Content-Type:").append(contentType.getContentType()).append("\r\n");
        }

        if(body != null) {
            int lengthOfBodyContent = body.length;
            stringBuilder.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        }

        if(!headers.isEmpty()) {
            headers.keySet().stream().forEach(key -> stringBuilder.append(key).append(":").append(headers.get(key)).append("\r\n"));
        }

        return stringBuilder;
    }
}
