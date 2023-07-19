package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ServletContainer;

import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);

    private String body;

    private Map<String, String> headers;

    private final String uri;
    private final HttpRequest request;

    private String version;

    private int statusCode;

    public HttpResponse(HttpRequest request) {
        this.request = request;

        uri = request.getRequestURI();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
    public String body() {
        return body;
    }

    public int getStatus() {
        return statusCode;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    //TODO: sendRedirect 구현

    //TODO: sendError 구현


}
