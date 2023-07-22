package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

public interface Controller {
    void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
