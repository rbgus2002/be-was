package controller.annotation;

import controller.Controller;
import mapper.ResponseMapper;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpMethod;

import java.lang.reflect.Method;

public class GetHandler implements Handler {

    @Override
    public boolean matchHttpMethod(HttpMethod httpMethod) {
        return httpMethod == HttpMethod.GET;
    }

    @Override
    public HttpResponse runController(Controller controller, HttpRequest httpRequest) throws Exception {
        for (Method method : controller.getClass().getDeclaredMethods()) {

            GetMapping isPresent = method.getAnnotation(GetMapping.class);
            if (isPresent == null) {
                continue;
            }

            if (isStaticRequest(httpRequest)) {
                return (HttpResponse) method.invoke(controller, httpRequest.getUri(), httpRequest.getMimeType());
            }

            if (match(isPresent, httpRequest.getUri())) {
                return (HttpResponse) method.invoke(controller);
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
}
