package exception;

import http.HttpStatus;

public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String filePath;

    public CustomException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
