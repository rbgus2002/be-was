package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public enum HomeController implements HttpController {
    HOME_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        return "/index.html";

    }
}
