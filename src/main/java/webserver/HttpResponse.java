package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    public static final String OK = "200";
    public static final String FOUND = "302";
    public static final String NOT_FOUND = "404";

    public static final String OK_MESSAGE = "OK";
    public static final String FOUND_MESSAGE = "Found";

    public static final String HTTP11 = "HTTP/1.1";

    private String method = OK;
    private String version = HTTP11;
    private String statusMessage = OK_MESSAGE;
    private byte[] body;
    private final Map<String, String> headers = new HashMap<>();

    public void setMethod(String method) {
        this.method = method;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setContentType(String type) {
        headers.put("Content-Type", type);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
        headers.put("Content-Length", String.valueOf(body.length));
    }

    public void setRedirect(String url) {
        setMethod(FOUND);
        setStatusMessage(FOUND_MESSAGE);
        setHeader("Location", url);
    }

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
