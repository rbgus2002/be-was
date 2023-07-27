package support.web.handler;

import support.annotation.Container;
import support.web.HttpEntity;
import support.web.exception.ServerErrorException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;

@Container
public class ControllerMethodReturnValueHandlerComposite {

    private final List<ControllerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();

    public ControllerMethodReturnValueHandlerComposite() {
        addHandler(new VoidHandler());
        addHandler(new ModelAndViewHandler());
        addHandler(new ResponseEntityHandler());
    }

    private ControllerMethodReturnValueHandlerComposite addHandler(ControllerMethodReturnValueHandler handler) {
        returnValueHandlers.add(handler);
        return this;
    }

    private ControllerMethodReturnValueHandler getAppropriateHandler(Class<?> returnType) throws ServerErrorException {
        return returnValueHandlers.stream()
                .filter(handler -> handler.supportsReturnType(returnType))
                .findAny()
                .orElseThrow(() -> new ServerErrorException("적절한 처리기가 없습니다."));
    }

    public HttpEntity handleReturnValue(Object returnValue, Class<?> returnType, HttpRequest request, HttpResponse response) throws Exception {
        ControllerMethodReturnValueHandler handler = getAppropriateHandler(returnType);
        return handler.handleReturnValue(returnValue, request, response);
    }

}
