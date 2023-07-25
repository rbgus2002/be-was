package exception.notFound;

public class InvalidMethodPathException extends NotFoundException {
    public InvalidMethodPathException(String rootPath, String fullPath) {
        super(rootPath + "를 다루는 컨트롤러에 " + fullPath + "를 다루는 메소드가 존재하지 않습니다.");
    }
}
