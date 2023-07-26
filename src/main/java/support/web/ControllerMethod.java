package support.web;

import java.util.Map;

public class ControllerMethod {

    private final Class<?> controller;
    private final Map<String, ControllerMethodStruct> methodList;

    public ControllerMethod(Class<?> controller, Map<String, ControllerMethodStruct> methodList) {
        this.controller = controller;
        this.methodList = methodList;
    }

    public Class<?> getControllerClass() {
        return controller;
    }

    public ControllerMethodStruct find(String path) {
        return methodList.get(path);
    }

}
