package exception.unsupportedMediaType;

import exception.CustomException;
import webserver.http.Constants.HttpStatus;

public abstract class UnsupportedMediaTypeException extends CustomException {
    protected static final HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
