package common.http;

import common.enums.ContentType;
import common.enums.RequestMethod;

import java.util.Map;
import java.util.Map.Entry;

import static utils.StringUtils.NEW_LINE;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final String body;

    public HttpRequest(RequestLine requestLine, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public RequestMethod getRequestMethod() {
        return requestLine.getRequestMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public ContentType getContentType() {
        return requestLine.getContentType();
    }

    public Map<String, String> getParams() {
        return requestLine.getParams();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(NEW_LINE)
                .append("[RequestMethod] ").append(getRequestMethod()).append(NEW_LINE)
                .append("[URL] ").append(getPath()).append(NEW_LINE)
                .append("[Version] ").append(getVersion()).append(NEW_LINE);

        for (Entry<String, String> entry : headers.entrySet()) {
            sb.append("[Header] ").append(entry.getKey()).append(": ").append(entry.getValue()).append(NEW_LINE);
        }
        for (Entry<String, String> entry : getParams().entrySet()) {
            sb.append("[Param] ").append(entry.getKey()).append(": ").append(entry.getValue()).append(NEW_LINE);
        }
        sb.append("[Body] ").append(body).append(NEW_LINE);

        return sb.toString();
    }
}
