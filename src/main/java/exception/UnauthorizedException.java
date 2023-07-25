package exception;

public class UnauthorizedException extends WasException {
    private static final String MESSAGE = "인증되지 않는 사용자입니다.";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}