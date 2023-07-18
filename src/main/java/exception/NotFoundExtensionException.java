package exception;

public class NotFoundExtensionException extends WasException {
    private static final String MESSAGE = "올바르지 않은 확장자입니다.";

    public NotFoundExtensionException() {
        super(MESSAGE);
    }
}
