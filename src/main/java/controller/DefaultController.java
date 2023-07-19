package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public enum DefaultController implements HttpController {
    DEFAULT_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        return request.getUrl();
    }


}
