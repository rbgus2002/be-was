package webserver.controller;

import webserver.HttpMethod;
import webserver.annotation.RequestParameter;
import webserver.annotation.SetCookie;
import webserver.request.HttpRequestMessage;
import webserver.response.HttpResponseMessage;
import webserver.session.SessionStorage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.UUID;

import static webserver.WebServer.logger;
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
        verifyParameterCount(targetMethod, requestData);

        Object[] arguments = new Object[targetMethod.getParameterCount()];
        Parameter[] targetParameters = targetMethod.getParameters();
        for (int i = 0; i < targetMethod.getParameterCount(); i++) {
            if (targetParameters[i].isAnnotationPresent(RequestParameter.class)) {
                RequestParameter targetParameter = targetParameters[i].getAnnotation(RequestParameter.class);
                arguments[i] = requestData.get(targetParameter.value());

                if (hasCookieAnnotation(targetParameters[i])) {
                    setCookieConnection(requestData, targetParameter);
                }
            }
        }
        return arguments;
    }

    private void setCookieConnection(Map<String, String> requestData, RequestParameter targetParameter) {
        logger.debug("[SetCookie Annotation key = {}, value = {} ]", targetParameter.value(), requestData.get(targetParameter.value()));

        String clientId = requestData.get(targetParameter.value());
        String sessionId = UUID.randomUUID().toString();
        SessionStorage.setSession(sessionId, clientId);

        response.setHeader("Set-Cookie", "sid=" + sessionId + "; " + "path=/");
    }

    private void verifyParameterCount(Method targetMethod, Map<String, String> requestParameters) {
        if (requestParameters.size() != targetMethod.getParameterCount()) {
            throw new IllegalArgumentException("요청된 파라미터의 갯수가 다릅니다.");
        }
    }

    private boolean hasCookieAnnotation(Parameter targetParameters) {
        return targetParameters.isAnnotationPresent(SetCookie.class);
    }
}
