package webserver;

import application.controller.StaticController;
import application.controller.UserController;
import application.controller.WebController;
import support.annotation.RequestMapping;
import exception.InvalidPathException;
import webserver.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {

    private final Map<String, WebController> controllers = new HashMap<>();
    private final WebController staticController = new StaticController();

    public ControllerMapper() {
        controllers.put("/user", new UserController());
    }

    private WebController getController(HttpRequest httpRequest) {
        WebController controller = controllers.get(httpRequest.getRootPath());
        if(controller == null) {
            return staticController;
        }
        return controller;
    }

    public Method getMethod(HttpRequest httpRequest) {
        WebController controller = getController(httpRequest);
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation.value().equals(httpRequest.getFullPath()) && annotation.method().equals(httpRequest.getHttpMethod())) {
                    return method;
                }
            }
        }
        throw new InvalidPathException();
    }
}
