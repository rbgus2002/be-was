package support;

import exception.ExceptionName;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import utils.ClassListener;
import webserver.request.HttpRequest;
import webserver.request.Query;

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

import static support.DefaultInstanceManager.getInstanceMagager;

public abstract class ControllerResolver {

    private final static Map<String, ControllerMethods> controllers = new HashMap<>();

    static {
        List<Class<?>> controllerClasses = ClassListener.scanClass("controller");

        controllerClasses.forEach(clazz -> {
            Controller annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
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

    /**
     * @return 만약 정상적으로 controller 처리가 완료될 경우 true를 반환한다. 아닌 경우 false를 반환한다.
     */
    public static boolean invoke(String url, HttpRequest request) throws InvocationTargetException, IllegalAccessException {
        AtomicReference<Class<?>> clazz = new AtomicReference<>(null);
        AtomicReference<Method> methodAtomicReference = new AtomicReference<>(null);
        controllers.forEach((s, controllerMethods) -> {
            if (url.startsWith(s)) {
                clazz.set(controllerMethods.getControllerClass());
                methodAtomicReference.set(controllerMethods.find(url.substring(s.length())));
            }
        });

        // controller 처리 대상인지 검증한다.
        Class<?> controllerClass = clazz.get();
        Method method = methodAtomicReference.get();
        if (controllerClass == null || method == null) {
            return false;
        }
        Object instance = getInstanceMagager().getInstance(controllerClass);

        // 헤더 처리
        Query requestQuery = request.getRequestQuery();

        Parameter[] parameters = method.getParameters();

        Object[] array = Arrays.stream(parameters)
                .filter(parameter -> parameter.isAnnotationPresent(RequestParam.class))
                .map(parameter -> parameter.getAnnotation(RequestParam.class))
                .map(RequestParam::value)
                .map(requestQuery::getValue)
                .toArray();

        if (Arrays.asList(array).contains(null)) {
            throw new IllegalArgumentException(ExceptionName.WRONG_ARGUMENT);
        }
        method.invoke(instance, array);
        return true;
    }

}
