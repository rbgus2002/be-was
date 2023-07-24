package webserver.http.response.process;

import common.annotation.RequestMapping;
import common.annotation.RequestParam;
import common.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import webserver.Main;
import webserver.http.Headers;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryParameter;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

public class ApplicationProcessStrategy implements ContentProcessStrategy {
    private static final String CLASS_EXTENSION = ".class";

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        try {
            List<Class> classes = findByHasAnnotationClasses(RestController.class);
            Method targetMethod = findTargetMethod(httpRequest, classes);
            return processMethod(httpRequest, targetMethod);
        } catch (
                InstantiationException | IllegalAccessException | IOException | NoSuchMethodException |
                ClassNotFoundException exception) {
            return HttpResponse.internalError(httpRequest.getMIME());
        } catch (InvocationTargetException e) {
            return HttpResponse.badRequest(httpRequest.getMIME());
        }
    }

    private Method findTargetMethod(final HttpRequest httpRequest, final List<Class> classes) {
        return classes.stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> method.getAnnotation(RequestMapping.class).value().equals(httpRequest.getPath()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 경로의 메서드를 찾을 수 없습니다."));
    }

    private HttpResponse processMethod(final HttpRequest httpRequest, final Method method)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {

        QueryParameter queryParameter = httpRequest.getRequestLine().getTarget().getQueryParameter();

        Object invoke = method.invoke(
                method.getDeclaringClass().getDeclaredConstructor().newInstance(),
                getMethodParameters(method, queryParameter.getMap())
        );

        return new HttpResponse(
                new ResponseLine(StatusCode.OK), Headers.create(httpRequest.getMIME()), convertObjectToBytes(invoke)
        );
    }

    private Object[] getMethodParameters(final Method method, final Map<String, String> map) {
        return Arrays.stream(method.getParameters())
                .filter(parameter -> map.containsKey(parameter.getDeclaredAnnotation(RequestParam.class).value()))
                .map(parameter -> map.get(parameter.getDeclaredAnnotation(RequestParam.class).value()))
                .toArray();
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

    private static List<Class> getClassesBy(final List<String> list) throws ClassNotFoundException {
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

    @Override
    public URL getURL(final HttpRequest httpRequest) {
        throw new RuntimeException("애플리케이션 요청입니다.");
    }

    @Override
    public String getRoot() {
        throw new RuntimeException("애플리케이션 요청입니다.");
    }
}
