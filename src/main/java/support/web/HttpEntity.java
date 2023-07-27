package support.web;

import webserver.Header;
import webserver.response.HttpStatus;

public class HttpEntity {

    private final HttpStatus status;
    private final Header header;

    public HttpEntity(HttpStatus status) {
        this(status, null);
    }

    public HttpEntity(HttpStatus status, Header header) {
        this.status = status;
        this.header = header;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Header getHeader() {
        return header;
    }

}
