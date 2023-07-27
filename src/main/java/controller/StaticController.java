package controller;

import http.HttpRequest;
import http.HttpResponse;

public class StaticController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        return request.getUrl();
    }
}
