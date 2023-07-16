package webserver.http.response;

import webserver.http.HttpHeaders;

public class HttpRequestImpl extends HttpResponse {
    private HttpHeaders headers;
    private byte[] body;

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
}
