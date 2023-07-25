package webserver;

import application.controller.UserController;
import application.controller.WebController;
import support.annotation.RequestMapping;
import exception.InvalidPathException;
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
        return controllers.get(request.getRootPath());
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
        throw new InvalidPathException();
    }
}
