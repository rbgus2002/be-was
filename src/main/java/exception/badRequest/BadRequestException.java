package exception.badRequest;

import webserver.Constants.HttpStatus;

public abstract class BadRequestException extends RuntimeException {
    protected static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
