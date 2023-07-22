package webserver;

import annotations.RequestMapping;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import model.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerAdapter.class);

    private final String STATIC_PATH = "./src/main/resources/static";
    private final String DYNAMIC_PATH = "./src/main/resources/templates";

    /**
     * Dynamic View 요청에 대한 처리
     * @param request
     * @param response
     * @param handler, null이 아님을 보장해야함
     * @return ModelAndView, templates에 존재하지 않으면 에러 페이지의 ModelAndView 반환
     */
    protected ModelAndView handle(HttpRequest request, HttpResponse response, Class<?> handler) {

        Class<?> controller = handler;

        Method requestHandler = Arrays.stream(controller.getDeclaredMethods())
                .filter(method -> matchesURI(request, method))
                .findFirst()
                .orElse(null);

        Object result = null;
        try {
            result = requestHandler.invoke(controller.getDeclaredConstructor().newInstance(), request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return createModelAndView(request, result);
    }

    private ModelAndView createModelAndView(HttpRequest request, Object result) {
        ModelAndView mv = new ModelAndView();

        String path = request.getRequestURI();

        if(!(result == null)) {
            path = (String) result;
        }

        if(path.equals("/")) {
            path = path + "index";
        }

        path = DYNAMIC_PATH + path + ".html";

        if(!Files.exists(Paths.get(path))) {
            return mv;
        }

        mv.setViewName(path);
        mv.setContentType(ContentType.TEXT_HTML);
        mv.setStatus(HttpStatus.OK);

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

    /**
     * controller에 매핑되지 않는 URL 요청이나 static 파일 요청이 왔을 때 해당 URL 위치의 파일을 가리키는 ModelAndView 객체 반환
     * @param request
     * @return ModelAndView, viewName은 URI로 설정
     */
    protected ModelAndView staticView(HttpRequest request) {
        ModelAndView mv = new ModelAndView();

        String requestURI = request.getRequestURI();
        if(requestURI.endsWith(".html")) {
            mv = createModelAndView(request, requestURI.substring(0, requestURI.length() - ".html".length()));
            return mv;
        }

        mv.setViewName(STATIC_PATH + requestURI);
        mv.setStatus(HttpStatus.OK);

        if(requestURI.endsWith(".css")) {
            mv.setContentType(ContentType.TEXT_CSS);
        }

        if(requestURI.endsWith(".js")) {
            mv.setContentType(ContentType.TEXT_JAVASCRIPT);
        }

        if(requestURI.endsWith("favicon.ico")) {
            mv.setContentType(ContentType.IMAGE_X_ICON);
        }

        if(requestURI.endsWith(".png")) {
            mv.setContentType(ContentType.IMAGE_PNG);
        }

        return mv;
    }
}
