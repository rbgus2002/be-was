package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewResolver;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {
    private static final Map<String, Controller> map = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static DispatcherServlet instance = null;

    public static DispatcherServlet getInstance(){
        if (instance == null) {
            logger.debug("map.size() = {}", map.size());
            return instance = new DispatcherServlet();
        }
        return instance;
    }

    private DispatcherServlet() {
    }

    public void service(HTTPServletRequest request, HTTPServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException, InstantiationException {
        String url = request.getUrl();
        String viewPath = url;
        Method[] declaredMethods = ControllerGroup.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            logger.debug("method = {}", declaredMethod);
            logger.debug("isProper = {}", isProperMethod(declaredMethod, url));
            if (isProperMethod(declaredMethod, url)) {
                viewPath = (String) declaredMethod.invoke(new ControllerGroup(), request, response);
                break;
            }
        }
        logger.debug("viewName = {}", viewPath);
        ViewResolver view = new ViewResolver(viewPath, response, request);
        view.service();
        view.render();
    }

    private boolean isProperMethod(Method method, String url) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            if (method.getAnnotation(RequestMapping.class).path().equals(url)) {
                return true;
            }
        }
        return false;
    }
}
