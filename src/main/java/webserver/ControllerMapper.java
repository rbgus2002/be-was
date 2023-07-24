package webserver;

import application.controller.UserController;
import application.controller.WebController;
import support.annotation.RequestMapping;
import exception.InvalidPathException;
import webserver.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static webserver.Constants.PathConstants.*;

public class ControllerMapper {

    private final Map<String, WebController> controllers = new HashMap<>();

    public ControllerMapper() {
        controllers.put("/user", new UserController());
    }

    private WebController getController(HttpRequest httpRequest) {
        WebController controller = controllers.get(httpRequest.getPathSegment(ROOT_PATH));
        if(controller == null) throw new InvalidPathException();
        return controller;
    }

    public Method getMethod(HttpRequest httpRequest) {
        WebController controller = getController(httpRequest);
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation.value().equals(httpRequest.getPathSegment(SUB_FIRST_PATH)) && annotation.method().equals(httpRequest.getHttpMethod())) {
                    return method;
                }
            }
        }
        throw new InvalidPathException();
    }
}
