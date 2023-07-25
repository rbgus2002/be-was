package webserver.myframework.handler.request;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.model.Model;


public interface RequestHandler {
    void handle(HttpRequest httpRequest, HttpResponse httpResponse, Model model);
}
