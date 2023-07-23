package controller;

import http.HttpResponse;

import java.util.Map;

public interface HttpController {
    String process(Map<String, String> requestParams, HttpResponse response);
}
