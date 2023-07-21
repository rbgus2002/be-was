package exception;

public class NoSuchControllerMethodException extends RuntimeException {
    public NoSuchControllerMethodException(String message) {
        super(message);
    }

}
