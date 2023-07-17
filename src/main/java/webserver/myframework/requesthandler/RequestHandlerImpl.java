package webserver.myframework.requesthandler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.lang.reflect.Method;

public class RequestHandlerImpl implements RequestHandler {
    private final Object controller;
    private final Method method;

    public RequestHandlerImpl(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws ReflectiveOperationException {
        method.invoke(controller, httpRequest, httpResponse);
    }
}
