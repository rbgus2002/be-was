package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static util.Parser.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private String path;
    private MIME mime;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body;
    private HttpStatusCode statusCode;

    public HttpResponse() {
    }

    public void set200Header(String path) throws IOException {
        setHeader(path, HttpStatusCode.OK);
        headers.put("Content-Type", mime.getContentType());
    }

    public void set302Header(String path) throws IOException {
        setHeader(path, HttpStatusCode.FOUND);
        headers.put("Location", path);
    }

    private void setHeader(String path, HttpStatusCode statusCode) {
        this.path = path;
        this.statusCode = statusCode;
        this.mime = convertExtensionToMime(getExtension(path));
    }

    public void setBody(byte[] body) {
        this.body = body;
        headers.put("Content-Length", Integer.toString(body.length));
    }

    public MIME getMime() {
        return this.mime;
    }

    public HttpStatusCode getStatusCode () {
        return this.statusCode;
    }

    public void setCookie(String value) {
        this.headers.put("Set-Cookie", value);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public byte[] getBody() {
        return this.body;
    }
}