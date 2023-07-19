package support.exception;

import webserver.response.HttpStatus;

public class ServerErrorException extends HttpException {

    public ServerErrorException() {
        super(HttpStatus.SERVER_ERROR);
    }

}
