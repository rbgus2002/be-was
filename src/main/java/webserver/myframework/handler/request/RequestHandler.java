package webserver.myframework.handler.request;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


public interface RequestHandler {
    String handle(HttpRequest httpRequest, HttpResponse httpResponse) throws ReflectiveOperationException;
}
