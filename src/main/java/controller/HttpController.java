package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface HttpController {
    String process(HttpRequest request, HttpResponse response);

}
