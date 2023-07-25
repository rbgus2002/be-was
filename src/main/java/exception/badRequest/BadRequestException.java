package exception.badRequest;

import exception.CustomException;
import webserver.Constants.HttpStatus;

public abstract class BadRequestException extends CustomException {
    protected static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
