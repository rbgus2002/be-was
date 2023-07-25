package exception.unsupportedMediaType;

public class InvalidContentTypeException extends UnsupportedMediaTypeException {
    public InvalidContentTypeException(String contentType) {
        super(contentType + "은 서버에서 지원하는 MIME 가 아닙니다.");
    }
}
