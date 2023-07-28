package support.web;

import support.instance.DefaultInstanceManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ControllerMethod {

    private final Class<?> controllerClass;
    private final String controllerName;
    private final Method method;

    public ControllerMethod(Class<?> controllerClass, String controllerName, Method method) {
        this.controllerClass = controllerClass;
        this.controllerName = controllerName;
        this.method = method;
    }

    public Parameter[] getParameters() {
        return method.getParameters();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public Object invoke(Object... args) throws IllegalAccessException, InvocationTargetException {
        Object instance = DefaultInstanceManager.getInstanceManager().getInstance(controllerName, controllerClass);
        return method.invoke(instance, args);
    }

}
