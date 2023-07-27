package webserver;

import annotations.RequestMapping;
import dto.UserDTO;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import model.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

public class HandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerAdapter.class);

    /**
     * Dynamic View 요청에 대한 처리
     * @param request
     * @param response
     * @param handler, null이 아님을 보장해야함
     * @return ModelAndView, templates에 존재하지 않으면 에러 페이지의 ModelAndView 반환
     */
    protected ModelAndView handle(HttpRequest request, HttpResponse response, Class<?> handler) {

        Method requestHandler = getMappedMethod(request, handler);

        //RequestMapping된 메서드의 파라미터에 맞게 데이터 삽입
        Parameter[] parameters = requestHandler.getParameters();
        Object[] parameterValues = new Object[parameters.length];
        int index = 0;
        for(Parameter parameter : parameters) {
            try {
                parameterValues[index] = getParameterValue(request, response, parameter);
                index++;
            } catch (RuntimeException e) {
                //404 에러페이지 내용을 가지는 view 반환하기
                return new ModelAndView();
            }
        }

        Object result = null;
        try {
            result = requestHandler.invoke(handler.getDeclaredConstructor().newInstance(), parameterValues);
        } catch (Exception e) {
            logger.error("Invoke되어야 하는 핸들러(requestHandler에서 전달받은)가 존재하지 않습니다.");
        }

        //핸들러가 실행되고 나온 return값을 기반으로 ModelAndView 생성
        return createModelAndView(request, result);
    }

    private Object getParameterValue(HttpRequest request, HttpResponse response, Parameter parameter) {

        if(parameter.getParameterizedType().equals(HttpRequest.class)) {
            return request;
        }

        if(parameter.getParameterizedType().equals(HttpResponse.class)) {
            return response;
        }

        if(request.getBody() == null) {
            throw new RuntimeException("URL 변수가 존재하지 않습니다.");
        }

        Type paramType = parameter.getParameterizedType();

        //TODO: 여기에서 클래스별로 처리 방식을 어떻게 해야 깔끔하게 될지 고민해야함
        if(paramType.equals(UserDTO.class)) {
            Map<String, String> dataset = (Map<String, String>) request.getBody();

             return new UserDTO.builder()
                     .userId(dataset.get("userId"))
                     .password(dataset.get("password"))
                     .email(dataset.get("email"))
                     .name(dataset.get("name"))
                     .build();
        }


        return null;
    }

    private Method getMappedMethod(HttpRequest request, Class<?> controller) {
        return Arrays.stream(controller.getDeclaredMethods())
                .filter(method -> matchesURI(request, method))
                .findFirst()
                .orElse(null);
    }

    /**
     * Controller에 있는 handler가 반환하는 반환값을 보고 처리함
     * redirect: 로 시작하면, 그 뒷부분을 URI로 하여 리디렉션을 진행
     * 주소로 반환하면 resources > templates 아래의 해당 주소에 있는 view 파일의 주소를 ModelAndView에 담음
     * @param request
     * @param result : handler에서 반환된 반환값
     * @return ModelAndView
     */
    protected ModelAndView createModelAndView(HttpRequest request, Object result) {
        ModelAndView mv = new ModelAndView();

        String path = request.getRequestURI();

        mv.setStatus(HttpStatus.OK);

        //handler의 return값이 "/user/login"과 같은 동적 주소인 경우
        if(!(result == null)) {
            path = (String) result;
        }

        if(path.startsWith("redirect:")) {
            mv.setStatus(HttpStatus.FOUND);
            path = path.substring("redirect:".length());
        }

        mv.setViewName(path);
        mv.setContentType(ContentType.TEXT_HTML);

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
}
