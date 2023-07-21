package exception;

public class BadRequestException extends WasException {
    private static final String MESSAGE = "올바르지 않은 경로입니다.";

    public BadRequestException() {
        super(MESSAGE);
    }
}