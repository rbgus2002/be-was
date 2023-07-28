package exception;

import webserver.http.Constants.HttpStatus;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
    public abstract HttpStatus getHttpStatus();
}
