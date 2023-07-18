package annotation;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import util.HttpUtils;
import util.StringUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandler {

    private static final Map<HttpUtils.Method, Map<String, MethodHandle>> methodHandles = new HashMap<>();

    static {
        methodHandles.put(HttpUtils.Method.GET, new HashMap<>());
        methodHandles.put(HttpUtils.Method.POST, new HashMap<>());

        Method[] methods = Controller.class.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) continue;
            processRequestMappingAnnotation(method);
        }
    }

    private static void processRequestMappingAnnotation(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String path = annotation.path();
        HttpUtils.Method httpMethod = annotation.method();
        try {
            MethodHandle methodHandle = createMethodHandle(method);
            methodHandles.get(httpMethod).put(path, methodHandle);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static MethodHandle createMethodHandle(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType;
        if (method.getParameterCount() > 0) {
            methodType = MethodType.methodType(HttpResponse.class, method.getParameterTypes());
        } else {
            methodType = MethodType.methodType(HttpResponse.class);
        }
        return MethodHandles.lookup()
                .findVirtual(Controller.class, method.getName(), methodType)
                .bindTo(Controller.getInstance());
    }

    private RequestMappingHandler() {
    }

    public static HttpResponse invokeMethod(HttpRequest httpRequest) throws Throwable {
        String path = httpRequest.uri().getPath();
        HttpUtils.Method httpMethod = httpRequest.method();
        MethodHandle method = methodHandles.get(httpRequest.method()).get(path);
        if (method == null) {
            throw new IllegalAccessException("잘못된 메소드입니다.");
        }
        if (httpMethod.equals(HttpUtils.Method.GET)) {
            return invokeGet(method, httpRequest);
        }
        if (httpMethod.equals(HttpUtils.Method.POST)) {
            return invokePost(method, httpRequest);
        }
        throw new IllegalArgumentException("잘못된 HTTP 요청입니다.");
    }

    private static HttpResponse invokeGet(MethodHandle methodHandle, HttpRequest httpRequest) throws Throwable {
        if (isValidInvoke(methodHandle)) {
            return (HttpResponse) methodHandle.invoke();
        }
        return (HttpResponse) methodHandle.invoke(StringUtils.parseParameters(httpRequest.uri().getQuery()));
    }

    private static HttpResponse invokePost(MethodHandle methodHandle, HttpRequest httpRequest) throws Throwable {
        if (isValidInvoke(methodHandle)) {
            return (HttpResponse) methodHandle.invoke();
        }
        return (HttpResponse) methodHandle.invoke(StringUtils.parseParameters(httpRequest.getBody()));
    }

    private static boolean isValidInvoke(MethodHandle methodHandle) {
        MethodType methodType = methodHandle.type();
        if (methodType.parameterCount() == 0) {
            return true;
        }
        if (methodType.parameterCount() > 1 || !(methodType.parameterType(0) == Map.class)) {
            throw new IllegalArgumentException("하나의 Map을 인자로 받을 수 있습니다.");
        }
        return false;
    }
}
