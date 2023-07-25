package controller;

import http.HttpRequest;
import http.HttpResponse;

public enum StaticController implements HttpController {
    STATIC_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        return request.getUrl();
    }
}
