package webserver.http.response;

import common.annotation.Controller;
import common.annotation.RequestBody;
import common.annotation.RequestMapping;
import common.annotation.RequestParam;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import webserver.Main;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryParameter;

public class ApplicationProcessStrategy implements ContentProcessStrategy {
    private static final String CLASS_EXTENSION = ".class";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        HttpResponse result = HttpResponse.init();
        try {
            List<Class> classes = findByHasAnnotationClasses(Controller.class);
            Method targetMethod = findTargetMethod(httpRequest, classes);
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

    private Method findTargetMethod(final HttpRequest httpRequest, final List<Class> classes) {
        return classes.stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> method.getAnnotation(RequestMapping.class).value().equals(httpRequest.getPath()))
                .filter(method -> method.getAnnotation(RequestMapping.class).method()
                        .equals(httpRequest.getRequestLine().getMethod())
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 경로의 메서드를 찾을 수 없습니다."));
    }

    private void processMethod(
            final HttpRequest httpRequest,
            final HttpResponse httpResponse,
            final Method method
    )
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        Object targetClass = method.getDeclaringClass().getDeclaredConstructor().newInstance();
        Object invoke = invokeMethod(method, targetClass, httpRequest, httpResponse);
        if (isRedirect(invoke)) {
            String redirectUrl = ((String) invoke).substring(REDIRECT_PREFIX.length());
            httpResponse.found(redirectUrl);
            return;
        }
        httpResponse.ok(httpRequest.getMIME(), convertObjectToBytes(invoke));
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
        setHttpResponse(httpResponse, parameters, result);
        setHttpRequest(httpRequest, parameters, result);
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

    private byte[] convertObjectToBytes(Object obj) throws IOException {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    public URL getURL(final HttpRequest httpRequest) {
        throw new RuntimeException("애플리케이션 요청입니다.");
    }

    @Override
    public String getRoot() {
        throw new RuntimeException("애플리케이션 요청입니다.");
    }
}
