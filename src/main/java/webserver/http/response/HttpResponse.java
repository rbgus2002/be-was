package webserver.http.response;

import exception.internalServerError.HttpResponseSendException;
import webserver.http.Constants.HeaderOption;
import webserver.http.Constants.HttpStatus;
import webserver.http.Constants.HttpVersion;
import webserver.http.Header;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static support.utils.StringUtils.*;

public class HttpResponse {

    private HttpVersion httpVersion;
    private HttpStatus httpStatus;
    private final Header header;
    private byte[] body;

    private HttpResponse(Header header) {
        this.header = header;
    }

    public static HttpResponse createEmpty() {
        return new HttpResponse(Header.createEmpty());
    }

    private String getStatusLine() {
        return httpVersion.getDescription() + SPACE + httpStatus.getDescription() + NEWLINE;
    }

    private String getHeader() {
        return header.toString();
    }

    public void addHeaderElement(final String key, final String value) {
        header.addElement(key, value);
    }

    public void addCookieOption(final String key, final String value) {
        header.addCookieOption(key, value);
    }

    public void setHttpVersion(final HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setBody(final byte[] body) {
        this.body = body;
        addHeaderElement(HeaderOption.CONTENT_LENGTH, String.valueOf(body.length));
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
        return httpVersion == that.httpVersion && httpStatus == that.httpStatus && Objects.equals(header, that.header) && Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(httpVersion, httpStatus, header);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
