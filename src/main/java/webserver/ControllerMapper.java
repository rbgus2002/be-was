package webserver;

import application.controller.UserController;
import application.controller.WebController;
import exception.notFound.InvalidControllerPathException;
import exception.notFound.InvalidMethodPathException;
import support.annotation.RequestMapping;
import webserver.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {

    private static final Map<String, WebController> controllers = new HashMap<>();

    static {
        controllers.put("/user", new UserController());
    }

    public WebController getController(HttpRequest request) {
        WebController controller = controllers.get(request.getRootPath());
        if(controller == null) throw new InvalidControllerPathException(request.getRootPath());
        return controller;
    }

    public Method getMethod(WebController controller, HttpRequest request) {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation.value().equals(request.getFullPath()) && annotation.method().equals(request.getHttpMethod())) {
                    return method;
                }
            }
        }
        throw new InvalidMethodPathException(request.getRootPath(), request.getFullPath());
    }
}
