package webserver.response;

import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpResponseMessage {
    private String version = "HTTP/1.1";
    private String statusCode = "";
    private String statusMessage = "";
    private Map<String, String> headerMap = new HashMap<>();
    private byte[] body = new byte[0];

    public HttpResponseMessage() {
        setHeader("Content-Length", "0");
        setHeader("Content-Type", "");
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
    }

    public void setHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public String getResponseHeader() {
        StringBuilder responseMessage = new StringBuilder();
        responseMessage.append(renderResponseStatusLine());
        for (String key : headerMap.keySet()) {
            responseMessage.append(key).append(": ").append(headerMap.get(key)).append(NEW_LINE);
        }
        return responseMessage.toString();
    }

    private String renderResponseStatusLine() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(version).append(" ").append(statusCode).append(" ").append(statusMessage).append(NEW_LINE).toString();
    }

    public byte[] getResponseBody() {
        return body;
    }
}
