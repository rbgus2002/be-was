package exception.internalServerError;

public class FileRenderException extends InternalServerException {
    public FileRenderException(String filePath) {
        super(filePath + " 위치의 파일을 렌더링 하는 와중에 예외가 발생했습니다.");
    }
}
