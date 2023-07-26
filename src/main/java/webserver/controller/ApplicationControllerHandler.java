package webserver.controller;

import webserver.HttpMethod;
import webserver.annotation.Cookies;
import webserver.annotation.HttpResponse;
import webserver.annotation.RequestParameter;
import webserver.request.HttpRequestMessage;
import webserver.response.HttpResponseMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static webserver.controller.ApplicationMethod.apiRouteToClassMap;
import static webserver.controller.ApplicationMethod.apiRouteToMethodMap;
import static webserver.handler.HttpBodyParser.parseBodyByContentType;

public class ApplicationControllerHandler {
    private HttpRequestMessage request;
    private HttpResponseMessage response;
    private ApiRoute apiRoute;
    private Class<?> targetClass;
    private Method targetMethod;

    private ApplicationControllerHandler(HttpRequestMessage request, HttpResponseMessage response) {
        this.request = request;
        this.response = response;
        initialize();
    }

    private void initialize() {
        apiRoute = new ApiRoute(request.getPath(), request.getMethod());
        this.targetClass = apiRouteToClassMap.get(apiRoute);
        this.targetMethod = apiRouteToMethodMap.get(apiRoute);
    }

    public static ApplicationControllerHandler of(HttpRequestMessage request, HttpResponseMessage response) {
        return new ApplicationControllerHandler(request, response);
    }

    public Object executeMethod() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        verifyExecuteMethod();
        if (request.getMethod().equals(HttpMethod.GET)) {
            return executeGet();
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            return executePost();
        }
        throw new IllegalArgumentException("메서드가 존재하지 않습니다.");
    }

    private void verifyExecuteMethod() {
        if (!apiRouteToClassMap.containsKey(apiRoute)) {
            throw new IllegalArgumentException("존재하지 않은 클래스입니다.");
        }
    }

    private Object executeGet() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        return targetMethod.invoke(targetClass.getDeclaredConstructor().newInstance(), getArguments(request.getParameters()));
    }

    private Object executePost() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Map<String, String> data = parseBodyByContentType(request.getHeader("Content-Type"), request.getBody());
        return targetMethod.invoke(targetClass.getDeclaredConstructor().newInstance(), getArguments(data));
    }

    private Object[] getArguments(Map<String, String> requestData) {
        Object[] arguments = new Object[targetMethod.getParameterCount()];
        Parameter[] targetParameters = targetMethod.getParameters();
        for (int i = 0; i < targetMethod.getParameterCount(); i++) {
            if (targetParameters[i].isAnnotationPresent(RequestParameter.class)) {
                RequestParameter targetParameter = targetParameters[i].getAnnotation(RequestParameter.class);
                arguments[i] = requestData.get(targetParameter.value());
            }
            if (targetParameters[i].isAnnotationPresent(HttpResponse.class)) {
                arguments[i] = response;
            }
            if (targetParameters[i].isAnnotationPresent(Cookies.class)) {
                // todo 파싱 로직 리팩토링 필요
                String data = request.getHeader("Cookie");
                Map<String, String> cookies = new HashMap<>();
                for (String tokens : data.split(";")) {
                    String[] keyValue = tokens.trim().split("=");

                    cookies.put(keyValue[0], keyValue[1]);
                }
                arguments[i] = cookies;
            }
        }
        return arguments;
    }
}
