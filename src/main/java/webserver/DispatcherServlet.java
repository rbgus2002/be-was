package webserver;

import application.controller.WebController;
import exception.CustomException;
import exception.internalServerError.MethodAccessException;
import exception.internalServerError.MethodInvocationException;
import exception.notFound.InvalidResourcePathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Constants.ContentType;
import webserver.http.Constants.HeaderOption;
import webserver.http.response.HttpResponse;
import webserver.view.view.View;
import webserver.view.viewResolver.StaticViewResolver;
import webserver.http.request.HttpRequest;

import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void dispatch(final HttpRequest request, final HttpResponse response, final DataOutputStream dos) {
        try {
            StaticViewResolver staticViewResolver = new StaticViewResolver();
            Map<String, Object> model = null;

            Optional<View> viewOpt = staticViewResolver.resolve(request.getFullPath());
            if(viewOpt.isEmpty()) {
                ModelAndView modelAndView = invokeControllerMethod(request, response);
                model = modelAndView.getModel();
                viewOpt = staticViewResolver.resolve(modelAndView.getViewName());
            }

            if(viewOpt.isEmpty()) throw new InvalidResourcePathException(request.getFullPath());

            viewOpt.get().render(request, response, model, dos);
        } catch (CustomException e) {
            logger.debug(e.getMessage());
            response.setHttpStatus(e.getHttpStatus());
            response.addHeaderElement(HeaderOption.CONTENT_TYPE, ContentType.HTML.getDescription());
            response.setBody(e.getHttpStatus().getDescription().getBytes(StandardCharsets.UTF_8));
            response.sendResponse(dos);
        }
    }

    private ModelAndView invokeControllerMethod(final HttpRequest request, final HttpResponse response) {
        ControllerMapper controllerMapper = new ControllerMapper();
        WebController controller = controllerMapper.getController(request);

        Method method = controllerMapper.getMethod(controller, request);

        try {
            return (ModelAndView) method.invoke(controller, request, response);
        } catch (IllegalAccessException e) {
            throw new MethodAccessException(method.getName());
        } catch (InvocationTargetException e) {
            throw new MethodInvocationException(method.getName());
        }
    }
}
