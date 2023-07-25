package webserver;

import controller.Controller;
import controller.annotaion.GetMapping;
import controller.annotaion.PostMapping;
import webserver.http.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    private static Map<String, Method> handlerMappings = new HashMap<>();

    static {
        Method[] methods = Controller.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                handlerMappings.put(method.getAnnotation(GetMapping.class).path(), method);
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                handlerMappings.put(method.getAnnotation(PostMapping.class).path(), method);
            }
        }
    }

    public static Method getHandler(HttpRequest request) {
        if (!handlerMappings.isEmpty()) {
            for (String url : handlerMappings.keySet()) {
                if (request.getRequestPath().equals(url)) {
                    return handlerMappings.get(url);
                }
            }
        }

        return null;
    }
}
