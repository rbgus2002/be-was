package controller;

import http.HttpRequest;
import http.HttpResponse;

public interface HttpController {
    String process(HttpRequest request, HttpResponse response);
}
