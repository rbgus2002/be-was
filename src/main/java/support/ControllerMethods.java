package support;

import java.lang.reflect.Method;
import java.util.Map;

public class ControllerMethods {

    private final Class<?> controller;
    private final Map<String, Method> methodList;

    public ControllerMethods(Class<?> controller, Map<String, Method> methodList) {
        this.controller = controller;
        this.methodList = methodList;
    }

    public Class<?> getControllerClass() {
        return controller;
    }

    public Method find(String path) {
        return methodList.get(path);
    }

}
