package support.web.handler;

import support.annotation.Component;
import support.web.HttpEntity;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

@Component
public class VoidHandler implements ControllerMethodReturnValueHandler {


    @Override
    public boolean supportsReturnType(Class returnType) {
        return returnType == void.class;
    }

    @Override
    public HttpEntity handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) {
        return null;
    }
}
