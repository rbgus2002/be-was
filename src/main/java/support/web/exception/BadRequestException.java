package support.web.exception;

import webserver.response.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

}
