package exception.path;

public abstract class InvalidPathException extends RuntimeException {
    public InvalidPathException(String message) {
        super(message);
    }
}
