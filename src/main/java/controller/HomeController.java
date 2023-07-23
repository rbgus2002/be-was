package controller;

import http.HttpResponse;

import java.util.Map;

public enum HomeController implements HttpController {
    HOME_CONTROLLER;

    @Override
    public String process(Map<String, String> requestParams, HttpResponse response) {
        return "/index.html";
    }
}
