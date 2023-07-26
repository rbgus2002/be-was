package support.web;

import webserver.Header;
import webserver.response.HttpStatus;

import javax.annotation.Nullable;

public class ResponseEntity {

    private final HttpStatus status;
    private final Header header;

    public ResponseEntity(HttpStatus status) {
        this(status, null);
    }

    public ResponseEntity(HttpStatus status, @Nullable Header header) {
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
