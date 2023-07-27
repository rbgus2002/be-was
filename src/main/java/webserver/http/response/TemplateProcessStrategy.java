package webserver.http.response;

import common.annotation.Controller;
import common.annotation.RequestBody;
import common.annotation.RequestMapping;
import common.annotation.RequestParam;
import common.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import webserver.Main;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryParameter;

public class TemplateProcessStrategy implements ContentProcessStrategy {
    private static final String CLASS_EXTENSION = ".class";
    private static final String ROOT = "/templates/";
    private static final String TEMPLATE_REPLACE_TEXT = "@Yunsik:replace";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        HttpResponse result = HttpResponse.init();
        try {
            List<Class> classes = findByHasAnnotationClasses(Controller.class);
            Optional<Method> targetMethod = findTargetMethod(httpRequest, classes);
            processMethod(httpRequest, result, targetMethod);
        } catch (
                InstantiationException | IllegalAccessException | IOException | NoSuchMethodException |
                ClassNotFoundException exception) {
            result.internalError(httpRequest.getMIME());
        } catch (InvocationTargetException e) {
            result.badRequest(httpRequest.getMIME());
        }
        return result;
    }

    private List<Class> findByHasAnnotationClasses(final Class annotation) throws ClassNotFoundException {
        validationIsAnnotation(annotation);
        return getAllClass().stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private void validationIsAnnotation(final Class annotation) {
        if (!annotation.isAnnotation()) {
            throw new IllegalArgumentException("클래스가 어노테이션이 아닙니다.");
        }
    }

    private List<Class> getAllClass() throws ClassNotFoundException {
        String canonicalName = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return getClassesBy(getClassPaths(canonicalName, canonicalName));
    }

    private List<Class> getClassesBy(final List<String> list) throws ClassNotFoundException {
        List<Class> result = new ArrayList<>();
        for (String s : list) {
            Class<?> clazz = ClassLoader.getSystemClassLoader()
                    .loadClass(s.replace("/", ".").substring(1));
            result.add(clazz);
        }
        return result;
    }

    private List<String> getClassPaths(final String root, final String path) {
        List<String> result = new ArrayList<>();
        for (String s : Objects.requireNonNull(new File(path).list())) {
            if (s.contains(CLASS_EXTENSION)) {
                result.add(path.split(root)[1] + "/" + s.replace(CLASS_EXTENSION, ""));
            }
            if (!s.contains(CLASS_EXTENSION)) {
                result.addAll(getClassPaths(root, path + "/" + s));
            }
        }
        return result;
    }

    private Optional<Method> findTargetMethod(final HttpRequest httpRequest, final List<Class> classes) {
        return classes.stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> method.getAnnotation(RequestMapping.class).value().equals(httpRequest.getPath()))
                .filter(method -> method.getAnnotation(RequestMapping.class).method()
                        .equals(httpRequest.getRequestLine().getMethod())
                )
                .findFirst();
    }

    private void processMethod(
            final HttpRequest httpRequest,
            final HttpResponse httpResponse,
            final Optional<Method> method
    )
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        byte[] resource = FileUtil.get(getURL(httpRequest));
        if (method.isPresent()) {
            Object targetClass = method.get().getDeclaringClass().getDeclaredConstructor().newInstance();
            Object invoke = invokeMethod(method.get(), targetClass, httpRequest, httpResponse);
            if (isRedirect(invoke)) {
                String redirectUrl = ((String) invoke).substring(REDIRECT_PREFIX.length());
                httpResponse.found(redirectUrl);
                return;
            }
            String resource_text = new String(resource);
            if (invoke != null && invoke.getClass().equals(String.class)) {
                if (resource_text.contains(TEMPLATE_REPLACE_TEXT)) {
                    resource_text = resource_text.replace(TEMPLATE_REPLACE_TEXT, (String) invoke);
                }
            }
            httpResponse.ok(httpRequest.getMIME(), resource_text.getBytes(StandardCharsets.UTF_8));
            return;
        }
        httpResponse.ok(httpRequest.getMIME(), resource);
    }

    private Object invokeMethod(
            final Method method,
            final Object targetClass,
            final HttpRequest httpRequest,
            final HttpResponse httpResponse
    ) throws IllegalAccessException, InvocationTargetException {
        if (method.getParameters().length == 0) {
            return method.invoke(targetClass);
        }
        return method.invoke(targetClass, mappingParameters(method, httpRequest, httpResponse));
    }

    private Object[] mappingParameters(
            final Method method,
            final HttpRequest httpRequest,
            final HttpResponse httpResponse
    ) {
        Parameter[] parameters = method.getParameters();
        Object[] result = new Object[parameters.length];
        setRequestBody(httpRequest, parameters, result);
        setQueryParam(httpRequest, parameters, result);
        setHttpRequest(httpRequest, parameters, result);
        setHttpResponse(httpResponse, parameters, result);
        return result;
    }

    private void setHttpRequest(final HttpRequest httpRequest, final Parameter[] parameters, final Object[] result) {
        IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(common.annotation.HttpRequest.class))
                .forEach(i -> result[i] = httpRequest);
    }

    private void setHttpResponse(final HttpResponse httpResponse, final Parameter[] parameters, final Object[] result) {
        IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(common.annotation.HttpResponse.class))
                .forEach(i -> result[i] = httpResponse);
    }

    private void setQueryParam(final HttpRequest httpRequest, final Parameter[] parameters, final Object[] result) {
        QueryParameter queryParameter = httpRequest.getRequestLine().getTarget().getQueryParameter();
        if (queryParameter.getMap().size() == 0) {
            return;
        }
        IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(RequestParam.class))
                .forEach(i -> result[i] = queryParameter.get(parameters[i].getAnnotation(RequestParam.class).value()));
    }

    private void setRequestBody(final HttpRequest httpRequest, final Parameter[] parameters, final Object[] result) {
        if (httpRequest.getBody() == null) {
            return;
        }
        IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(RequestBody.class))
                .forEach(i -> result[i] = httpRequest.getBody());
    }

    private boolean isRedirect(final Object invoke) {
        return invoke.getClass().equals(String.class) && ((String) invoke).startsWith(REDIRECT_PREFIX);
    }

    @Override
    public String getRoot() {
        return ROOT;
    }
}
