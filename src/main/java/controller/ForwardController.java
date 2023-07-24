package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class ForwardController implements Controller {
    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) {
        if(response != null) {
            response.setToUrl(request.getUrl());
        }
        return response;
    }
}
