package webserver.http.response;

import java.io.Serializable;
import webserver.http.Headers;
import webserver.http.Http.MIME;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.response.process.ContentProcessStrategy;

public class HttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ResponseLine responseLine;
    private final Headers headers;
    private final byte[] body;

    public HttpResponse(final ResponseLine responseLine, final Headers headers, final byte[] body) {
        this.responseLine = responseLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse notFound(final MIME mime) {
        return new HttpResponse(new ResponseLine(StatusCode.NOT_FOUND), Headers.create(mime), null);
    }

    public static HttpResponse internalError(final MIME mime) {
        return new HttpResponse(new ResponseLine(StatusCode.INTERNAL_ERROR), Headers.create(mime), null);
    }

    public static HttpResponse badRequest(final MIME mime) {
        return new HttpResponse(new ResponseLine(StatusCode.BAD_REQUEST), Headers.create(mime), null);
    }

    public static HttpResponse from(final HttpRequest httpRequest) {
        ContentProcessStrategy contentProcessStrategy = httpRequest.getMIME().getStrategy();
        return contentProcessStrategy.process(httpRequest);
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
