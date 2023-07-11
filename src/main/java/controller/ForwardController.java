package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class ForwardController implements Controller{

    private String url;
    public ForwardController(String url) {
        this.url = url;
    }

    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        return url;
    }
}
