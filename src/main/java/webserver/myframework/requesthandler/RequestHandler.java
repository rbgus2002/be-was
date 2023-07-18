package webserver.myframework.requesthandler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


public interface RequestHandler {
    void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
