package support.web;

import exception.ExceptionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.exception.BadRequestException;
import support.exception.HttpException;
import support.exception.NotSupportedException;
import support.exception.ServerErrorException;
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

import static support.instance.DefaultInstanceManager.getInstanceMagager;

public abstract class ControllerResolver {

    private static final Map<HttpMethod, Map<String, ControllerMethod>> methodController = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ControllerResolver.class);

    static {
        Arrays.stream(HttpMethod.values())
                .forEach(httpMethod -> methodController.put(httpMethod, new HashMap<>()));

        List<Class<?>> controllerClasses = ClassListener.scanClass("controller");

        controllerClasses.forEach(controllerClass -> {
            Controller controller = controllerClass.getAnnotation(Controller.class);
            if (controller != null) {
                String path = controller.value();

                Arrays.stream(controllerClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .forEach(
                                method -> {
                                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                    Map<String, ControllerMethod> controllers = methodController.get(requestMapping.method());
                                    controllers.put(path + requestMapping.value(),
                                            new ControllerMethod(controllerClass, method));
                                }
                        );
            }
        });
    }

    /**
     * {@link Controller}의 {@link RequestMapping}된 메소드를 실행한다.
     *
     * @return 성공시 {@link ModelAndView} 반환
     * @throws HttpException         컨트롤러 처리 대상이거나 연관된 경우 상황에 따라 Http Status를 반환하기 위한 각종 예외를 발생한다.
     * @throws NotSupportedException 컨트롤러 처리 대상이 아닐 경우 발생한다.
     */
    public static ModelAndView invoke(String url, HttpRequest request, HttpResponse response) throws HttpException, NotSupportedException {
        // 요청 url에 해당하는 controller method를 찾는다.
        ControllerMethod controllerMethodStruct = findControllerMethodStruct(url, request);
        Class<?> controllerClass = controllerMethodStruct.getControllerClass();
        Method method = controllerMethodStruct.getMethod();

        // 헤더 처리
        Object[] args = transformQuery(request, response, method);

        // 메소드 실행
        Object instance = getInstanceMagager().getInstance(controllerClass);
        try {
            return (ModelAndView) method.invoke(instance, args);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            throw throwable instanceof HttpException ? (HttpException) throwable : new ServerErrorException();
        } catch (IllegalAccessException e) {
            throw new ServerErrorException();
        }
    }

    private static ControllerMethod findControllerMethodStruct(String url, HttpRequest request) throws NotSupportedException {
        Map<String, ControllerMethod> controllers = methodController.get(request.getRequestMethod());
        ControllerMethod controllerMethodStruct = controllers.get(url);
        if (controllerMethodStruct == null) {
            throw new NotSupportedException();
        }
        return controllerMethodStruct;
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
            logger.debug("BadRequest 발생");
            throw new BadRequestException(ExceptionName.WRONG_ARGUMENT);
        }

        return args;
    }

}
