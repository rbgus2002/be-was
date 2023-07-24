package webserver.exception;

public class InvalidPathException extends RuntimeException {
    public InvalidPathException() {
        super("경로가 잘못 설정되어있습니다.");
    }
}
