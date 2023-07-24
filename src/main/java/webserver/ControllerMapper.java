package webserver;

import application.controller.UserController;
import application.controller.WebController;
import support.annotation.RequestMapping;
import webserver.exception.InvalidPathParameterException;
import webserver.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static webserver.Constants.PathConstants.*;

public class ControllerMapper {

    private final Map<String, WebController> controllers = new HashMap<>();

    public ControllerMapper() {
        controllers.put("/user", new UserController());
    }

    private Optional<WebController> getController(HttpRequest httpRequest) {
        WebController controller = controllers.get(httpRequest.getPathSegment(ROOT_PATH));
        if(controller == null) return Optional.empty();
        return Optional.of(controller);
    }

    public Optional<Method> getMethod(HttpRequest httpRequest) {
        Optional<WebController> controllerOpt = getController(httpRequest);
        if(controllerOpt.isEmpty()) throw new InvalidPathParameterException();

        WebController controller = controllerOpt.get();
        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation.value().equals(httpRequest.getPathSegment(SUB_FIRST_PATH)) && annotation.method().equals(httpRequest.getHttpMethod())) {
                    return Optional.of(method);
                }
            }
        }
        return Optional.empty();
    }
}
