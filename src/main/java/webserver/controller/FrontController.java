package webserver.controller;

import model.User;
import session.UserSessionManager;
import webserver.ModelAndView;
import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FrontController {
    private final HttpRequest httpRequest;
    private final DataOutputStream dos;

    public FrontController(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    public void doDispatch() throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        if (!HandlerMapper.hasHandler(httpRequest)) {
            HttpResponse httpResponse = HttpResponse.createStatic(httpRequest.getRequestUri());
            httpResponse.responseStatic(dos);
            return;
        }

        Method method = HandlerMapper.getHandlerMethod(httpRequest);
        ModelAndView modelAndView = HandlerAdapter.runHandlerMethod(method, httpRequest);
        Cookie cookie = createSession(modelAndView);

        if (isRedirect(modelAndView.getViewPath())) {
            HttpResponse httpResponse = HttpResponse.createRedirect(modelAndView.getViewPath(), cookie);
            httpResponse.responseRedirect(dos);
            return;
        }

        HttpResponse httpResponse = HttpResponse.createDynamic(modelAndView.getViewPath(), cookie);
        httpResponse.responseDynamic(dos);
    }

    private Cookie createSession(ModelAndView modelAndView) {
        if (!(modelAndView.getModel() instanceof User)) {
            return null;
        }
        return UserSessionManager.addUser((User) modelAndView.getModel());
    }

    private boolean isRedirect(String viewPath) {
        return viewPath.startsWith("redirect:");
    }

}
