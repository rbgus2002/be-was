package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Container;
import support.web.*;
import support.web.exception.*;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.strategy.NotFound;

import java.util.Arrays;

@Container
public class HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    public void doService(HttpRequest request, HttpResponse response) {
        String path = request.getRequestPath();

        try {
            try {
                HttpEntity httpEntity = ControllerResolver.invoke(path, request, response);
                if (httpEntity == null) {
                    httpEntity = new HttpEntity(HttpStatus.OK);
                }
                response.setStatus(httpEntity.getStatus());
                if (httpEntity.getHeader() != null) {
                    response.appendHeader(httpEntity.getHeader());
                }
            } catch (NotSupportedException e) {
                if (request.getRequestMethod() == HttpMethod.GET) {
                    ModelAndView modelAndView = new ModelAndView();
                    modelAndView.setViewName(path);
                    callViewResolver(request, response, modelAndView);
                } else {
                    buildErrorResponse(request, response);
                }
            }
        } catch (FoundException e) {
            response.setStatus(e.getHttpStatus());
            response.appendHeader("Location", e.getRedirectionUrl());
        } catch (HttpException e) {
            logger.debug(e.getMessage());
            response.setStatus(e.getHttpStatus());
        } catch (Exception e) {
            logger.debug(e.getClass().getName());
            logger.debug(Arrays.toString(e.getStackTrace()));
            logger.debug(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private void callViewResolver(HttpRequest request, HttpResponse response, ModelAndView modelAndView) throws ServerErrorException {
        try {
            ViewResolver.buildView(request, response, modelAndView);
            response.setStatus(HttpStatus.OK);
        } catch (NotFoundException e) {
            buildErrorResponse(request, response);
        }
    }

    private void buildErrorResponse(HttpRequest request, HttpResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND);
        response.buildHeader(new NotFound());
        ViewResolver.buildErrorView(request, response);
    }

}
