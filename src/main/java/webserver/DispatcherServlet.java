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
        Method handler = HandlerMapping.getHandler(request);
        HttpResponse httpResponse;

        if (handler == null) {
            httpResponse = new HttpResponse("200 OK", request.getRequestPath());
            httpResponse.response(out);
            return;
        }

        String httpMethod = request.getMethod();
        boolean isGet = httpMethod.equals("GET");
        if (isGet) {
            MethodType methodType = getMethodType(handler);
            MethodHandle methodHandle = getMethodHandle(handler, methodType);
            httpResponse = getHttpResponse(request, methodHandle);
            httpResponse.response(out);
        }
    }

    private MethodType getMethodType(Method handler) {
        MethodType methodType;
        if (handler.getParameterCount() > 0) {
            methodType = MethodType.methodType(HttpResponse.class, handler.getParameterTypes());
        } else {
            methodType = MethodType.methodType(HttpResponse.class);
        }
        return methodType;
    }

    private MethodHandle getMethodHandle(Method handler, MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup()
                .findVirtual(Controller.class, handler.getName(), methodType)
                .bindTo(new Controller());
    }

    private HttpResponse getHttpResponse(HttpRequest request, MethodHandle methodHandle) throws Throwable {
        HttpResponse httpResponse;
        if (methodHandle.type().parameterCount() > 0) {
            httpResponse = (HttpResponse) methodHandle.invoke(request.getParams());
        } else {
            httpResponse = (HttpResponse) methodHandle.invoke();
        }
        return httpResponse;
    }
}
