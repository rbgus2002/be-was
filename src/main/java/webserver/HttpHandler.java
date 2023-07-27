package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Container;
import support.web.*;
import support.web.exception.NotFoundException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.util.Arrays;

@Container
public class HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private ControllerResolver controllerResolver;

    public ControllerResolver getControllerResolver() {
        if(controllerResolver == null) {
            controllerResolver = new ControllerResolver();
        }
        return controllerResolver;
    }

    public void doService(HttpRequest request, HttpResponse response) {
        String path = request.getRequestPath();

        try {
            HttpEntity httpEntity = getControllerResolver().invoke(path, request, response);
            if (httpEntity == null) {
                httpEntity = doGetOrOther(request, response, path);
                assert httpEntity != null;
            }

            response.setStatus(httpEntity.getStatus());
            if (httpEntity.getHeader() != null) {
                response.appendHeader(httpEntity.getHeader());
            }
        } catch (Exception e) {
            logger.debug(e.getClass().getName());
            logger.debug(Arrays.toString(e.getStackTrace()));
            logger.debug(e.getMessage());
            response.setStatus(HttpStatus.SERVER_ERROR);
        }
    }

    private HttpEntity doGetOrOther(HttpRequest request, HttpResponse response, String path) throws IOException {
        if (request.getRequestMethod() == HttpMethod.GET) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(path);
            return callViewResolver(request, response, modelAndView);
        }
        return buildErrorResponse(request, response);
    }

    private HttpEntity callViewResolver(HttpRequest request, HttpResponse response, ModelAndView modelAndView) throws IOException {
        try {
            return ViewResolver.buildView(request, response, modelAndView);
        } catch (NotFoundException e) {
            return buildErrorResponse(request, response);
        }
    }

    private HttpEntity buildErrorResponse(HttpRequest request, HttpResponse response) {
        ViewResolver.buildErrorView(request, response);
        return new HttpEntity(HttpStatus.NOT_FOUND);
    }

}
