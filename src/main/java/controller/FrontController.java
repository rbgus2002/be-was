package controller;

import http.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.Model;
import model.User;
import session.Session;
import session.SessionManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class FrontController {
    private static final String REDIRECT = "redirect:";
    private static FrontController frontController;

    private FrontController() {}

    public static FrontController getInstance() {
        if (frontController == null) {
            synchronized(FrontController.class)
            {
                frontController = new FrontController();
            }
        }
        return frontController;
    }

    public HttpResponse doDispatch(HttpRequest httpRequest) throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        if (!ControllerMapper.hasHandler(httpRequest)) {
            return HttpResponse.createStatic(httpRequest.getRequestUri());
        }

        Method method = ControllerMapper.getHandlerMethod(httpRequest);
        ModelAndView modelAndView = ControllerAdapter.runHandlerMethod(method, httpRequest);

        if (isRedirect(modelAndView.getViewPath())) {
            Cookie cookie = createCookie(modelAndView);
            return HttpResponse.createRedirect(modelAndView.getViewPath(), cookie);
        }

        return HttpResponse.createDynamic(modelAndView.getViewPath());
    }


    //TODO: ModelAndView랑 세션 종속성 끊기
    private Cookie createCookie(ModelAndView modelAndView) {
        Optional<Model> optionalModel = modelAndView.getModel();

        if (optionalModel.isEmpty()) {
            return null;
        }

        Model model = optionalModel.get();
        User user = (User) model.getValue("user");
        if (user == null) {
            return null;
        }

        String sessionId = SessionManager.addSession(user);
        Session session = SessionManager.getSession(sessionId);
        return Cookie.create(sessionId, session.getExpires());
    }

    private boolean isRedirect(String viewPath) {
        return viewPath.startsWith(REDIRECT);
    }

}
