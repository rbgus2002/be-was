package webserver.myframework.requesthandler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.lang.reflect.Method;

public class RequestHandlerImpl implements RequestHandler {
    private final Object controller;
    private final Method method;

    public RequestHandlerImpl(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse)  {
        try {
            method.invoke(controller, httpRequest, httpResponse);
        } catch (Exception e) {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
