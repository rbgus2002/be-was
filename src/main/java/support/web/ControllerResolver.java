package support.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.*;
import support.instance.DefaultInstanceManager;
import support.web.exception.BadRequestException;
import support.web.handler.ControllerMethodReturnValueHandlerComposite;
import utils.ClassListener;
import webserver.request.HttpRequest;
import webserver.request.QueryParameter;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Container
public class ControllerResolver {

    private final Map<HttpMethodAndPath, ControllerMethod> controllers = new HashMap<>();
    private ControllerMethodReturnValueHandlerComposite handlers;
    private static final Logger logger = LoggerFactory.getLogger(ControllerResolver.class);

    public ControllerResolver() {
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
                                            new ControllerMethod(controllerClass, controllerClass.getName().replaceFirst(".*\\.", ""), method));
                                }
                        );
            }
        });
    }

    private ControllerMethodReturnValueHandlerComposite getHandlers() {
        if (handlers == null) {
            handlers = DefaultInstanceManager.getInstanceManager().getInstance("ControllerMethodReturnValueHandlerComposite", ControllerMethodReturnValueHandlerComposite.class);
        }
        return handlers;
    }

    /**
     * {@link Controller}의 {@link RequestMapping}된 메소드를 실행한다.
     *
     * @return HttpEntity 클라이언트에 반환할 http response Status & header 값 <br/>
     * 만약 처리할 수 없는 경우 null을 반환한다.
     */
    public HttpEntity invoke(String url, HttpRequest request, HttpResponse response) throws Exception {
        // 요청 url에 해당하는 controller method를 찾는다.
        ControllerMethod controllerMethodStruct = controllers.get(new HttpMethodAndPath(request.getRequestMethod(), url));
        if (controllerMethodStruct == null) {
            return null;
        }

        // 헤더 처리
        Object[] args;
        try {
            args = transformQuery(request, response, controllerMethodStruct.getParameters());
        } catch (BadRequestException e) {
            return new HttpEntity(HttpStatus.BAD_REQUEST);
        }

        // 메소드 실행
        HttpEntity httpEntity = getHandlers().handleReturnValue(controllerMethodStruct.invoke(args), controllerMethodStruct.getReturnType(), request, response);
        return httpEntity != null ? httpEntity : new HttpEntity(HttpStatus.OK);
    }

    /**
     * 컨트롤러 메소드에서 요구하는 적합한 쿼리 또는 파라미터 값을 찾는다.
     *
     * @throws BadRequestException 요구하는 쿼리 값을 모두 충족하지 않을 경우 발생한다.
     */
    private Object[] transformQuery(HttpRequest request, HttpResponse response, Parameter[] parameters) throws BadRequestException {
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
