package exception.internalServerError;

public class MethodAccessException extends InternalServerException {
    public MethodAccessException(String methodName) {
        super(methodName + "에 엑세스를 시도했지만 허용되지 않았습니다.");
    }
}
