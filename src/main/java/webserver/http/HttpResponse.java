package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTML_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";
    private final String status;
    private final String path;
    private final HttpMime mime;
    private final byte[] body;

    public HttpResponse(String status, String path, HttpMime mime) throws IOException {
        this.status = status;
        this.path = path;
        this.mime = mime;
        body = getBody(path, mime);
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

    public void response(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        if (this.status.equals("200 OK")) {
            response200Header(dos, body.length);
        } else if (this.status.equals("302 Found")) {
            response302Header(dos);
        }
        responseBody(dos, body);
    }

    public static HttpResponse redirect(String path) throws IOException {
        return new HttpResponse("302 Found", path, HttpMime.HTML);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + getContentType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            logger.debug("this.path: {}", this.path);
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + this.path + "\r\n");
            dos.writeBytes("Cache-Control: no-cache, no-store, must-revalidate\r\n");
            dos.writeBytes("Pragma: no-cache\r\n");
            dos.writeBytes("Expires: 0\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
