package exception.httpVersionNotSupported;

public class InvalidVersionException extends HttpVersionNotSupported {
    public InvalidVersionException(String version) {
        super("올바르지 않은 HTTP 버전입니다: " + version);
    }
}
