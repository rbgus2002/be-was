package webserver;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;
import webserver.http.HttpMime;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.HttpStatus;

import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void doService(HttpRequest request, OutputStream out) throws Throwable {
        logger.debug("request: {}", request);
        doDispatch(request, out);
    }

    protected void doDispatch(HttpRequest request, OutputStream out) throws Throwable {
        Method handler = HandlerMapper.getHandler(request);
        HttpResponse httpResponse;

        if (handler == null) {
            String path = request.getRequestPath();
            HttpMime mime = request.getMime();
            httpResponse = new HttpResponse(HttpStatus.OK, path, mime);
            httpResponse.response(out);
            return;
        }

        HttpMethod httpMethod = request.getHttpMethod();
        boolean isGet = (httpMethod == HttpMethod.GET);
        boolean isPost = (httpMethod == HttpMethod.POST);
        if (isGet || isPost) {
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
        if (request.getParamMap().size() > 0) {
            logger.debug("param");
            return (HttpResponse) methodHandle.invoke(request.getParamMap());
        }

        if (request.getBodyMap().size() > 0) {
            logger.debug("body");
            return (HttpResponse) methodHandle.invoke(request.getBodyMap());
        }

        return (HttpResponse) methodHandle.invoke();
    }
}
