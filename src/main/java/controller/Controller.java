package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {


    String execute(HttpRequest request, HttpResponse response);
}
