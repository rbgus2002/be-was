package webserver;

import application.controller.UserController;
import application.controller.WebController;
import support.annotation.RequestMapping;
import webserver.Constants.PathConstants;
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

    private WebController getController(HttpRequest httpRequest) {
        return controllers.get(httpRequest.getPathSegment(ROOT_PATH));
    }

    public Optional<Method> getMethod(HttpRequest httpRequest) {
        WebController controller = getController(httpRequest);
        for (Method method : controller.getClass().getMethods()) {
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
