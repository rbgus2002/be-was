package exception.internalServerError;

public class HttpResponseSendException extends InternalServerException{
    public HttpResponseSendException() {
        super("Response 를 보내는 와중에 예외가 발생했습니다.");
    }
}
