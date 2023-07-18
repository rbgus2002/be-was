package webserver.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private HttpStatus status;
    private Map<String, String> headers;

    public HttpResponse() {
        headers = new LinkedHashMap<>();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(status.getStatusLine()).append(HttpHeaderName.CRLF.getName());

        for (String key : headers.keySet()) {
            stringBuilder.append(key).append(": ").append(headers.get(key)).append(HttpHeaderName.CRLF.getName());
        }

        stringBuilder.append(HttpHeaderName.CRLF.getName());

        return stringBuilder.toString();
    }
}
