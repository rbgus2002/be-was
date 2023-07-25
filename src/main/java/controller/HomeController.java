package controller;

import http.HttpRequest;
import http.HttpResponse;

public enum HomeController implements HttpController {
    HOME_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        return "/index.html";
    }
}
