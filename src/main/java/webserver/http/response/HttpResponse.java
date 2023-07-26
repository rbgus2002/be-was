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
    private Headers headers;
    private byte[] body;

    public HttpResponse(final ResponseLine responseLine, final Headers headers, final byte[] body) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse init() {
        return new HttpResponse(null, null, null);
    }

    public void ok(final MIME mime, final byte[] body) {
        this.responseLine = new ResponseLine(StatusCode.OK);
        this.headers = Headers.create(mime);
        this.body = body;
    }

    public void found(final String url) {
        this.responseLine = new ResponseLine(StatusCode.FOUND);
        this.headers = new Headers(Map.of(Http.Headers.LOCATION.getName(), url));
        this.body = null;
    }

    public void notFound(final MIME mime) {
        this.responseLine = new ResponseLine(StatusCode.NOT_FOUND);
        this.headers = Headers.create(mime);
        this.body = null;
    }

    public void internalError(final MIME mime) {
        this.responseLine = new ResponseLine(StatusCode.INTERNAL_ERROR);
        this.headers = Headers.create(mime);
        this.body = null;
    }

    public void badRequest(final MIME mime) {
        this.responseLine = new ResponseLine(StatusCode.BAD_REQUEST);
        this.headers = Headers.create(mime);
        this.body = null;
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
