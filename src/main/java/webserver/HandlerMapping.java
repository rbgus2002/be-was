package webserver;

import annotation.GetMapping;
import controller.Controller;
import webserver.http.request.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    public static Map<String, Method> GETMap = new HashMap<>();
    static{
        Method[] methods = Controller.class.getMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(GetMapping.class)){
                GETMap.put(method.getAnnotation(GetMapping.class).value(), method);
            }
        }
    }

    private HandlerMapping() {
    }

    public static Method getMethodMapped(HttpRequest request){
        return GETMap.getOrDefault(request.getPath(), null);
    }
}
