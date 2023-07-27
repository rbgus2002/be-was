package router;

import controller.DynamicFileController;
import controller.ServiceController;
import controller.StaticFileController;
import exception.HTTPException;
import exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.HttpUtil;
import webserver.http.model.Request;
import webserver.http.model.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Request 객체를 받아 컨트롤러에 정의된 메서드에게 처리를 맡기는 클래스
 */
public class Router {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final DynamicFileController dynamicFileController = new DynamicFileController();
    private static final ServiceController serviceController = new ServiceController();

    public static Response generateResponse(Request request) {
        Response response;

        try {

            // 동적(렌더링) 파일
            response = findMethodAndGenerateResponse(dynamicFileController, request);
            if(response != null) {
                return response;
            }

            // 정적 파일
            response = StaticFileController.genereateResponse(request);
            if(response != null) {
                return response;
            }

            // 서비스 로직
            response = findMethodAndGenerateResponse(serviceController, request);
            if(response != null) {
                return response;
            }

            // 모든 로직에서 처리되지 않는 경우 404 Not Found 반환
            throw new NotFoundException();
        }
        catch (HTTPException e) {
            return e.generateResponse();
        }
        catch (InvocationTargetException e) {
            HTTPException httpException = (HTTPException) e.getTargetException();
            return httpException.generateResponse();
        }
        catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    private static Response findMethodAndGenerateResponse(Object obj, Request request) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Method[] fileMethods =  clazz.getDeclaredMethods();
        for(Method method: fileMethods) {
            if(method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if(request.getMethod() == requestMapping.method()) {
                    if(Objects.equals(request.getTargetUri(), requestMapping.value())) {
                        return (Response) method.invoke(obj, request);
                    }
                    if(request.getTargetUri().startsWith(requestMapping.value())) {
                        return (Response) method.invoke(obj, request);
                    }
                }
            }
        }

        return null;
    }
}
