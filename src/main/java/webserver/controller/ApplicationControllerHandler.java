package webserver.controller;

import webserver.annotation.RequestParameter;
import webserver.exception.BadRequestException;
import webserver.request.HttpRequestMessage;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import static webserver.controller.ApplicationMethod.apiRouteToClassMap;
import static webserver.controller.ApplicationMethod.apiRouteToMethodMap;

public class ApplicationControllerHandler {

    public static void executeMethod(HttpRequestMessage httpRequestMessage) throws BadRequestException, ReflectiveOperationException {
        ApiRoute requestApiRoute = new ApiRoute(httpRequestMessage.getPath(), httpRequestMessage.getMethod());

        if (!apiRouteToClassMap.containsKey(requestApiRoute)) {
            throw new BadRequestException("존재하지 않은 요청입니다.");
        }

        Class<?> targetClass = apiRouteToClassMap.get(requestApiRoute);
        Method targetMethod = apiRouteToMethodMap.get(requestApiRoute);

        targetMethod.invoke(targetClass.getDeclaredConstructor().newInstance(), getArguments(targetMethod, httpRequestMessage.getParameters()));
    }

    private static Object[] getArguments(Method targetMethod, Map<String, String> requestParameters) throws BadRequestException {
        if (requestParameters.size() != targetMethod.getParameterCount()) {
            throw new BadRequestException("요청된 파라미터의 갯수가 다릅니다.");
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
