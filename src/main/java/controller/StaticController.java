package controller;

import http.HttpRequest;
import http.HttpResponse;

public class StaticController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if ("GET".equals(request.getMethod())) {
            return request.getUrl();
        }
        response.setMethodNotAllowed();
        return "/error/405.html";
    }
}
