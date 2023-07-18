package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

import static utils.StringUtils.appendNewLine;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private String version = "HTTP/1.1";
    private HttpStatus httpStatus;
    private ContentType contentType;
    private byte[] body;

    public static class ResponseBuilder {
        private String version = "HTTP/1.1";
        private HttpStatus httpStatus = HttpStatus.OK;
        private ContentType contentType = ContentType.HTML;
        private byte[] body;

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

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    public HttpResponse(ResponseBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.contentType = builder.contentType;
        this.body = builder.body;
    }

    public void send(DataOutputStream dos) {
        responseHeader(dos);
        responseBody(dos);
    }


    private void responseHeader(DataOutputStream dos) {
        try {
            String statusLine = version + " " + httpStatus.getStatusCode() + " " + httpStatus.getStatusMessage();
            String contentTypeLine = "Content-Type: " + contentType.getMimeType() + ";charset=utf-8";
            String contentLengthLine = "Content-Length: " + body.length;
            dos.writeBytes(appendNewLine(statusLine));
            dos.writeBytes(appendNewLine(contentTypeLine));
            dos.writeBytes(appendNewLine(contentLengthLine));
            dos.writeBytes(appendNewLine(""));
            logger.debug("{} {} {} \r\n", version, httpStatus.getStatusCode(), httpStatus.getStatusMessage());
            logger.debug("Content-Type: {};charset=utf-8\n", contentType.getMimeType());
            logger.debug("Content-Length: {}\r\n", body.length);
            logger.debug("\r\n");
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
}
