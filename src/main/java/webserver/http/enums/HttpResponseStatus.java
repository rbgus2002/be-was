package webserver.http.enums;

public enum HttpResponseStatus {
    OK(200, "OK", ""),
    FOUND(302, "Found", ""),
    BAD_REQUEST(400, "Bad Request", "요청한 파일을 찾을 수 없습니다."),
    NOT_FOUND(404, "Not Found", "잘못된 요청입니다.");

    private int statusCode;
    private String statusText;
    private String body;

    HttpResponseStatus(int statusCode, String statusText, String body) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getBody() {
        return body;
    }
}
