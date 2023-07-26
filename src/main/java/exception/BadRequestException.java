package exception;

public class BadRequestException extends RuntimeException {
    public static final String BAD_REQUEST_MESSAGE = "올바른 요청이 아닙니다.";

    public BadRequestException() {
        super(BAD_REQUEST_MESSAGE);
    }

}
