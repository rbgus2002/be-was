package exception;

import http.HttpStatus;

import static http.FilePath.LOGIN;

public class SessionIdException extends CustomException {

    private final HttpStatus httpStatus = HttpStatus.OK;
    private final String filePath = LOGIN;
    public SessionIdException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
