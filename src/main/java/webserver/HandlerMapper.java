package webserver;

import controller.Controller;
import controller.annotaion.GetMapping;
import controller.annotaion.PostMapping;
import webserver.http.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    private static Map<String, Method> handlers = new HashMap<>();

    static {
        Method[] methods = Controller.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                handlers.put(method.getAnnotation(GetMapping.class).path(), method);
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                handlers.put(method.getAnnotation(PostMapping.class).path(), method);
            }
        }
    }

    public static Method getHandler(HttpRequest request) {
        if (!handlers.isEmpty()) {
            for (String url : handlers.keySet()) {
                if (request.getRequestPath().equals(url)) {
                    return handlers.get(url);
                }
            }
        }

        return null;
    }
}
