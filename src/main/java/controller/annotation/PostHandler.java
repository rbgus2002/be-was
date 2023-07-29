package controller.annotation;

import controller.Controller;
import mapper.ResponseMapper;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpMethod;

import java.lang.reflect.Method;
import java.util.Map;

public class PostHandler implements Handler {
    private final HttpRequest httpRequest;

    public PostHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public boolean matchHttpMethod(HttpMethod method) {
        return method == HttpMethod.POST;
    }

    @Override
    public HttpResponse runController(Controller controller, HttpRequest httpRequest) throws Exception {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            PostMapping isPresent = method.getAnnotation(PostMapping.class);
            if (isPresent == null) {
                continue;
            }

            if (match(isPresent, httpRequest.getUri())) {
                Map<String, String> bodyMap = httpRequest.getBodyMap();
                return (HttpResponse) method.invoke(controller, bodyMap);
            }
        }
        return ResponseMapper.createNotFoundResponse();
    }

    private boolean match(PostMapping postMapping, String uri) {
        String path = postMapping.path();
        return path.equals(uri);
    }
}
