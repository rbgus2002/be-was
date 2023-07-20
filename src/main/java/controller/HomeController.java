package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public enum HomeController implements HttpController {
    HOME_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        return "/index.html";
    }
}
