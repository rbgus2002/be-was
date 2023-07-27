package support.web;

import support.instance.DefaultInstanceManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ControllerMethod {

    private final Class<?> controllerClass;
    private final Method method;

    public ControllerMethod(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Parameter[] getParameters() {
        return method.getParameters();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public Object invoke(Object... args) throws IllegalAccessException, InvocationTargetException {
        Object instance = DefaultInstanceManager.getInstanceManager().getInstance(controllerClass);
        return method.invoke(instance, args);
    }

}
