package webserver.myframework.handler.request;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.handler.argument.ArgumentResolver;
import webserver.myframework.model.Model;

import java.lang.reflect.Method;

public class RequestHandlerImpl implements RequestHandler {
    private final ArgumentResolver argumentResolver;
    private final Object controller;
    private final Method method;

    public RequestHandlerImpl(Object controller, Method method, ArgumentResolver argumentResolver) {
        this.controller = controller;
        this.method = method;
        this.argumentResolver = argumentResolver;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, Model model)  {
        try {
            Object[] parameters = argumentResolver.resolve(method, httpRequest, httpResponse, model);
            method.invoke(controller, parameters);
        } catch (IllegalArgumentException argumentException) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
