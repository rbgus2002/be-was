package webserver.controller;

import webserver.ModelAndView;
import webserver.http.request.HttpRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerAdapter {
    public static ModelAndView runHandlerMethod(Method method, HttpRequest httpRequest) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = method.getDeclaringClass();
        Constructor<?> constructor = clazz.getConstructor();
        return (ModelAndView) method.invoke(constructor.newInstance(), httpRequest);
    }
}
