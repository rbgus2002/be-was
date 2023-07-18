package webserver.http;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final HttpVersion httpVersion;
    private final HttpStatus httpStatus;
    private final Map<String, List<String>> metaData;
    private final byte[] body;

    public HttpResponse(HttpVersion httpVersion, HttpStatus httpStatus, Map<String, List<String>> metaData) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.metaData = metaData;
        this.body = null;
    }

    public HttpResponse(HttpVersion httpVersion, HttpStatus httpStatus, Map<String, List<String>> metaData, byte[] body) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.metaData = metaData;
        this.body = body;
    }

    public boolean hasBody() {
        return body != null;
    }

    public Map<String, List<String>> getMetaData() {
        return metaData;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
