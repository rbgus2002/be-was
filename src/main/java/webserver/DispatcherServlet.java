package webserver;

import controller.Controller;
import exception.NotSupportedContentTypeException;
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
import static webserver.http.HttpStatus.*;

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private DispatcherServlet() {
    }

    public static DispatcherServlet init() {
        return new DispatcherServlet();
    }

    public void doService(HttpRequest request, HttpResponse response, OutputStream out) throws Throwable {
        logger.debug("{}", request);

        doDispatch(request, response, out);
    }

    public void doDispatch(HttpRequest request, HttpResponse response, OutputStream out) throws Throwable {
        Method method = HandlerMapping.getMethodMapped(request);
        handle(request, response, method);
        processDispatchResult(request, response, out);
    }

    private void handle(HttpRequest request, HttpResponse response, Method method) throws Throwable {
        String path = request.getPath();
        if (hasRequestPathMapped(method)) {
            path = executeRequest(request, method);
        }
        processResources(response, path);
    }

    private static boolean hasRequestPathMapped(Method method) {
        return method != null;
    }

    private String executeRequest(HttpRequest request, Method method) throws Throwable {
        MethodHandle methodHandle = getMethodHandle(method);
        String path;
        if (hasParameter(methodHandle.type())) {
            path = (String) methodHandle.invoke(request.getQuery());
        } else {
            path = (String) methodHandle.invoke();
        }
        return path;
    }

    private void processResources(HttpResponse response, String filePath) throws IOException {
        try {
            ContentType type = ContentType.findBy(filePath);
            filePath = type.mapResourceFolders(filePath);
            response.setResults(filePath, OK);
        } catch (NotSupportedContentTypeException e) {
            response.setResults(null, BAD_REQUEST);
            logger.debug(e.getMessage());
        } catch (IOException e) {
            response.setResults(null, NOT_FOUND);
            logger.debug(e.getMessage());
        }
    }

    private MethodHandle getMethodHandle(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = (hasParameter(method)) ? methodType(String.class, method.getParameterTypes()) : methodType(String.class);
        return MethodHandles.lookup()
                .findVirtual(Controller.class, method.getName(), methodType)
                .bindTo(new Controller());
    }

    private boolean hasParameter(MethodType methodType) {
        return methodType.parameterCount() != 0;
    }

    private boolean hasParameter(Method method) {
        return method.getParameterCount() != 0;
    }

    private void processDispatchResult(HttpRequest request, HttpResponse response, OutputStream out) {
        ContentType type = ContentType.findBy(request.getPath());
        response.writeResponseToOutputStream(out, type);
    }
}
