package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.exception.*;
import support.instance.DefaultInstanceManager;
import support.web.ControllerResolver;
import support.web.ModelAndView;
import support.web.ViewResolver;
import support.web.view.View;
import support.web.view.ViewFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.strategy.NotFound;

import java.lang.reflect.InvocationTargetException;

public class HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    public void doGet(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        try {
            try {
                ModelAndView modelAndView = ControllerResolver.invoke(path, request, response);
                callViewResolver(request, response, modelAndView.getViewName());
            } catch (NotSupportedException e) {
                callViewResolver(request, response, path);
            }
        } catch (FoundException e) {
            response.setStatus(e.getHttpStatus());
            response.appendHeader("Location", e.getRedirectionUrl());
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatus());
        }

    }

    public void doPost(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        try {
            ModelAndView modelAndView = ControllerResolver.invoke(path, request, response);
            callViewResolver(request, response, modelAndView.getViewName());
        } catch (NotSupportedException e) {
            buildErrorResponse(request, response);
        } catch (FoundException e) {
            response.setStatus(e.getHttpStatus());
            response.appendHeader("Location", e.getRedirectionUrl());
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatus());
        }
    }

    private static void callViewResolver(HttpRequest request, HttpResponse response, String path) throws ServerErrorException {
        ViewFactory viewFactory = DefaultInstanceManager.getInstanceMagager().getInstance(ViewFactory.class);
        try {
            View view = viewFactory.getViewByName(path);
            if (view != null) {
                ViewResolver.buildView(request, response, view);
            } else {
                ViewResolver.buildView(request, response, path);
            }
            response.setStatus(HttpStatus.OK);
        } catch (NotFoundException e) {
            buildErrorResponse(request, response);
        }
    }

    private static void buildErrorResponse(HttpRequest request, HttpResponse response) {
        ViewFactory viewFactory = DefaultInstanceManager.getInstanceMagager().getInstance(ViewFactory.class);
        response.setStatus(HttpStatus.NOT_FOUND);
        response.buildHeader(new NotFound());
        ViewResolver.buildView(request, response, viewFactory.getErrorView());
    }

}
