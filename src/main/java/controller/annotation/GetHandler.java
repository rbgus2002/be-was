package controller.annotation;

import controller.Controller;
import mapper.ResponseMapper;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpMethod;

import java.lang.reflect.Method;

import static session.Authorization.NEEDED_AUTHORIZATION;

public class GetHandler implements Handler {

    @Override
    public boolean matchHttpMethod(HttpMethod httpMethod) {
        return httpMethod == HttpMethod.GET;
    }

    @Override
    public HttpResponse runController(Controller controller, HttpRequest httpRequest) throws Exception {
        for (Method method : controller.getClass().getDeclaredMethods()) {
            GetMapping annotatedMethod = method.getAnnotation(GetMapping.class);

            if (annotatedMethod == null) {
                continue;
            }

            if (match(annotatedMethod, httpRequest.getUri())) {
                return (HttpResponse) method.invoke(controller, httpRequest);
            }

            if (!needAuthorization(httpRequest) && isStaticRequest(httpRequest)) {
                return (HttpResponse) method.invoke(controller, httpRequest);
            }
        }
        return ResponseMapper.createNotFoundResponse();
    }

    private boolean isStaticRequest(HttpRequest httpRequest) {
        return httpRequest.getUri().contains(".");
    }

    private boolean match(GetMapping getMapping, String uri) {
        String path = getMapping.path();
        return path.equals(uri);
    }

    private boolean needAuthorization(HttpRequest httpRequest) {
        return NEEDED_AUTHORIZATION.contains(httpRequest.getUri());
    }
}
