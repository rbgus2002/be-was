package webserver.exception;

import webserver.request.RequestPath;

public class InvalidPathParameterException extends RuntimeException {
    public InvalidPathParameterException() {
        super("경로의 변수가 잘못 설정되어있습니다.");
    }
}
