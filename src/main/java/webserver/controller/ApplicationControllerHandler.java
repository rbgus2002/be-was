package webserver.controller;

import webserver.HttpMethod;
import webserver.annotation.RequestParameter;
import webserver.request.HttpRequestMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import static webserver.controller.ApplicationMethod.apiRouteToClassMap;
import static webserver.controller.ApplicationMethod.apiRouteToMethodMap;
import static webserver.handler.HttpBodyParser.parseBodyByContentType;

public class ApplicationControllerHandler {

    public static Object executeMethod(HttpRequestMessage httpRequestMessage) throws ReflectiveOperationException {
        ApiRoute requestApiRoute = new ApiRoute(httpRequestMessage.getPath(), httpRequestMessage.getMethod());

        if (!apiRouteToClassMap.containsKey(requestApiRoute)) {
            throw new IllegalArgumentException("존재하지 않은 클래스입니다.");
        }

        Class<?> targetClass = apiRouteToClassMap.get(requestApiRoute);
        Method targetMethod = apiRouteToMethodMap.get(requestApiRoute);

        if (requestApiRoute.getMethod().equals(HttpMethod.GET)) {
            return executeGet(httpRequestMessage, targetClass, targetMethod);
        }
        if (requestApiRoute.getMethod().equals(HttpMethod.POST)) {
            return executePost(httpRequestMessage, targetClass, targetMethod);
        }
        throw new IllegalArgumentException("수행할 HTTP 메서드가 존재하지 않습니다.");
    }

    private static Object executeGet(HttpRequestMessage httpRequestMessage, Class<?> targetClass, Method targetMethod) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        return targetMethod.invoke(targetClass.getDeclaredConstructor().newInstance(), getArguments(targetMethod, httpRequestMessage.getParameters()));
    }

    private static Object executePost(HttpRequestMessage httpRequestMessage, Class<?> targetClass, Method targetMethod) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Map<String, String> data = parseBodyByContentType(httpRequestMessage.getHeader("Content-Type"), httpRequestMessage.getBody());
        return targetMethod.invoke(targetClass.getDeclaredConstructor().newInstance(), getArguments(targetMethod, data));
    }

    private static Object[] getArguments(Method targetMethod, Map<String, String> requestParameters) {
        if (requestParameters.size() != targetMethod.getParameterCount()) {
            throw new IllegalArgumentException("요청된 파라미터의 갯수가 다릅니다.");
        }

        Object[] arguments = new Object[targetMethod.getParameterCount()];
        Parameter[] targetParameters = targetMethod.getParameters();
        for (int i = 0; i < targetMethod.getParameterCount(); i++) {
            RequestParameter targetParameter = targetParameters[i].getAnnotation(RequestParameter.class);
            arguments[i] = requestParameters.get(targetParameter.value());
        }
        return arguments;
    }
}
