package router;

import controller.DynamicFileController;
import controller.ServiceController;
import controller.StaticFileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.model.Request;
import webserver.model.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Request 객체를 받아 컨트롤러에 정의된 메서드에게 처리를 맡기는 클래스
 */
public class Router {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static Response generateResponse(Request request) {
        Response response;

        try {

            // 동적(렌더링) 파일
            response = findMethodAndGenerateResponse(DynamicFileController.class, request);
            if(response != null) {
                return response;
            }

            // 정적 파일
            response = StaticFileController.genereateResponse(request);
            if(response != null) {
                return response;
            }

            // 서비스 로직
            response = findMethodAndGenerateResponse(ServiceController.class, request);
            if(response != null) {
                return response;
            }
        }
        catch (Exception e) {
            logger.error(e.getStackTrace().toString());
        }

        return null;
    }

    private static Response findMethodAndGenerateResponse(Class<?> clazz, Request request) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        Method[] fileMethods =  clazz.getDeclaredMethods();
        for(Method method: fileMethods) {
            if(method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if(request.getMethod() == requestMapping.method() &&
                        Objects.equals(request.getTargetUri(), requestMapping.value())) {
                    return (Response) method.invoke(Class.forName(clazz.getName()).getConstructor().newInstance(), request);
                }
            }
        }

        return null;
    }
}
