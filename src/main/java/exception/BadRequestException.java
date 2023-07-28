package exception;

import webserver.http.HttpUtil.STATUS;

public class BadRequestException extends HTTPException {
    public BadRequestException() {
        super(STATUS.BAD_REQUEST, "잘못된 형태의 요청입니다.");
    }
}
