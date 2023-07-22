package annotation;

import controller.Controller;
import db.Session;
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

    private static final Controller controller = new Controller();

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
                .bindTo(controller);
    }

    private RequestMappingHandler() {
    }

    public static HttpResponse invokeMethod(HttpRequest httpRequest, Session session) throws Throwable {
        String path = httpRequest.uri().getPath();
        HttpUtils.Method httpMethod = httpRequest.method();
        MethodHandle method = methodHandles.get(httpRequest.method()).get(path);
        if (method == null) {
            throw new IllegalAccessException("잘못된 메소드입니다.");
        }

        MethodType methodType = method.type();
        Class<?>[] paramTypes = methodType.parameterArray();

        if (paramTypes.length == 0) {
            return (HttpResponse) method.invoke();
        }
        return invokeMethodWithSessionAndMap(method, session, httpMethod, httpRequest);
    }

    private static HttpResponse invokeMethodWithSessionAndMap(MethodHandle method, Session session, HttpUtils.Method httpMethod, HttpRequest httpRequest) throws Throwable {
        MethodType methodType = method.type();
        Class<?>[] paramTypes = methodType.parameterArray();
        Object[] args = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = chooseArgument(paramTypes[i], session, httpMethod, httpRequest);
        }
        return (HttpResponse) method.invokeWithArguments(args);
    }

    private static Object chooseArgument(Class<?> paramType, Session session, HttpUtils.Method httpMethod, HttpRequest httpRequest) {
        if (paramType == Session.class) {
            return session;
        }
        if (paramType == Map.class) {
            return httpMethod.equals(HttpUtils.Method.GET) ?
                    StringUtils.parseParameters(httpRequest.uri().getQuery()) :
                    StringUtils.parseParameters(httpRequest.getBody());
        }
        throw new IllegalArgumentException("RequestMapping의 인자는 Map과 Session만 설정할 수 있습니다.");
    }
}
