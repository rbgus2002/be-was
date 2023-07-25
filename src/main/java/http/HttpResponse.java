package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String TEMPLATE_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";

    private String path;
    private MIME mime;
    private Map<String, String> headers;
    private byte[] body;
    private HttpStatusCode statusCode;

    public HttpResponse(String path, HttpStatusCode statusCode, MIME mime) throws IOException {
        this.path = path;
        this.statusCode = statusCode;
        this.mime = mime;

        if (mime.equals(MIME.html)) {
            this.body = Files.readAllBytes(new File(TEMPLATE_PATH + path).toPath());
        } else {
            this.body = Files.readAllBytes(new File(STATIC_PATH + path).toPath());
        }
    }

    public static HttpResponse ok(String path, MIME mime) throws IOException {
        return new HttpResponse(path, HttpStatusCode.OK, mime);
    }

    public static HttpResponse redirect(String path, MIME mime) throws IOException {
        return new HttpResponse(path, HttpStatusCode.FOUND, mime);
    }

    public void response(DataOutputStream dos) {
        if (this.statusCode == HttpStatusCode.OK) {
            response200Header(dos, body.length);
        }
        else if(this.statusCode == HttpStatusCode.FOUND) {
            response302Header(dos);
        }
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mime.getContentType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        String location = "/index.html";
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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
