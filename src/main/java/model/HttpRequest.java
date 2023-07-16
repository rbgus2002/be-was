package model;

import model.enums.Method;

public class HttpRequest {
    private final RequestUri requestUri;
    private final String protocol;
    private final Method method;
    private final HttpHeader httpHeader;
    private final String body;

    public HttpRequest(RequestUri requestUri, String protocol, Method method, HttpHeader httpHeader, String body) {
        this.requestUri = requestUri;
        this.protocol = protocol;
        this.method = method;
        this.httpHeader = httpHeader;
        this.body = body;
    }

    public boolean match(Method method, String uri) {
        return this.method == method && this.requestUri.match(uri);
    }

    public boolean match(Method method) {
        return this.method == method;
    }

    public HttpHeader getHeader() {
        return httpHeader.newInstance();
    }

    public String getProtocol() {
        return this.protocol;
    }

    public boolean endsWithHtml() {
        return requestUri.uriEndsWith(HTML_FILE_FORMAT);
    }

}
