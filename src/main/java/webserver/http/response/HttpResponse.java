package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpHeaders;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final HttpStatusLine httpStatusLine;
    private final HttpHeaders httpHeaders;
    private final byte[] body;

    private HttpResponse(HttpStatusLine httpStatusLine, HttpHeaders httpHeaders, byte[] body) {
        this.httpStatusLine = httpStatusLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public void responseStatic(DataOutputStream dos) {
        response200Header(dos);
        responseBody(dos);
    }

    public void responseDynamic(DataOutputStream dos) {
        response302Header(dos);
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: 0" + "\r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static HttpResponse createStatic(String requestUri) throws IOException {
        byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + requestUri).toPath());
        return new HttpResponse(
                HttpStatusLine.createStaticStatusLine(),
                HttpHeaders.createStaticStatusHeaders(body.length),
                body
        );
    }

    public static HttpResponse createRedirect() {
        byte[] emptyBody = new byte[0];
        return new HttpResponse(
                HttpStatusLine.createRedirectStatusLine(),
                HttpHeaders.createRedirectStatusHeaders(),
                emptyBody
        );
    }

}
