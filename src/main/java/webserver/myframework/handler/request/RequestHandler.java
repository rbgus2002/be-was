package webserver.myframework.handler.request;

import webserver.http.HttpRequest;


public interface RequestHandler {
    String handle(HttpRequest request) throws ReflectiveOperationException;
}
