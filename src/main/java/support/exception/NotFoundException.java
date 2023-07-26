package support.exception;

import webserver.response.HttpStatus;

public class NotFoundException extends HttpException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

}
