package exception.badRequest;

public class MissingParameterException extends BadRequestException {
    public MissingParameterException() {
        super("필수 매개 변수가 올바르지 않습니다.");
    }
}
