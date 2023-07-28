package exception;

import webserver.http.HttpUtil.STATUS;

public class InternalServerErrorException extends HTTPException {
    public InternalServerErrorException() {
        super(STATUS.INTERNAL_SERVER_ERROR, "서버에서 처리 중 오류가 발생했습니다.");
    }
}
