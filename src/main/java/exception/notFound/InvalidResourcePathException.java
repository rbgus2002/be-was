package exception.notFound;

public class InvalidResourcePathException extends NotFoundException {
    public InvalidResourcePathException(String resourcePath) {
        super(resourcePath + "에 리소스 파일이 존재하지 않습니다.");
    }
}
