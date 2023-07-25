package exception.internalServerError;

import webserver.Constants.HttpStatus;

public abstract class InternalServerException extends RuntimeException {
    protected static final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
