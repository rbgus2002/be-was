package exception.path;

public class InvalidPathFormatException extends InvalidPathException{
    public InvalidPathFormatException(String path) {
        super(path + "의 형식가 올바르지 않습니다.");
    }
}
