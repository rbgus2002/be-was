package webserver.response;

import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class ResponseMessage {
    private final String version = "HTTP/1.1";
    private final String statusCode;
    private final String statusMessage;
    private final Map<String, String> metadata = new HashMap<>();
    private final byte[] body;

    public ResponseMessage(HttpStatus httpStatus) {
        this.statusCode = httpStatus.getCode();
        this.statusMessage = httpStatus.getMessage();
        this.body = "".getBytes();
        setMetadata("Content-Length", "0");
        setMetadata("Content-Type", "");
    }

    public ResponseMessage(HttpStatus httpStatus, byte[] body) {
        this.statusCode = httpStatus.getCode();
        this.statusMessage = httpStatus.getMessage();
        this.body = body;
        setMetadata("Content-Length", String.valueOf(body.length));
        setMetadata("Content-Type", "text/html;charset=utf-8");
    }

    public void setMetadata(String key, String value) {
        metadata.put(key, value);
    }

    public String getResponseHeader() {
        StringBuilder responseMessage = new StringBuilder();
        responseMessage.append(version).append(" ").append(statusCode).append(" ").append(statusMessage).append(NEW_LINE);
        for (String key : metadata.keySet()) {
            responseMessage.append(key).append(": ").append(metadata.get(key)).append(NEW_LINE);
        }
        return responseMessage.toString();
    }

    public byte[] getResponseBody() {
        return body;
    }
}
