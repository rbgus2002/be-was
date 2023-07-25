package webserver.controller;

import webserver.annotaion.RequestMapping;
import webserver.http.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerMapper {
    private static Method[] methods;
    private static Method getStaticResource;

    static {
        try {
            methods = Controller.class.getMethods();
            getStaticResource = Controller.class.getMethod("getStaticResource", HttpRequest.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getHandler(HttpRequest request) {
        List<Method> filteredMethods = Arrays.stream(methods)
                .filter(method -> isMatch(method, request)).collect(Collectors.toList());
        if (filteredMethods.isEmpty()) {
            return getStaticResource;
        }
        return filteredMethods.get(0);
    }

    private static boolean isMatch(Method method, HttpRequest request) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            return request.isMatchHandler(annotation.method(), annotation.path());
        }
        return false;
    }
}
