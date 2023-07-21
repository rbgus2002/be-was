package support.exception;

import webserver.response.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
