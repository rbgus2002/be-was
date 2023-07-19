package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class HomeController extends HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        return "/index.html";
    }
}
