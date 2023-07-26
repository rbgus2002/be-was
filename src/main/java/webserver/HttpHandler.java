package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.exception.FoundException;
import support.exception.HttpException;
import support.exception.NotSupportedException;
import support.instance.DefaultInstanceManager;
import support.web.ControllerResolver;
import support.web.view.View;
import support.web.view.ViewFactory;
import support.web.view.ViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.strategy.NotFound;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class HttpHandler {

    public static final String MAIN_PAGE = "/index.html";
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    public void doGet(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        if (interceptController(request, response, path)) {
            return;
        }

        callViewResolver(request, response, path);
    }

    private static void callViewResolver(HttpRequest request, HttpResponse response, String path) {
        ViewFactory viewFactory = DefaultInstanceManager.getInstanceMagager().getInstance(ViewFactory.class);
        try {
            View view = viewFactory.getViewByName(path);
            if (view != null) {
                ViewResolver.buildView(request, response, view);
            } else {
                ViewResolver.buildView(request, response, path);
            }
            response.setStatus(HttpStatus.OK);
        } catch (IOException e) {
            buildErrorResponse(request, response, viewFactory);
        }
    }

    private static void buildErrorResponse(HttpRequest request, HttpResponse response, ViewFactory viewFactory) {
        response.setStatus(HttpStatus.NOT_FOUND);
        response.buildHeader(new NotFound());
        ViewResolver.buildView(request, response, viewFactory.getErrorView());
    }

    public void doPost(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        String path = request.getRequestPath();

        if (interceptController(request, response, path)) {
            return;
        }

        ViewFactory viewFactory = DefaultInstanceManager.getInstanceMagager().getInstance(ViewFactory.class);
        buildErrorResponse(request, response, viewFactory);
    }

    private boolean interceptController(HttpRequest request, HttpResponse response, String path) {
        try {
            String viewName = ControllerResolver.invoke(path, request, response);
            callViewResolver(request, response, viewName);
            return true;
        } catch (NotSupportedException e) {
            return false;
        } catch (FoundException e) {
            response.setStatus(e.getHttpStatus());
            response.appendHeader("Location", e.getRedirectionUrl());
            return true;
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatus());
            return true;
        }
    }

}
