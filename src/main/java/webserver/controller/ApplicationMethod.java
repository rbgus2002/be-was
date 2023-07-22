package webserver.controller;

import org.reflections.Reflections;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationMethod {
    public static Map<ApiRoute, Class<?>> apiRouteToClassMap;
    public static Map<ApiRoute, Method> apiRouteToMethodMap;

    static {
        Set<Class<?>> classes = new Reflections("application").getTypesAnnotatedWith(Controller.class);
        Map<ApiRoute, Class<?>> classMap = new HashMap<>();
        Map<ApiRoute, Method> methodMap = new HashMap<>();

        for (Class<?> clazz : classes) {
            for (Method method : clazz.getDeclaredMethods()) {
                RequestMapping targetAnnotation = method.getDeclaredAnnotation(RequestMapping.class);
                classMap.put(new ApiRoute(targetAnnotation.path(), targetAnnotation.method()), clazz);
                methodMap.put(new ApiRoute(targetAnnotation.path(), targetAnnotation.method()), method);
            }
        }
        apiRouteToClassMap = Collections.unmodifiableMap(classMap);
        apiRouteToMethodMap = Collections.unmodifiableMap(methodMap);
    }
}
