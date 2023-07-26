package support.web.handler;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface ControllerMethodReturnValueHandler {

    boolean supportsReturnType(Class<?> returnType);

    void handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) throws Exception;

}
