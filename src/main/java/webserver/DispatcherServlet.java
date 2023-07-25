package webserver;

import controller.Controller;
import exception.NotSupportedContentTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import http.HttpResponse;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Map;

import static java.lang.invoke.MethodType.methodType;
import static http.HttpStatus.*;

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final DispatcherServlet servlet = new DispatcherServlet();

    private DispatcherServlet() {
    }

    public static DispatcherServlet init() {
        return servlet;
    }

    public void doService(HttpRequest request, HttpResponse response, OutputStream out) throws Throwable {
        logger.debug("{}", request);

        doDispatch(request, response, out);
    }

    public void doDispatch(HttpRequest request, HttpResponse response, OutputStream out) throws Throwable {
        Method method = HandlerMapping.getMethodMapped(request);
        String filePath = handle(request, response, method);
        processDispatchResult(filePath, response, out);
    }

    private String handle(HttpRequest request, HttpResponse response, Method method) throws Throwable {
        String path = request.getPath();
        if (hasRequestPathMapped(method)) {
            path = executeRequest(request, method);
        }
        return processResources(response, path);
    }

    private static boolean hasRequestPathMapped(Method method) {
        return method != null;
    }

    private String executeRequest(HttpRequest request, Method method) throws Throwable {
        MethodHandle methodHandle = getMethodHandle(method);
        String path;
        if (hasParameter(methodHandle.type())) {
            Map<String, String> map = (request.isGetMethod()) ? request.getQuery() : request.getBody();
            path = (String) methodHandle.invoke(map);
        } else {
            path = (String) methodHandle.invoke();
        }
        return path;
    }

    private String processResources(HttpResponse response, String filePath) throws IOException {
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
        return filePath;
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

    private void processDispatchResult(String filePath, HttpResponse response, OutputStream out) {
        ContentType type = ContentType.findBy(filePath);
        response.writeResponseToOutputStream(out, type);
    }
}
