package webserver.response;

import exception.internalServerError.HttpResponseSendException;
import webserver.Constants.ContentType;
import webserver.Constants.HttpStatus;
import webserver.Constants.HttpVersion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static support.utils.StringUtils.*;

public class HttpResponse {

    private final HttpVersion httpVersion;
    private final HttpStatus httpStatus;
    private final ContentType contentType;
    private final byte[] body;

    private HttpResponse(HttpVersion httpVersion, HttpStatus httpStatus, ContentType contentType, byte[] body) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.body = body;
    }

    public static HttpResponse ofWithBodyData(HttpVersion httpVersion, HttpStatus httpStatus, ContentType contentType, byte[] body) {
        return new HttpResponse(httpVersion, httpStatus, contentType, body);
    }

    public static HttpResponse ofWithStatusOnly(HttpVersion httpVersion, HttpStatus httpStatus) {
        byte[] body = httpStatus.getDescription().getBytes();
        return new HttpResponse(httpVersion, httpStatus, ContentType.HTML, body);
    }

    private String getStatusLine() {
        return httpVersion.getDescription() + SPACE + httpStatus.getDescription() + NEWLINE;
    }

    private String getHeader() {
        return "Content-Type:" + SPACE + contentType.getDescription() + NEWLINE +
                "Content-Length:" + SPACE + body.length + NEWLINE +
                NEWLINE;
    }

    public void sendResponse(final DataOutputStream dos) throws HttpResponseSendException {
        try {
            dos.writeBytes(getStatusLine() + getHeader());
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            throw new HttpResponseSendException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return httpVersion == that.httpVersion && httpStatus == that.httpStatus && contentType == that.contentType && Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(httpVersion, httpStatus, contentType);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
