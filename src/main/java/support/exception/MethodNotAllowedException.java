package support.exception;

import webserver.response.HttpStatus;

public class MethodNotAllowedException extends HttpException {

    public MethodNotAllowedException() {
        super(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
