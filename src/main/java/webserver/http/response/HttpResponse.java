package webserver.http.response;

import java.io.Serializable;
import java.util.Map;
import webserver.http.Headers;
import webserver.http.Http;
import webserver.http.Http.MIME;
import webserver.http.Http.StatusCode;

public class HttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private ResponseLine responseLine;
    private final Headers headers;
    private byte[] body;

    public HttpResponse(final ResponseLine responseLine, final Headers headers, final byte[] body) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse init() {
        return new HttpResponse(null, new Headers(), null);
    }

    public void ok(final MIME mime, final byte[] body) {
        set(StatusCode.OK, Headers.create(mime), body);
    }

    public void found(final String url) {
        set(StatusCode.FOUND, new Headers(Map.of(Http.Headers.LOCATION.getName(), url)), null);
    }

    public void notFound(final MIME mime) {
        set(StatusCode.NOT_FOUND, Headers.create(mime), null);
    }

    public void internalError(final MIME mime) {
        set(StatusCode.INTERNAL_ERROR, Headers.create(mime), null);
    }

    public void badRequest(final MIME mime) {
        set(StatusCode.BAD_REQUEST, Headers.create(mime), null);
    }

    private void set(final StatusCode statusCode, final Headers headers, final byte[] body) {
        this.responseLine = new ResponseLine(statusCode);
        this.headers.addAll(headers);
        this.body = body;
    }

    public ResponseLine getResponseLine() {
        return responseLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }
}
