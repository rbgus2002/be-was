package webserver.exception;

public class InvalidPathLengthException extends RuntimeException{
    public InvalidPathLengthException() {
        super("경로의 길이가 적절하지 않습니다.");
    }
}
