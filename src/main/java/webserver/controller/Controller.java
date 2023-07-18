package webserver.controller;

import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;

public interface Controller {

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
