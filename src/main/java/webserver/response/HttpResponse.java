package webserver.response;

import webserver.ContentType;

import java.io.DataOutputStream;
import java.io.IOException;

import static utils.StringUtils.*;

public class HttpResponse {

    private final HttpStatus httpStatus;
    private final ContentType contentType;
    private final byte[] body;

    private HttpResponse(HttpStatus httpStatus, ContentType contentType, byte[] body) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.body = body;
    }

    public static HttpResponse of (HttpStatus httpStatus, ContentType contentType, byte[] body) {
        return new HttpResponse(httpStatus, contentType, body);
    }

    private String getStatusLine() {
        return "HTTP/1.1 " + httpStatus.getDescription() + NEWLINE;
    }

    private String getHeader() {
        return "Content-Type: " + contentType.getDescription() + NEWLINE +
                "Content-Length: " + body.length + NEWLINE +
                NEWLINE;
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        dos.writeBytes(getStatusLine() + getHeader());
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
