package exception;

public class InvalidVersionException extends RuntimeException {
    public InvalidVersionException(String version) {
        super("올바르지 않은 버전입니다: " + version);
    }
}
