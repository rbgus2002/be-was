package webserver.myframework.handler.request;

import webserver.http.HttpRequest;

import java.lang.reflect.Method;

public class RequestHandlerImpl implements RequestHandler {
    private final Object controller;
    private final Method method;

    public RequestHandlerImpl(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public String handle(HttpRequest request) throws ReflectiveOperationException {
        Object path = method.invoke(controller, request);
        if(!(path instanceof String)) {
            //TODO: 어떻게 바꿀지 고민해야됨
            throw new RuntimeException();
        }
        return ((String) path);
    }
}
