package exception;

import webserver.Constants.HttpStatus;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
    public abstract HttpStatus getHttpStatus();
}
