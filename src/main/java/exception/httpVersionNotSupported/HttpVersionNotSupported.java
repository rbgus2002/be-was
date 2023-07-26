package exception.httpVersionNotSupported;

import webserver.Constants.HttpStatus;

public abstract class HttpVersionNotSupported extends RuntimeException {
    protected static final HttpStatus httpStatus = HttpStatus.HTTP_VERSION_NOT_SUPPORTED;

    public HttpVersionNotSupported(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
