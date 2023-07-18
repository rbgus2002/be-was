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

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void doDispatch(HttpRequest request, OutputStream out) throws Throwable {
        Method method = HandlerMapping.getHandler(request);

        HttpResponse response;
        if(request.isStaticFileRequested()){
            response = HttpResponse.find(request.getPath());
        }else{
            response = callMethod(request, method);
        }

        doResponse(out, response);
    }

    private HttpResponse callMethod(HttpRequest request, Method method) throws Throwable {
        MethodHandle methodHandle = getMethodHandle(method);
        HttpResponse response;
        if(methodHandle.type().parameterCount() == 0){
            response = (HttpResponse) methodHandle.invoke();
        }else{
            response = (HttpResponse) methodHandle.invoke(request.getQuery());
        }
        return response;
    }

    private MethodHandle getMethodHandle(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType;
        if(method.getParameterCount() == 0){
            methodType = MethodType.methodType(HttpResponse.class);
        }else{
            methodType = MethodType.methodType(HttpResponse.class, method.getParameterTypes());
        }
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
