package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class DefaultController extends HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        return request.getUrl();
    }
}
