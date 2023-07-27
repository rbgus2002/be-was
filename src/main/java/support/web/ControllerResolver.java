package support.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.PathVariable;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.instance.DefaultInstanceManager;
import support.web.exception.BadRequestException;
import support.web.exception.NotSupportedException;
import support.web.handler.ControllerMethodReturnValueHandlerComposite;
import utils.ClassListener;
import webserver.request.HttpRequest;
import webserver.request.QueryParameter;
import webserver.response.HttpResponse;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ControllerResolver {

    private static final Map<HttpMethodAndPath, ControllerMethod> controllers = new HashMap<>();
    private static ControllerMethodReturnValueHandlerComposite handlers;
    private static final Logger logger = LoggerFactory.getLogger(ControllerResolver.class);

    static {
        List<Class<?>> controllerClasses = ClassListener.scanClass("controller");

        controllerClasses.forEach(controllerClass -> {
            Controller controller = controllerClass.getAnnotation(Controller.class);
            if (controller != null) {
                Arrays.stream(controllerClass.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .forEach(
                                method -> {
                                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                    controllers.put(new HttpMethodAndPath(requestMapping.method(), controller.value() + requestMapping.value()),
                                            new ControllerMethod(controllerClass, method));
                                }
                        );
            }
        });
    }

    private static ControllerMethodReturnValueHandlerComposite getHandlers() {
        if (handlers == null) {
            handlers = DefaultInstanceManager.getInstanceMagager().getInstance(ControllerMethodReturnValueHandlerComposite.class);
        }
        return handlers;
    }

    /**
     * {@link Controller}의 {@link RequestMapping}된 메소드를 실행한다.
     */
    public static HttpEntity invoke(String url, HttpRequest request, HttpResponse response) throws Exception {
        // 요청 url에 해당하는 controller method를 찾는다.
        ControllerMethod controllerMethodStruct = findControllerMethodStruct(url, request);

        // 헤더 처리
        Object[] args = transformQuery(request, response, controllerMethodStruct.getParameters());

        // 메소드 실행
        return getHandlers().handleReturnValue(controllerMethodStruct.invoke(args), controllerMethodStruct.getReturnType(), request, response);
    }

    private static ControllerMethod findControllerMethodStruct(String url, HttpRequest request) throws NotSupportedException {
        ControllerMethod controllerMethodStruct = controllers.get(new HttpMethodAndPath(request.getRequestMethod(), url));

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
    private static Object[] transformQuery(HttpRequest request, HttpResponse response, Parameter[] parameters) throws BadRequestException {
        QueryParameter body = request.getBody();
        QueryParameter query = request.getQuery();

        Object[] args = Arrays.stream(parameters)
                .map(parameter -> {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        return body.getValue(parameter.getAnnotation(RequestParam.class).value());
                    } else if (parameter.isAnnotationPresent(PathVariable.class)) {
                        return query.getValue(parameter.getAnnotation(PathVariable.class).value());
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
            throw new BadRequestException();
        }

        return args;
    }

}
