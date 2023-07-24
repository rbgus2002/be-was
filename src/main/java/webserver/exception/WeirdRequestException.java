package webserver.exception;

public class WeirdRequestException extends IllegalArgumentException{
    public WeirdRequestException(String errorMessage) {
        super(errorMessage);
    }
}
