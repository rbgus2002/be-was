package exception.notFound;

public class InvalidControllerPathException extends NotFoundException {
    public InvalidControllerPathException(String rootPath) {
        super(rootPath + "를 다루는 컨트롤러가 존재하지 않습니다.");
    }
}
