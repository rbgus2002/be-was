package webserver.http.response;

import webserver.http.HttpHeaders;

public class HttpResponse {

    private final HttpStatusLine httpStatusLine;
    private final HttpHeaders httpHeaders;
    private final byte[] body;

    public HttpResponse(HttpStatusLine httpStatusLine, HttpHeaders httpHeaders, byte[] body) {
        this.httpStatusLine = httpStatusLine;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }
}
