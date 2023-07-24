package support.web;

import exception.ExceptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.exception.*;
import utils.ClassListener;
import webserver.request.HttpRequest;
import webserver.request.KeyValue;
import webserver.response.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static support.instance.DefaultInstanceManager.getInstanceMagager;

public abstract class ControllerResolver {

    private static final Map<String, ControllerMethod> controllers = new HashMap<>();
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

    /**
     * {@link Controller}의 {@link RequestMapping}된 메소드를 실행한다.
     *
     * @return 성공시 반환할 View 이름
     * @throws HttpException         컨트롤러 처리 대상이거나 연관된 경우 상황에 따라 Http Status를 반환하기 위한 각종 예외를 발생한다.
     * @throws NotSupportedException 컨트롤러 처리 대상이 아닐 경우 발생한다.
     */
    public static String invoke(String url, HttpRequest request, HttpResponse response) throws HttpException, NotSupportedException {
        // 요청 url에 해당하는 controller method를 찾는다.
        AtomicReference<Class<?>> clazz = new AtomicReference<>(null);
        AtomicReference<Method> methodAtomicReference = new AtomicReference<>(null);
        AtomicBoolean hasMethod = new AtomicBoolean(false);

        controllers.forEach((s, controllerMethods) -> {
            if (url.startsWith(s)) {
                ControllerMethodStruct methodStruct = controllerMethods.find(url.substring(s.length()));
                if (methodStruct != null) {
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
        Object[] args = transformQuery(request, response, method);

        // 메소드 실행
        Object instance = getInstanceMagager().getInstance(controllerClass);
        try {
            return (String) method.invoke(instance, args);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            throw throwable instanceof HttpException ? (HttpException) throwable : new ServerErrorException();
        } catch (IllegalAccessException e) {
            throw new ServerErrorException();
        }
    }

    /**
     * 컨트롤러 메소드에서 요구하는 적합한 쿼리 또는 파라미터 값을 찾는다.
     *
     * @throws BadRequestException 요구하는 쿼리 값을 모두 충족하지 않을 경우 발생한다.
     */
    private static Object[] transformQuery(HttpRequest request, HttpResponse response, Method method) throws BadRequestException {
        KeyValue requestQuery = request.getPathQueryOrParameter()
                .orElseThrow(() -> new BadRequestException(ExceptionName.WRONG_ARGUMENT));
        Parameter[] parameters = method.getParameters();

        Object[] args = Arrays.stream(parameters)
                .map(parameter -> {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        return requestQuery.getValue(parameter.getAnnotation(RequestParam.class).value());
                    } else if (parameter.getType() == HttpRequest.class) {
                        return request;
                    } else if (parameter.getType() == HttpResponse.class) {
                        return response;
                    }
                    return null;
                })
                .toArray();

        logger.debug("요청 인자 크기 : {}", args.length);

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
