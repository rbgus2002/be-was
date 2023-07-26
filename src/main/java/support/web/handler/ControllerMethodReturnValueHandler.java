package support.web.handler;

import support.web.ResponseEntity;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface ControllerMethodReturnValueHandler {

    boolean supportsReturnType(Class<?> returnType);

    ResponseEntity handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) throws Exception;

}
