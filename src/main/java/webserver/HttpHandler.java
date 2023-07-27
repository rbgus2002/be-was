package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Container;
import support.web.*;
import support.web.exception.HttpException;
import support.web.exception.NotFoundException;
import support.web.exception.ServerErrorException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.util.Arrays;

@Container
public class HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    public void doService(HttpRequest request, HttpResponse response) {
        String path = request.getRequestPath();

        try {
            HttpEntity httpEntity = ControllerResolver.invoke(path, request, response);
            if (httpEntity == null) {
                httpEntity = doGetOrOther(request, response, path);
                assert httpEntity != null;
            }

            response.setStatus(httpEntity.getStatus());
            if (httpEntity.getHeader() != null) {
                response.appendHeader(httpEntity.getHeader());
            }
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

    private HttpEntity doGetOrOther(HttpRequest request, HttpResponse response, String path) throws ServerErrorException {
        if (request.getRequestMethod() == HttpMethod.GET) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName(path);
            return callViewResolver(request, response, modelAndView);
        }
        return buildErrorResponse(request, response);
    }

    private HttpEntity callViewResolver(HttpRequest request, HttpResponse response, ModelAndView modelAndView) throws ServerErrorException {
        try {
            ViewResolver.buildView(request, response, modelAndView);
            return new HttpEntity(HttpStatus.OK);
        } catch (NotFoundException e) {
            return buildErrorResponse(request, response);
        }
    }

    private HttpEntity buildErrorResponse(HttpRequest request, HttpResponse response) {
        ViewResolver.buildErrorView(request, response);
        return new HttpEntity(HttpStatus.NOT_FOUND);
    }

}
