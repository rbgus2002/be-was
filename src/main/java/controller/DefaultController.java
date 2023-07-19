package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public enum DefaultController implements HttpController {
    DEFAULT_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        return request.getUrl();
    }
}
