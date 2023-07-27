package support.web.handler;

import support.instance.DefaultInstanceManager;
import support.web.HttpEntity;
import support.web.ResponseEntity;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ResponseEntityHandler implements ControllerMethodReturnValueHandler {


    private ControllerMethodReturnValueHandlerComposite handlers;

    @Override
    public boolean supportsReturnType(Class<?> returnType) {
        return returnType == ResponseEntity.class;
    }

    public ControllerMethodReturnValueHandlerComposite getHandlers() {
        if (handlers == null) {
            handlers = DefaultInstanceManager.getInstanceMagager().getInstance(ControllerMethodReturnValueHandlerComposite.class);
        }
        return handlers;
    }

    @Override
    public HttpEntity handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) throws Exception {
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) returnValue;
        HttpEntity objectHttpEntity = getHandlers().handleReturnValue(responseEntity.getReturnValue(), responseEntity.getReturnType(), request, response);
        return objectHttpEntity != null ? objectHttpEntity : responseEntity.getHttpEntity();
    }

}
