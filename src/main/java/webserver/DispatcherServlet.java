package webserver;

import application.controller.WebController;
import exception.CustomException;
import exception.internalServerError.MethodAccessException;
import exception.internalServerError.MethodInvocationException;
import exception.notFound.InvalidResourcePathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.response.HttpResponse;
import webserver.view.view.View;
import webserver.view.viewResolver.StaticViewResolver;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HttpRequest request;

    public DispatcherServlet(HttpRequest request) {
        this.request = request;
    }

    public void dispatch(final DataOutputStream dos) {

        try {
            StaticViewResolver staticViewResolver = new StaticViewResolver();
            Map<String, Object> model = null;

            Optional<View> viewOpt = staticViewResolver.resolve(request.getFullPath());
            if(viewOpt.isEmpty()) {
                ModelAndView modelAndView = invokeControllerMethod();
                model = modelAndView.getModel();
                viewOpt = staticViewResolver.resolve(modelAndView.getViewName());
            }

            if(viewOpt.isEmpty()) throw new InvalidResourcePathException(request.getFullPath());

            viewOpt.get().render(request.getVersion(), request.getContentType(), model, dos);
        } catch (CustomException e) {
            logger.debug(e.getMessage());
            HttpResponse httpResponse = HttpResponse.ofWithStatusOnly(request.getVersion(), e.getHttpStatus());
            httpResponse.sendResponse(dos);
        }
    }

    private ModelAndView invokeControllerMethod() {
        ControllerMapper controllerMapper = new ControllerMapper();
        WebController controller = controllerMapper.getController(request);

        Method method = controllerMapper.getMethod(controller, request);

        try {
            return (ModelAndView) method.invoke(controller, request);
        } catch (IllegalAccessException e) {
            throw new MethodAccessException(method.getName());
        } catch (InvocationTargetException e) {
            throw new MethodInvocationException(method.getName());
        }
    }
}
