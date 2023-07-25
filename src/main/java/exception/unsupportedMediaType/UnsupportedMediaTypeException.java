package exception.unsupportedMediaType;

import webserver.Constants.HttpStatus;

public abstract class UnsupportedMediaTypeException extends RuntimeException {
    protected static final HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
