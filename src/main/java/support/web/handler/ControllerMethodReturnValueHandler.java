package support.web.handler;

import support.web.HttpEntity;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface ControllerMethodReturnValueHandler {

    boolean supportsReturnType(Class<?> returnType);

    HttpEntity handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) throws Exception;

}
