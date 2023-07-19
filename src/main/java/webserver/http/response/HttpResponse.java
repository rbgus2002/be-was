package webserver.http.response;

import java.io.Serializable;
import webserver.http.Headers;
import webserver.http.Http.MIME;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.response.process.HtmlContentProcessStrategy;

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

    public static HttpResponse notFound() {
        return new HttpResponse(new ResponseLine(StatusCode.NOT_FOUND), Headers.create(MIME.HTML), null);
    }

    public static HttpResponse internalError() {
        return new HttpResponse(new ResponseLine(StatusCode.INTERNAL_ERROR), Headers.create(MIME.HTML), null);
    }

    public static HttpResponse from(final HttpRequest httpRequest) {
        HtmlContentProcessStrategy htmlContentProcessStrategy = new HtmlContentProcessStrategy();
        return htmlContentProcessStrategy.process(httpRequest);
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
