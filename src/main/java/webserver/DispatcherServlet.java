package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void doService(HttpRequest request, OutputStream out) throws Throwable {
        logger.debug("request: {}", request);
        doDispatch(request, out);
    }

    protected void doDispatch(HttpRequest request, OutputStream out) throws Throwable {
        Method handler = getHandler(request);
        HttpResponse httpResponse;

        if (handler == null) {
            httpResponse = new HttpResponse("200 OK", request.getRequestPath());
            httpResponse.response(out);
            return;
        }

        String httpMethod = request.getMethod();
        boolean isGet = httpMethod.equals("GET");
        if (isGet) {
            MethodType methodType;
            if (handler.getParameterCount() > 0) {
                methodType = MethodType.methodType(HttpResponse.class, handler.getParameterTypes());
            } else {
                methodType = MethodType.methodType(HttpResponse.class);
            }

            MethodHandle methodHandle = MethodHandles.lookup()
                    .findVirtual(Controller.class, handler.getName(), methodType)
                    .bindTo(new Controller());

            if (methodHandle.type().parameterCount() > 0) {
                httpResponse = (HttpResponse) methodHandle.invoke(request.getParams());
            } else {
                httpResponse = (HttpResponse) methodHandle.invoke();
            }

            httpResponse.response(out);
        }
    }

    private Method getHandler(HttpRequest request) {
        Map<String, Method> handlers = HandlerMapping.getHandlerMappings();
        if (!handlers.isEmpty()) {
            for (String url : handlers.keySet()) {
                if (request.getRequestPath().equals(url)) {
                    return handlers.get(url);
                }
            }
        }

        return null;
    }
}
