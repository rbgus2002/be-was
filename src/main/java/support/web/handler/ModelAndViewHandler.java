package support.web.handler;

import support.web.ModelAndView;
import support.web.ViewResolver;
import support.web.exception.NotFoundException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.strategy.NotFound;

public class ModelAndViewHandler implements ControllerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(Class<?> returnType) {
        return returnType == ModelAndView.class;
    }

    @Override
    public void handleReturnValue(Object returnValue, HttpRequest request, HttpResponse response) throws Exception {
        try {
            ViewResolver.buildView(request, response, (ModelAndView) returnValue);
            response.setStatus(HttpStatus.OK);
        } catch (NotFoundException e) {
            ViewResolver.buildErrorView(request, response);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.buildHeader(new NotFound());
        }
    }

}
