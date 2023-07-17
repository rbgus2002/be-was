package webserver.http.response;

import webserver.http.HttpHeaders;

public class HttpRequestImpl extends HttpResponse {
    private static final String redirectionHeader = "Location";
    private String uri = HttpResponse.NOT_RENDER_URI;
    private HttpStatus httpStatus = HttpStatus.OK;
    private final HttpHeaders headers = new HttpHeaders();
    private byte[] body = new byte[0];

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getUri() {
        return this.uri;
    }

    @Override
    public void setStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public void setHeader(String headerName, String value) {
        headers.addHeader(headerName, value);
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public void sendRedirection(String uri) {
        setStatus(HttpStatus.FOUND);
        setHeader(redirectionHeader, uri);
    }
}
