package support.web;

import java.lang.reflect.Method;

public class ControllerMethod {

    private final Class<?> controllerClass;
    private final Method method;

    public ControllerMethod(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }

}
