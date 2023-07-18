package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private String method = "200";
    private String version = "HTTP/1.1";
    private String statusMessage = "OK";
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
        String contentType;
        if (type.equals("css")) {
            contentType = "text/css";
        } else if (type.equals("js")) {
            contentType = "text/javascript";
        } else if (type.equals("html")) {
            contentType = "text/html; charset=utf-8";
        } else if (type.equals("ico")) {
            contentType = "image/x-icon";
        } else if (type.equals("jpg") || type.equals("jpeg")) {
            contentType = "image/jpeg";
        } else if (type.equals("png")) {
            contentType = "image/png";
        } else if (type.equals("txt")) {
            contentType = "text/plain";
        } else {
            contentType = type;
        }
        headers.put("Content-Type", contentType);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
        headers.put("Content-Length", String.valueOf(body.length));
    }

    public void setRedirect(String url) {
        setMethod("302");
        setStatusMessage("Found");
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
