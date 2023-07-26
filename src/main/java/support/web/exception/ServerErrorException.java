package support.web.exception;

import webserver.response.HttpStatus;

public class ServerErrorException extends HttpException {

    public ServerErrorException() {
        this(null);
    }

    public ServerErrorException(final String message) {
        super(message, HttpStatus.SERVER_ERROR);
    }

}
