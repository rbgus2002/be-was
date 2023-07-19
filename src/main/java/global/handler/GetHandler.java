package global.handler;

import annotations.MyGetMapping;
import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GetHandler implements Handler {
    private final HttpMethod httpMethod = HttpMethod.GET;

    public GetHandler() {
    }

    public boolean matchHttpMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public String startController(String uri, Controller controller) throws Exception {
        for (Method method : Controller.class.getDeclaredMethods()) {
            if (isGetMapping(method, uri)) {
                return (String) method.invoke(controller);
            }
        }

        Set<String> GetMappingsSet = getDeclaredGetMappings();
        if (GetMappingsSet.contains(uri)) {
            return uri;
        }
        throw new BadRequestException();
    }

    private Set<String> getDeclaredGetMappings() {
        return Arrays.stream(Controller.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MyGetMapping.class))
                .map(method -> method.getAnnotation(MyGetMapping.class).path())
                .collect(Collectors.toSet());
    }

    private boolean isGetMapping(Method method, String uri) {
        if (method.isAnnotationPresent(MyGetMapping.class)) {
            String path = method.getAnnotation(MyGetMapping.class).path();
            return path.equals(uri);
        }
        return false;
    }
}