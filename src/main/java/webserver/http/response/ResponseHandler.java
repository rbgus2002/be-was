package webserver.http.response;

import webserver.controller.Controller;
import webserver.controller.HandlerMapper;
import webserver.http.request.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResponseHandler {
    private ResponseHandler() {
    }

    public static void doResponse(OutputStream out, HttpRequest request) throws InvocationTargetException, IllegalAccessException, IOException {
        Method handler = HandlerMapper.getHandler(request);
        HttpResponse response = (HttpResponse) handler.invoke(Controller.getInstance(), request);
        response.write(out);
    }
}
