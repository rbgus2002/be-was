package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


public class HomeController implements Controller {


    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        return "/index.html";
    }
}
