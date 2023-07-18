package webserver.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private HttpStatus status;
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        headers = new LinkedHashMap<>();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isBodyEmpty() {
        return body == null || body.length == 0;
    }

    public String getHeaderMessage() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(status.getStatusLine())
                .append(HttpConstant.CRLF);

        for (String key : headers.keySet()) {
            stringBuilder.append(key)
                    .append(": ")
                    .append(headers.get(key))
                    .append(HttpConstant.CRLF);
        }

        return stringBuilder.toString();
    }

    public byte[] getHeaderBytes() {
        return getHeaderMessage().getBytes();
    }

    public byte[] getBodyBytes() {
        return body;
    }
}
