package webserver.response;

import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpResponseMessage {
    private String version = "HTTP/1.1";
    private String statusCode = "";
    private String statusMessage = "";
    private Map<String, String> headerMap = new HashMap<>();
    private byte[] body;

    public HttpResponseMessage() {
        setHeader("Content-Length", "0");
        setHeader("Content-Type", "");
    }

    public HttpResponseMessage(HttpStatus httpStatus, byte[] body) {
        this.statusCode = httpStatus.getCode();
        this.statusMessage = httpStatus.getMessage();
        this.body = body;
        setHeader("Content-Length", String.valueOf(body.length));
        setHeader("Content-Type", "text/html;charset=utf-8");
    }

    public void setStatusLine(HttpStatus httpStatus) {
        this.statusCode = httpStatus.getCode();
        this.statusMessage = httpStatus.getMessage();

    }

    public void setBody(String body) {
        setBody(body.getBytes());
    }

    public void setBody(byte[] body) {
        this.body = body;
        setHeader("Content-Length", String.valueOf(body.length));
        setHeader("Content-Type", "text/html;charset=utf-8");
    }

    public void setHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public String getResponseHeader() {
        StringBuilder responseMessage = new StringBuilder();
        responseMessage.append(version).append(" ").append(statusCode).append(" ").append(statusMessage).append(NEW_LINE);
        for (String key : headerMap.keySet()) {
            responseMessage.append(key).append(": ").append(headerMap.get(key)).append(NEW_LINE);
        }
        return responseMessage.toString();
    }

    public byte[] getResponseBody() {
        return body;
    }

}
