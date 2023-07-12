package annotation;

import controller.Controller;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandler {

    private static final Map<String, Map<String, MethodHandle>> map = new HashMap<>();

    static {
        map.put(HttpMethod.GET.name(), new HashMap<>());
        map.put(HttpMethod.POST.name(), new HashMap<>());

        Method[] methods = Controller.class.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) continue;
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            String path = annotation.path();
            HttpMethod httpMethod = annotation.method();
            try {
                MethodType methodType;
                if (method.getParameterCount() > 0) {
                    methodType = MethodType.methodType(HttpResponse.class, method.getParameterTypes());
                } else {
                    methodType = MethodType.methodType(HttpResponse.class);
                }
                MethodHandle methodHandle = MethodHandles.lookup()
                        .findVirtual(Controller.class, method.getName(), methodType)
                        .bindTo(Controller.getInstance());
                map.get(httpMethod.name()).put(path, methodHandle);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private RequestMappingHandler() {
    }

    public static HttpResponse invokeMethod(HttpRequest httpRequest) throws Throwable {
        String path = httpRequest.uri().getPath();
        HttpMethod httpMethod = httpRequest.method();
        MethodHandle method = map.get(HttpMethod.GET.name()).get(path);
        if (method == null) {
            throw new IllegalAccessException("잘못된 메소드입니다.");
        }
        if (httpMethod.equals(HttpMethod.GET)) {
            MethodType methodType = method.type();
            if (methodType.parameterCount() == 0) {
                return (HttpResponse) method.invoke();
            }
            if (methodType.parameterCount() > 1 || !(methodType.parameterType(0) == Map.class)) {
                throw new IllegalArgumentException("GET method는 하나의 Map을 인자로 받을 수 있습니다.");
            }
        }
        return new HttpResponse("");
    }
}
