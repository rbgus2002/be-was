package exception;

import webserver.http.HttpUtil.STATUS;

public class NotFoundException extends HTTPException {
    public NotFoundException() {
        super(STATUS.NOT_FOUND, "서버가 지원하지 않는 경로의 요청입니다.");
    }
}
