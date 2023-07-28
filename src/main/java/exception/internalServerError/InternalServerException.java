package exception.internalServerError;

import exception.CustomException;
import webserver.http.Constants.HttpStatus;

public abstract class InternalServerException extends CustomException {
    protected static final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
