package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static java.lang.invoke.MethodType.methodType;

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void doDispatch(HttpRequest request, OutputStream out) throws Throwable {
        Method method = HandlerMapping.getHandler(request);
        HttpResponse response = getHttpResponse(request, method);
        doResponse(out, response);
    }

    private HttpResponse getHttpResponse(HttpRequest request, Method method) throws Throwable {
        return (method == null) ? HttpResponse.findStatic(request.getPath()) : callMethod(request, method);
    }

    private HttpResponse callMethod(HttpRequest request, Method method) throws Throwable {
        MethodHandle methodHandle = getMethodHandle(method);
        Object response = (hasParameter(methodHandle.type())) ? methodHandle.invoke(request.getQuery()) : methodHandle.invoke();
        return (HttpResponse) response;
    }

    private boolean hasParameter(MethodType methodType){
        return methodType.parameterCount() != 0;
    }

    private boolean hasParameter(Method method){
        return method.getParameterCount() != 0;
    }

    private MethodHandle getMethodHandle(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = (hasParameter(method)) ? methodType(HttpResponse.class, method.getParameterTypes()) : methodType(HttpResponse.class);
        return MethodHandles.lookup()
                .findVirtual(Controller.class, method.getName(), methodType)
                .bindTo(new Controller());
    }

    public void doService(InputStream in, OutputStream out) throws Throwable {
        // request
        HttpRequest request = HttpRequest.from(in);
        logger.debug("{}", request);

        doDispatch(request, out);
    }

    private void doResponse(OutputStream out, HttpResponse httpResponse){
        httpResponse.response(out);
    }
}
