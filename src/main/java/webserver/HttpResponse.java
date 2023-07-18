package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private String method = "200";
    private String version = "HTTP/1.1";
    private String statusMessage = "OK";
    private byte[] body;
    private final Map<String, String> headers = new HashMap<>();
    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void send() throws IOException {
        dos.writeBytes(version + " " + method + statusMessage + "\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

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

    public void setBody(byte[] body, String extension) {
        this.body = body;
        headers.put("Content-Length", String.valueOf(body.length));
        if (extension.equals("css")) {
            setContentType("text/css");
        } else if (extension.equals("js")) {
            setContentType("text/javascript");
        } else if (extension.equals("html")) {
            setContentType("text/html; charset=utf-8");
        } else if (extension.equals("ico")) {
            setContentType("image/x-icon");
        } else if (extension.equals("jpg") || extension.equals("jpeg")) {
            setContentType("image/jpeg");
        } else if (extension.equals("png")) {
            setContentType("image/png");
        } else {
            setContentType("text/plain");
        }
    }

    public void setRedirect(String url) {
        setMethod("302");
        setStatusMessage("Found");
        setHeader("Location", url);
    }
}
