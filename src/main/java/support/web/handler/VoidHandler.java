package support.web.handler;

import support.web.ResponseEntity;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

public class VoidHandler implements ControllerMethodReturnValueHandler {


    @Override
    public boolean supportsReturnType(Class returnType) {
        return false;
    }

    @Override
    public ResponseEntity handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
