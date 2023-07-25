package exception.notFound;

import webserver.Constants.HttpStatus;

public abstract class NotFoundException extends RuntimeException {

    protected static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
