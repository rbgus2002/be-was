package exception;

public abstract class WasException extends RuntimeException {
    public WasException(String message) {
        super(message);
    }
}