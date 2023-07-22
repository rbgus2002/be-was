package exception;

public class NotSupportedContentTypeException extends RuntimeException{
    public NotSupportedContentTypeException() {
        super("This Content Type is not supported");
    }
}
