package webserver;

import application.controller.WebController;
import exception.notFound.NotFoundException;
import exception.badRequest.MissingParameterException;
import exception.notFound.InvalidResourcePathException;
import webserver.view.view.View;
import webserver.view.viewResolver.StaticViewResolver;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class DispatcherServlet {

    private final HttpRequest request;

    public DispatcherServlet(HttpRequest request) {
        this.request = request;
    }

    public void dispatch(final DataOutputStream dos) throws IOException, NotFoundException, MissingParameterException, InvocationTargetException, IllegalAccessException {
        StaticViewResolver staticViewResolver = new StaticViewResolver();

        Optional<View> viewOpt = staticViewResolver.resolve(request.getFullPath());
        if(viewOpt.isPresent()) {
            viewOpt.get().render(request.getVersion(), request.getContentType(), null, dos);
            return;
        }

        ControllerMapper controllerMapper = new ControllerMapper();
        WebController controller = controllerMapper.getController(request);

        Method method = controllerMapper.getMethod(controller, request);
        ModelAndView modelAndView = (ModelAndView) method.invoke(controller, request);
        viewOpt = staticViewResolver.resolve(modelAndView.getViewName());
        View view = viewOpt.orElseThrow(() -> new InvalidResourcePathException(request.getFullPath()));
        view.render(request.getVersion(), request.getContentType(), modelAndView.getModel(), dos);
    }
}
