package global.handler;

import annotations.GetMapping;
import annotations.PostMapping;
import controller.Controller;
import exception.BadRequestException;
import global.constant.HttpMethod;
import global.request.RequestBody;
import global.request.RequestLine;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PostHandler implements Handler {
    private final HttpMethod httpMethod = HttpMethod.POST;
    private final RequestBody requestBody;

    public PostHandler(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public boolean matchHttpMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public byte[] startController(RequestLine requestLine, Controller controller) throws Exception {
        for (Method method : Controller.class.getDeclaredMethods()) {
            if (isPostMapping(method, requestLine.getUri())) {
                return (byte[]) method.invoke(controller, requestBody);
            }
        }

        Set<String> PostMappingsSet = getDeclaredPostMappings();
        if (PostMappingsSet.contains(requestLine.getUri())) {
            return requestLine.getUri().getBytes();
        }
        throw new BadRequestException();
    }


    private Set<String> getDeclaredPostMappings() {
        return Arrays.stream(Controller.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PostMapping.class))
                .map(method -> method.getAnnotation(PostMapping.class).path())
                .collect(Collectors.toSet());
    }

    private boolean isPostMapping(Method method, String uri) {
        if (method.isAnnotationPresent(PostMapping.class)) {
            String path = method.getAnnotation(PostMapping.class).path();
            return path.equals(uri);
        }
        return false;
    }
}