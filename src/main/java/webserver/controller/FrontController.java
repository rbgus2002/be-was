package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FrontController {
    private final HttpRequest httpRequest;
    private final DataOutputStream dos;

    public FrontController(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    public void doDispatch() throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        if (!HandlerMapper.hasHandler(httpRequest)) {
            HttpResponse httpResponse = HttpResponse.createStatic(httpRequest.getRequestUri());
            httpResponse.responseStatic(dos);
            return;
        }

        Method method = HandlerMapper.getHandlerMethod(httpRequest);
        HandlerAdapter.runHandlerMethod(method, httpRequest);
        HttpResponse httpResponse = HttpResponse.createRedirect();
        httpResponse.responseDynamic(dos);
    }
}
