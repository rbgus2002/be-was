package support.web.exception;

public class ServerErrorException extends Exception {

    public ServerErrorException() {
        this(null);
    }

    public ServerErrorException(final String message) {
        super(message);
    }

}
