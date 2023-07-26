package exception.notFound;

import exception.CustomException;
import webserver.http.Constants.HttpStatus;

public abstract class NotFoundException extends CustomException {

    protected static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
