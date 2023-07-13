package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;


public class HomeController implements Controller {


    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        return "/index.html";
    }
}
