package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

import static http.Extension.HTML;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String version = "HTTP/1.1";
    private HttpStatus httpStatus;
    private String contentType;
    private String location;
    private byte[] body;

    public static class ResponseBuilder {
        private String version = "HTTP/1.1";
        private HttpStatus httpStatus = HttpStatus.OK;
        private String contentType = MIME.getMIME().get(HTML);
        private String location = "/index.html";
        private byte[] body;

        public ResponseBuilder() {
        }

        public ResponseBuilder setStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ResponseBuilder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public ResponseBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public ResponseBuilder setBody(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    public HttpResponse(ResponseBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.contentType = builder.contentType;
        this.location = builder.location;
        this.body = builder.body;
    }

    public void send(DataOutputStream dos) {
        responseHeader(dos);
        responseBody(dos);
    }


    private void responseHeader(DataOutputStream dos) {
        try {
            String statusLine = String.format("%s %d %s \r\n", version, httpStatus.getStatusCode(), httpStatus.getStatusMessage());
            String contentTypeLine = String.format("Content-Type: %s;charset=utf-8 \r\n", contentType);
            String contentLengthLine = String.format("Content-Length: %d \r\n", body.length);
            String locationLine = String.format("Location: %s \r\n", location);
            dos.writeBytes(statusLine);
            dos.writeBytes(contentTypeLine);
            dos.writeBytes(contentLengthLine);
            dos.writeBytes(locationLine);
            dos.writeBytes("\r\n");
            logger.debug(statusLine);
            logger.debug(contentTypeLine);
            logger.debug(contentLengthLine);
            logger.debug(locationLine);
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

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
