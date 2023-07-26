package controller;

import model.Model;
import model.User;
import session.Session;
import session.SessionManager;
import http.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FrontController {
    private static final String REDIRECT = "redirect:";
    private static FrontController frontController;
    private final HttpRequest httpRequest;
    private final DataOutputStream dos;

    public FrontController(HttpRequest httpRequest, DataOutputStream dos) {
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    public static FrontController getInstance(HttpRequest httpRequest, DataOutputStream dos) {
        if (frontController == null) {
            synchronized(FrontController.class)
            {
                frontController = new FrontController(httpRequest, dos);
            }
        }

        return frontController;
    }

    public void doDispatch() throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        if (!ControllerMapper.hasHandler(httpRequest)) {
            HttpResponse httpResponse = HttpResponse.createStatic(httpRequest.getRequestUri());
            httpResponse.responseStatic(dos);
            return;
        }

        Method method = ControllerMapper.getHandlerMethod(httpRequest);
        ModelAndView modelAndView = ControllerAdapter.runHandlerMethod(method, httpRequest);

        if (isRedirect(modelAndView.getViewPath())) {
            String sessionId = createSession(modelAndView);
            Session session = SessionManager.getSession(sessionId);
            Cookie cookie = Cookie.create(sessionId, session.getExpires());
            HttpResponse httpResponse = HttpResponse.createRedirect(modelAndView.getViewPath(), cookie);
            httpResponse.responseRedirect(dos);
            return;
        }

        HttpResponse httpResponse = HttpResponse.createDynamic(modelAndView.getViewPath());
        httpResponse.responseDynamic(dos);
    }

    //TODO: ModelAndView랑 세션 종속성 끊기
    private String createSession(ModelAndView modelAndView) {
        Model model = modelAndView.getModel();
        User user = (User) model.getValue("user");
        if (user == null) {
            return null;
        }
        return SessionManager.addSession(user);
    }

    private boolean isRedirect(String viewPath) {
        return viewPath.startsWith(REDIRECT);
    }

}
