package support.web.exception;

import webserver.response.HttpStatus;

public abstract class HttpException extends Exception {

    private final HttpStatus httpStatus;

    public HttpException(HttpStatus status) {
        super();
        this.httpStatus = status;
    }

    protected HttpException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
