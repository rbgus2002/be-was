package webserver;

import controller.Controller;
import controller.annotaion.GetMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static Map<String, Method> handlerMappings = new HashMap<>();

    static {
        Method[] methods = Controller.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                handlerMappings.put(method.getAnnotation(GetMapping.class).path(), method);
            }
        }
    }

    public static Map<String, Method> getHandlerMappings() {
        return handlerMappings;
    }
}
