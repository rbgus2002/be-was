package webserver.controller;

import webserver.http.request.HttpRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerAdapter {
    public static void runHandlerMethod(Method method, HttpRequest httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = method.getDeclaringClass();
        Constructor<?> constructor = clazz.getConstructor();
        method.invoke(constructor.newInstance(), httpRequest);
    }
}
