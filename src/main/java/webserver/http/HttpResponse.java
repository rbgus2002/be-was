package webserver.http;

import webserver.utils.HttpConstants;

public class HttpResponse {
    private HttpStatus status;
    private final HttpHeaders headers;
    private byte[] body;

    public HttpResponse() {
        headers = new HttpHeaders();
    }

    public String get(String name) {
        return headers.get(name);
    }

    public void set(String name, String value) {
        headers.put(name, value);
    }

    public void set(String name, int value) {
        headers.put(name, String.valueOf(value));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isBodyEmpty() {
        return body == null || body.length == 0;
    }

    public String getHeaderMessage() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(status.getStatusLine()).append(HttpConstants.CRLF);

        for (String fieldName : headers.getFieldNames()) {
            stringBuilder.append(fieldName)
                    .append(": ")
                    .append(headers.get(fieldName))
                    .append(HttpConstants.CRLF);
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
