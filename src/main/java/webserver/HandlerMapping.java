package webserver;

import annotation.GetMapping;
import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.Uri;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;

import static utils.StringUtils.appendNewLine;

public class HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMapping.class);

    public static void getHandler(HttpRequest request) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method[] methods = Controller.class.getMethods();
        for (Method method : methods) {
            if (isMapped(method, request.getPath())) {
                if(request.getQuery().size() != 0){
                    method.invoke(Controller.class.getDeclaredConstructor().newInstance());
                }else{
                    method.invoke(Controller.class.getDeclaredConstructor().newInstance());
                }
            }
        }
    }

    private static boolean isMapped(Method method, String requestPath) {
        if (!method.isAnnotationPresent(GetMapping.class)) {
            return false;
        }
        return requestPath.equals(method.getAnnotation(GetMapping.class).value());
    }


    /*
    doDispatch(HttpRequest request)
    실제 요청을 매핑시켜주는 메소드
     */

    /*
    getHandler(HttpRequest request?)
    templates 디렉토리 안에서 파일 찾아주는 메소드
     */
}
