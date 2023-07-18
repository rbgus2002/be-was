package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String version = "HTTP/1.1";
    private HttpStatus httpStatus;
    private ContentType contentType;
    private byte[] body;
    private DataOutputStream dos;

    public static class ResponseBuilder {
        private String version = "HTTP/1.1";
        private HttpStatus httpStatus = HttpStatus.OK;
        private ContentType contentType = ContentType.HTML;
        private byte[] body;
        private DataOutputStream dos;

        public ResponseBuilder() {
        }

        public ResponseBuilder setStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ResponseBuilder setContentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public ResponseBuilder setBody(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponse build(DataOutputStream dos) {
            this.dos = dos;
            return new HttpResponse(this);
        }
    }

    public HttpResponse(ResponseBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.contentType = builder.contentType;
        this.body = builder.body;
        this.dos = builder.dos;
    }

    public void send() {
        responseHeader();
        responseBody();
    }


    private void responseHeader() {
        try {
            dos.writeBytes(version + " " + httpStatus.getStatusCode() + " " + httpStatus.getStatusMessage() + "\r\n");
            dos.writeBytes("Content-Type: " + contentType.getMimeType() + ";charset=utf-8\r\n");
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
