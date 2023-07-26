package webserver;

import controller.Controller;
import exception.NotSupportedContentTypeException;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import http.HttpResponse;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static http.HttpStatus.*;
import static java.lang.invoke.MethodType.methodType;

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final DispatcherServlet servlet = new DispatcherServlet();

    private DispatcherServlet() {
    }

    public static DispatcherServlet getInstance() {
        return servlet;
    }

    public void doService(HttpRequest request, OutputStream out) throws Throwable {
        logger.debug("{}", request);

        doDispatch(request, out);
    }

    public void doDispatch(HttpRequest request, OutputStream out) throws Throwable {
        Method method = HandlerMapping.getMethodMapped(request);
        HttpResponse response = handle(request, method);
        processDispatchResult(response, out);
    }

    private HttpResponse handle(HttpRequest request, Method method) throws Throwable {
        String path = request.getPath();

        HttpResponse response;
        if (hasRequestPathMapped(method)) {
            response = executeRequest(request, method);
        } else {
            response = HttpResponse.init(path);
        }
        logger.debug("response : {}", response);
        processResources(response);
        return response;
    }

    private static boolean hasRequestPathMapped(Method method) {
        return method != null;
    }

    private HttpResponse executeRequest(HttpRequest request, Method method) throws Throwable {
        MethodHandle methodHandle = getMethodHandle(method);
        HttpResponse response;
        if (hasParameter(methodHandle.type())) {
            Map<String, String> map = (request.isGetMethod()) ? request.getQuery() : request.getBody();
            response = (HttpResponse) methodHandle.invoke(map);
        } else {
            response = (HttpResponse) methodHandle.invoke();
        }
        return response;
    }

    private void processResources(HttpResponse response) throws IOException {
        try {
            ContentType type = ContentType.findBy(response.getFilePath());
            logger.debug("type : {}", type);
            response.mapResourcePath(type);
            logger.debug("pathFile 변경 후 response : {}", response);
            response.doResponse();
        } catch (NotSupportedContentTypeException e) {
            logger.debug("NotSupportedContentTypeException >> {}", response);
            logger.error(e.getMessage());
            response.doResponse(FOUND);
        } catch (IOException e) {
            logger.debug("IOException (readAllBytes ERROR) >> {}", response);
            logger.error(Arrays.toString(e.getStackTrace()));
            response.doResponse(NOT_FOUND);
        }
    }

    private MethodHandle getMethodHandle(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = (hasParameter(method)) ? methodType(HttpResponse.class, method.getParameterTypes()) : methodType(HttpResponse.class);
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

    private void processDispatchResult(HttpResponse response, OutputStream out) {
        response.writeResponseToOutputStream(out);
    }
}
