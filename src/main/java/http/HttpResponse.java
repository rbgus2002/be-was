package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String version = "HTTP/1.1";
    private HttpStatus httpStatus = HttpStatus.OK;
    private byte[] body;
    private DataOutputStream dos;

    public HttpResponse(Map<HttpStatus, byte[]> response, DataOutputStream dos) {
        response.forEach((status, bytes) -> {
            this.httpStatus = status;
            this.body = bytes;
        });
        this.dos = dos;
    }

    public void send() {
        responseHeader();
        responseBody();
    }


    private void responseHeader() {
        try {
            dos.writeBytes(version + " " + httpStatus.getStatusCode() + " " + httpStatus.getStatusMessage() + "\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody() {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
