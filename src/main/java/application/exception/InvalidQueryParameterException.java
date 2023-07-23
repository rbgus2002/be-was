package application.exception;

public class InvalidQueryParameterException extends RuntimeException {
    public InvalidQueryParameterException() {
        super("Query parameter 가 올바르지 않습니다.");
    }
}
