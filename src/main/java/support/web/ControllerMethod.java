package support.web;

import support.instance.DefaultInstanceManager;
import support.web.exception.HttpException;
import support.web.exception.ServerErrorException;

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

    public Object invoke(Object... args) throws IllegalAccessException, HttpException {
        Object instance = DefaultInstanceManager.getInstanceMagager().getInstance(controllerClass);
        try {
            return method.invoke(instance, args);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            throw throwable instanceof HttpException ? (HttpException) throwable : new ServerErrorException();
        }
    }

}
