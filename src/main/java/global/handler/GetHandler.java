package global.handler;

import annotations.GetMapping;
import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;
import global.request.RequestLine;

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

    public String startController(RequestLine requestLine, Controller controller) throws Exception {
        for (Method method : Controller.class.getDeclaredMethods()) {
            if (isGetMapping(method, requestLine.getUri())) {
                return (String) method.invoke(controller, requestLine.getQueryParams());
            }
        }

        Set<String> GetMappingsSet = getDeclaredGetMappings();
        if (GetMappingsSet.contains(requestLine.getUri())) {
            return requestLine.getUri();
        }
        throw new BadRequestException();
    }

    private Set<String> getDeclaredGetMappings() {
        return Arrays.stream(Controller.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GetMapping.class))
                .map(method -> method.getAnnotation(GetMapping.class).path())
                .collect(Collectors.toSet());
    }

    private boolean isGetMapping(Method method, String uri) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            String path = method.getAnnotation(GetMapping.class).path();
            return path.equals(uri);
        }
        return false;
    }
}