package webserver.controller;

import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;

import java.io.UnsupportedEncodingException;

public interface Controller {

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws UnsupportedEncodingException;
}
