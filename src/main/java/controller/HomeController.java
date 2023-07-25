package controller;

import annotation.Controller;
import annotation.RequestMapping;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


@Controller
public class HomeController {
    @RequestMapping(path = "/")
    public void execute(HttpRequest request, HttpResponse response) {
        response.setToUrl("/index.html");
    }
}
