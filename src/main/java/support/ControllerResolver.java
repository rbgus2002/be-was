package support;

import exception.ExceptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.PathVariable;
import support.exception.BadRequestException;
import support.exception.MethodNotAllowedException;
import support.exception.NotSupportedException;
import support.exception.ServerErrorException;
import utils.ClassListener;
import webserver.request.HttpRequest;
import webserver.request.Query;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static support.DefaultInstanceManager.getInstanceMagager;

public abstract class ControllerResolver {

    private final static Map<String, ControllerMethod> controllers = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ControllerResolver.class);

    static {
        List<Class<?>> controllerClasses = ClassListener.scanClass("controller");

        controllerClasses.forEach(clazz -> {
            Controller annotation = clazz.getAnnotation(Controller.class);
            if (annotation != null) {
                String path = annotation.value();

                Map<String, ControllerMethodStruct> controllerMethod = Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .collect(Collectors.toUnmodifiableMap(
                                method -> method.getAnnotation(RequestMapping.class).value(),
                                method -> new ControllerMethodStruct(HttpMethod.POST, method)
                        ));


                controllers.put(path, new ControllerMethod(clazz, controllerMethod));
            }
        });
    }

    public static void invoke(String url, HttpRequest request) throws MethodNotAllowedException, BadRequestException, NotSupportedException, ServerErrorException {
        // 요청 url에 해당하는 controller method를 찾는다.
        AtomicReference<Class<?>> clazz = new AtomicReference<>(null);
        AtomicReference<Method> methodAtomicReference = new AtomicReference<>(null);
        AtomicBoolean hasMethod = new AtomicBoolean(false);

        controllers.forEach((s, controllerMethods) -> {
            if (url.startsWith(s)) {
                ControllerMethodStruct methodStruct = controllerMethods.find(url.substring(s.length()));
                if(methodStruct != null){
                    hasMethod.set(true);
                    if (methodStruct.getHttpMethod() == request.getRequestMethod()) {
                        hasMethod.set(true);
                        clazz.set(controllerMethods.getControllerClass());
                        methodAtomicReference.set(methodStruct.getMethod());
                    }
                }
            }
        });

        Class<?> controllerClass = clazz.get();
        Method method = methodAtomicReference.get();
        verifyControllerTrigger(hasMethod.get(), controllerClass, method);

        // 헤더 처리
        Object[] args = transformQuery(request, method);

        // 메소드 실행
        Object instance = getInstanceMagager().getInstance(controllerClass);
        try {
            method.invoke(instance, args);
        } catch (Exception e) {
            throw new ServerErrorException();
        }
    }

    /**
     * 컨트롤러 메소드에서 요구하는 적합한 쿼리 값을 찾는다.
     * @throws BadRequestException 요구하는 쿼리 값을 모두 충족하지 않을 경우 발생한다.
     */
    private static Object[] transformQuery(HttpRequest request, Method method) throws BadRequestException {
        Query requestQuery = request.getRequestQuery();
        Parameter[] parameters = method.getParameters();

        Object[] args = Arrays.stream(parameters)
                .filter(parameter -> parameter.isAnnotationPresent(PathVariable.class))
                .map(parameter -> parameter.getAnnotation(PathVariable.class))
                .map(PathVariable::value)
                .map(requestQuery::getValue)
                .toArray();

        if (Arrays.asList(args).contains(null)) {
            throw new BadRequestException(ExceptionName.WRONG_ARGUMENT);
        }
        return args;
    }

    /**
     * 컨트롤러 처리 대상인지 검증한다. <br />
     * Note: 만약 처리 컨트롤러 메소드가 없을 경우 예외가 발생한다.
     *
     * @throws MethodNotAllowedException 같은 URL을 공유하는 다른 컨트롤러 메소드가 있을 경우 발생한다.
     * @throws NotSupportedException     다른 조건 없이 단순히 처리 메소드가 없을 경우 발생한다.
     */
    private static void verifyControllerTrigger(boolean hasMethod, Class<?> controllerClass, Method method) throws MethodNotAllowedException, NotSupportedException {
        if (controllerClass == null || method == null) {
            if (hasMethod) {
                throw new MethodNotAllowedException();
            }
            throw new NotSupportedException();
        }
    }

}
