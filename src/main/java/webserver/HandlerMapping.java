package webserver;

import annotation.RequestMapping;
import controller.UserController;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    public static final Map<HandlerKey, Method> mappings = new HashMap<>();

    static {
        Method[] methods = UserController.class.getDeclaredMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                String path = annotation.path();
                HttpMethod httpMethod = annotation.method();
                HandlerKey handlerKey = new HandlerKey(path, httpMethod);
                mappings.put(handlerKey, method);
            }
        }
    }

    public static Method getHandler(HttpRequest request) {
        String path = request.getPath();
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        HandlerKey handlerKey = new HandlerKey(path, httpMethod);

        if(mappings.containsKey(handlerKey)) {
            return mappings.get(handlerKey);
        }
        return null;
    }
}
