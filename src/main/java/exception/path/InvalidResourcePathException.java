package exception.path;

public class InvalidResourcePathException extends InvalidPathException {
    public InvalidResourcePathException(String resourceName) {
        super(resourceName + "에 리소스 파일이 존재하지 않습니다.");
    }
}
