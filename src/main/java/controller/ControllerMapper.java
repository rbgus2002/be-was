package controller;

import annotation.Controller;
import annotation.RequestMapping;
import http.request.HttpRequest;
import http.request.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerMapper {
    private static final Map<ValueAndMethod, Method> handlerMapper = new ConcurrentHashMap<>();

    static {
        try {
            List<Class<?>> handlers = getHandlers();
            for (Class<?> handler : handlers) {
                Method[] methods = handler.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        handlerMapper.put(new ValueAndMethod(requestMapping.value(), requestMapping.method()), method);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method getHandlerMethod(HttpRequest request) {
        return handlerMapper.get(new ValueAndMethod(request.getRequestUri(), request.getRequestMethod()));
    }

    public static boolean hasHandler(HttpRequest httpRequest) {
        String requestUri = httpRequest.getRequestUri();
        RequestMethod requestMethod = httpRequest.getRequestMethod();
        if (requestUri.contains("?")) {
            requestUri = requestUri.substring(0, requestUri.indexOf("?"));
        }
        return handlerMapper.get(new ValueAndMethod(requestUri, requestMethod)) != null;
    }

    private static List<Class<?>> getHandlers() throws IOException, ClassNotFoundException {
        return findAllControllerClasses("webserver.controller");
    }

    private static List<Class<?>> findAllControllerClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;

    }

    private static Collection<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        if (!directory.exists()) {
            return Collections.emptyList();
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class<?> clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                if (clazz.isAnnotationPresent(Controller.class) && !Modifier.isAbstract(clazz.getModifiers())) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
}
