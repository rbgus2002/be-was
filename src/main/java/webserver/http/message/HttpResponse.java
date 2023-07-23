package webserver.http.message;

import webserver.http.Mime;

import java.nio.charset.StandardCharsets;

public class HttpResponse {
    public static final String NOT_FOUND_BODY_HTML = "<html><body>NOT FOUND</body><html>";
    public static final String BAD_REQUEST_HTML = "<html><body>badRequest</body><html>";

    private final HttpVersion httpVersion;
    private final HttpStatus httpStatus;
    private final HttpHeaders httpHeaders;
    private final byte[] body;

    public HttpResponse(HttpVersion httpVersion, HttpStatus httpStatus, HttpHeaders httpHeaders) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.body = new byte[0];
    }

    public HttpResponse(HttpVersion httpVersion, HttpStatus httpStatus, HttpHeaders httpHeaders, byte[] body) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static HttpResponse okWithFile(byte[] file, Mime mime) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader(HttpHeaders.CONTENT_TYPE, mime.getContentType());
        httpHeaders.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length));
        return new HttpResponse(HttpVersion.V1_1, HttpStatus.OK, httpHeaders, file);
    }

    public static HttpResponse notFound() {
        byte[] notFoundHtml = NOT_FOUND_BODY_HTML.getBytes(StandardCharsets.UTF_8);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader(HttpHeaders.CONTENT_TYPE, Mime.HTML.getContentType());
        httpHeaders.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(notFoundHtml.length));

        return new HttpResponse(HttpVersion.V1_1, HttpStatus.NOT_FOUND, httpHeaders, notFoundHtml);
    }

    public static HttpResponse redirect(String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader(HttpHeaders.LOCATION, url);

        return new HttpResponse(HttpVersion.V1_1, HttpStatus.FOUND, httpHeaders);
    }

    public static HttpResponse badRequest() {
        byte[] badRequestHtml = BAD_REQUEST_HTML.getBytes(StandardCharsets.UTF_8);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader(HttpHeaders.CONTENT_TYPE, Mime.HTML.getContentType());
        httpHeaders.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(badRequestHtml.length));

        return new HttpResponse(HttpVersion.V1_1, HttpStatus.BAD_REQUEST, httpHeaders, badRequestHtml);
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
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

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion=" + httpVersion +
                ", httpStatus=" + httpStatus +
                ", httpHeaders=" + httpHeaders +
                '}';
    }
}
