package webserver;

public class HttpRequestLine {
    private String method;
    private String uri;
    private String version;

    private HttpRequestLine(String requestLine) {
        // Postman에서 API 호출 시 NPE 발생 방지
        // TODO : null 들어오는 이유 확인하기
        if(requestLine == null){
            return;
        }
        String[] token = requestLine.split(" ");
        this.method = token[0];
        this.uri = token[1];
        this.version = token[2];
    }

    public static HttpRequestLine from(String requestLine){
        return new HttpRequestLine(requestLine);
    }

    String getMethod() {
        return method;
    }

    String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return method + " " + uri + " " + version;
    }
}
