package controller;

import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

public interface Controller {
    void execute(HttpRequest request, HttpResponse response);
}
