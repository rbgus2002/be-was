package support.web.handler;

import support.web.ModelAndView;
import support.web.ResponseEntity;
import support.web.ViewResolver;
import support.web.exception.NotFoundException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

public class ModelAndViewHandler implements ControllerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(Class<?> returnType) {
        return returnType == ModelAndView.class;
    }

    @Override
    public ResponseEntity handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) throws Exception {
        try {
            ViewResolver.buildView(request, response, (ModelAndView) returnValue);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            ViewResolver.buildErrorView(request, response);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
