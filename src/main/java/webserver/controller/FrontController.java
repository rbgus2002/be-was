package webserver.controller;

import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;

public class FrontController {

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    public FrontController(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }


}
