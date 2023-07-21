package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void doService(HttpRequest request, OutputStream out) {
        logger.debug("request: {}", request);
        doDispatch(request, out);
    }

    protected void doDispatch(HttpRequest request, OutputStream out) {
        Method handler = getHandler(request);
        if (handler == null) {
            return;
        }

        String method = request.getMethod();
        boolean isGet = method.equals("GET");
        // TODO: invoke
    }

    private Method getHandler(HttpRequest request) {
        Map<String, Method> handlers = HandlerMapping.getHandlerMappings();
        if (handlers != null) {
            for (String url : handlers.keySet()) {
                if (request.getRequestPath().equals(url)) {
                    return handlers.get(url);
                }
            }
        }

        return null;
    }
}
