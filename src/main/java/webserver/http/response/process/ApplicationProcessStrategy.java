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
import java.util.stream.Collectors;
import webserver.Main;
import webserver.http.Headers;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryParameter;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

public class ApplicationProcessStrategy implements ContentProcessStrategy {
    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        List<Class> classes = findByHasAnnotationClasses(RestController.class);

        Method targetMethod = classes.stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> method.getAnnotation(RequestMapping.class).value().equals(httpRequest.getPath()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 경로의 메서드를 찾을 수 없습니다."));

        return processMethod(httpRequest, targetMethod);
    }

    private HttpResponse processMethod(final HttpRequest httpRequest, final Method method) {
        try {
            QueryParameter queryParameter = httpRequest.getRequestLine().getTarget().getQueryParameter();
            Map<String, String> map = queryParameter.getMap();

            Object[] parameters = Arrays.stream(method.getParameters())
                    .filter(parameter -> map.containsKey(parameter.getDeclaredAnnotation(RequestParam.class).value()))
                    .map(parameter -> map.get(parameter.getDeclaredAnnotation(RequestParam.class).value()))
                    .toArray();

            Object invoke = method.invoke(method.getDeclaringClass().newInstance(), parameters);

            return new HttpResponse(
                    new ResponseLine(StatusCode.OK), Headers.create(httpRequest.getMIME()), convertObjectToBytes(invoke)
            );
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            return HttpResponse.internalError(httpRequest.getMIME());
        } catch (InvocationTargetException e) {
            return HttpResponse.badRequest(httpRequest.getMIME());
        }
    }

    private byte[] convertObjectToBytes(Object obj) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        }
    }

    public List<Class> findByHasAnnotationClasses(final Class annotation) {
        return getAllClass().stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private List<Class> getAllClass() {
        List<Class> result = new ArrayList<>();

        String canonicalName = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        List<String> list = getList(canonicalName, canonicalName);
        for (String s : list) {
            try {
                Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(s.replace("/", ".").substring(1));
                result.add(clazz);
            } catch (ClassNotFoundException exception) {
                // 아무것도 안함
            }
        }
        return result;
    }

    private List<String> getList(final String root, final String path) {
        List<String> result = new ArrayList<>();
        File file = new File(path);
        for (String s : file.list()) {
            if (s.contains(".class")) {
                result.add(path.split(root)[1] + "/" + s.replace(".class", ""));
            }
            if (!s.contains(".class")) {
                result.addAll(getList(root, path + "/" + s));
            }
        }
        return result;
    }

    @Override
    public URL getURL(final HttpRequest httpRequest) {
        return ContentProcessStrategy.super.getURL(httpRequest);
    }

    @Override
    public String getRoot() {
        return null;
    }
}
