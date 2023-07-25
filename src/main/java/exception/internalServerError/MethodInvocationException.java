package exception.internalServerError;

public class MethodInvocationException extends InternalServerException {
    public MethodInvocationException(String methodName) {
        super(methodName + "을 호출하는 동안 예외가 발생했습니다.");
    }
}
