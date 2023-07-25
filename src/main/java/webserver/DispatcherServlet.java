package webserver;

import application.controller.WebController;
import exception.InvalidPathException;
import exception.InvalidQueryParameterException;
import webserver.view.view.View;
import webserver.view.viewResolver.StaticViewResolver;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet {

    private final HttpRequest request;

    public DispatcherServlet(HttpRequest request) {
        this.request = request;
    }

    public void dispatch(final DataOutputStream dos) throws IOException, InvalidPathException, InvalidQueryParameterException, InvocationTargetException, IllegalAccessException {
        StaticViewResolver staticViewResolver = new StaticViewResolver();

        View view = staticViewResolver.resolve(request.getFullPath());
        if(view != null) {
            view.render(request.getVersion(), null, dos);
            return;
        }

        ControllerMapper controllerMapper = new ControllerMapper();
        WebController controller = controllerMapper.getController(request);

        Method method = controllerMapper.getMethod(controller, request);
        ModelAndView modelAndView = (ModelAndView) method.invoke(controller, request);
        view = staticViewResolver.resolve(modelAndView.getViewName());
        view.render(request.getVersion(), modelAndView.getModel(), dos);
    }
}
