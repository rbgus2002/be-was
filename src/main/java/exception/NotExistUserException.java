package exception;

import http.HttpStatus;

import static http.FilePath.LOGIN_FAILED;

public class NotExistUserException extends CustomException {

    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    private final String filePath = LOGIN_FAILED;
    public NotExistUserException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
