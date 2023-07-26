package support.web.handler;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

public class VoidHandler implements ControllerMethodReturnValueHandler {


    @Override
    public boolean supportsReturnType(Class returnType) {
        return false;
    }

    @Override
    public void handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) {
        response.setStatus(HttpStatus.OK);
    }
}
