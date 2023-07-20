package controller;

import http.HttpRequest;
import http.HttpResponse;

public class BasicController extends Controller {

    @Override
    public HttpResponse.ResponseBuilder doGet(String uri) {
        return null;
    }

    @Override
    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        return null;
    }
}
