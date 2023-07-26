package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMime;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTML_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String NEW_LINE = "\r\n";
    private final HttpStatus status;
    private final String path;
    private final HttpMime mime;
    private final Map<String, String> header;
    private final byte[] body;

    public HttpResponse(HttpStatus status, String path, HttpMime mime) throws IOException {
        this.status = status;
        this.path = path;
        this.mime = mime;
        this.body = getBody(path, mime);
        this.header = setHeader();
    }

    private byte[] getBody(String path, HttpMime mime) throws IOException {
        byte[] body;

        if (mime.getExtension().equals("html")) {
            body = Files.readAllBytes(new File(HTML_PATH + path).toPath());
        } else {
            body = Files.readAllBytes(new File(STATIC_PATH + path).toPath());
        }

        return body;
    }

    public String getPath() {
        return path;
    }

    private String getContentType() {
        String contentType = mime.getContentType();
        logger.debug("content type: {}", contentType);
        return contentType;
    }

    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(setStatusLine());
        dos.writeBytes(getHeader());
        responseBody(dos, body);
    }

    public static HttpResponse redirect(String path) throws IOException {
        HttpResponse response = new HttpResponse(HttpStatus.FOUND, path, HttpMime.HTML);
        response.header.put("Location", path);
        return response;
    }

    private String setStatusLine() {
        return "HTTP/1.1 " + this.status.getStatusCode() + " " + this.status.getStatus() + " " + NEW_LINE;
    }

    private Map<String, String> setHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", getContentType() + ";charset=utf-8\r\n");
        header.put("Content-Length", String.valueOf(this.body.length));
        return header;
    }

    public void setCookie(String sessionId, String path) {
        this.header.put("Set-Cookie", "sid=" + sessionId + "; Path=" + path);
    }

    private String getHeader() {
        StringBuilder header = new StringBuilder();
        for (String key : this.header.keySet()) {
            header.append(key).append(": ").append(this.header.get(key)).append(" ").append(NEW_LINE);
        }
        header.append(NEW_LINE);

        return header.toString();
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
            header.put("Content-Length", String.valueOf(body.length));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
