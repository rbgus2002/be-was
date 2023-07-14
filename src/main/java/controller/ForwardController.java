package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ForwardController implements Controller {



    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        return request.getUrl();
    }
}
