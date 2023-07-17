package support;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestParam;
import utils.ClassListener;
import webserver.Query;
import webserver.RequestHeader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static support.DefaultManageObjectFactory.getManageObjectFactory;

public abstract class ControllerResolver {

    private final static Map<String, ControllerMethods> controllers = new HashMap<>();

    static {
        List<Class<?>> controllerClasses = ClassListener.scanClass("controller");

        controllerClasses.forEach(clazz -> {
            Controller annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                try {
                    getManageObjectFactory().addInstance(clazz);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                String path = annotation.value();

                Map<String, Method> controllerMethods = Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .collect(Collectors.toUnmodifiableMap(
                                method -> method.getAnnotation(RequestMapping.class).value(),
                                Function.identity()
                        ));

                controllers.put(path, new ControllerMethods(clazz, controllerMethods));
            }
        });
    }

    public static void invoke(String url, RequestHeader header) throws InvocationTargetException, IllegalAccessException {
        AtomicReference<Class<?>> clazz = new AtomicReference<>(null);
        AtomicReference<Method> methodAtomicReference = new AtomicReference<>(null);
        controllers.forEach((s, controllerMethods) -> {
            if (url.startsWith(s)) {
                clazz.set(controllerMethods.getControllerClass());
                methodAtomicReference.set(controllerMethods.find(url.substring(s.length())));
            }
        });

        Class<?> controllerClass = clazz.get();
        Method method = methodAtomicReference.get();
        if (controllerClass == null || method == null) {
            throw new IllegalArgumentException("URL_NOT_FOUND");
        }
        Object instance = getManageObjectFactory().getInstance(controllerClass);

        // 헤더 처리
        Query requestQuery = header.getRequestQuery();

        Parameter[] parameters = method.getParameters();

        Object[] array = Arrays.stream(parameters)
                .filter(parameter -> parameter.isAnnotationPresent(RequestParam.class))
                .map(parameter -> parameter.getAnnotation(RequestParam.class))
                .map(RequestParam::value)
                .map(requestQuery::getValue)
                .toArray();

        method.invoke(instance, array);
    }

}
