package webserver;

import annotations.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

public class HandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerAdapter.class);

    //TODO: 예외처리를 404 페이지로 MV 반환하도록 수정
    protected ModelAndView handle(HttpRequest request, HttpResponse response, Object handler) {

        Class<?> controller = handler.getClass();

        Method requestHandler = Arrays.stream(controller.getDeclaredMethods())
                .filter(method -> matchesURI(request, method))
                .findFirst()
                .orElse(null);

        if(requestHandler == null) {
            response.setStatus(HttpStatus.OK);
            return staticView(request);
        }

        Object result = null;
        ModelAndView mv = null;
        try {
            result = requestHandler.invoke(controller.getDeclaredConstructor().newInstance(), request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        Type type = requestHandler.getGenericReturnType();

        if(type.equals(String.class)) {
            mv = new ModelAndView();
            mv.setViewName((String) result);
            return mv;
        }

        if(type.equals(void.class)) {
            mv = new ModelAndView();
            mv.setViewName(request.getRequestURI());
            return mv;
        }

        if(type.equals(Object.class)) {
            //객체로 반환시 처리
        }

        return mv;
    }

    private boolean matchesURI(HttpRequest request, Method method) {
        if(method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);

            if(annotation.method().compareTo(request.getMethod()) != 0) {
                return false;
            }

            return annotation.value().equals(request.getRequestURI());
        }
        return false;
    }

    protected ModelAndView staticView(HttpRequest request) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName(request.getRequestURI());

        return mv;
    }
}
