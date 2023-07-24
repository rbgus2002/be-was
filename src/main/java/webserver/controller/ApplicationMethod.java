package webserver.controller;

import org.reflections.Reflections;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

public class ApplicationMethod {
    public static Map<ApiRoute, Class<?>> apiRouteToClassMap;
    public static Map<ApiRoute, Method> apiRouteToMethodMap;

    public static void initialize() {
        Set<Class<?>> classes = new Reflections("application").getTypesAnnotatedWith(Controller.class);
        Map<ApiRoute, Class<?>> classMap = new HashMap<>();
        Map<ApiRoute, Method> methodMap = new HashMap<>();

        for (Class<?> clazz : classes) {
            Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> {
                        RequestMapping targetAnnotation = method.getDeclaredAnnotation(RequestMapping.class);
                        classMap.put(new ApiRoute(targetAnnotation.path(), targetAnnotation.method()), clazz);
                        methodMap.put(new ApiRoute(targetAnnotation.path(), targetAnnotation.method()), method);
                    });
        }
        apiRouteToClassMap = Collections.unmodifiableMap(classMap);
        apiRouteToMethodMap = Collections.unmodifiableMap(methodMap);
    }
}
