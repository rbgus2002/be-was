package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


public interface Controller {
    String execute(HttpRequest request, HttpResponse response);
}
    