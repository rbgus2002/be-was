package webserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.ResponseWriter;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;

import static webserver.http.response.ResponseMessageHeader.BLANK;

public class DispatcherServlet {

    private final AnnotationProcessor annotationProcessor;

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public static final String REDIRECT = "redirect:";
    private static final DispatcherServlet dispatcherServlet = new DispatcherServlet();

    private DispatcherServlet() {
        this.annotationProcessor = AnnotationProcessor.createAnnotationProcessor();
    }

    public static DispatcherServlet of() {
        return dispatcherServlet;
    }

    public void service(HttpRequest req, HttpResponse resp, DataOutputStream dataOutputStream) {
        logger.info("DispatcherServlet service");
        ControllerConfig handler = annotationProcessor.getHandler(req.getUrl());
        resp.setToUrl(req.getUrl());
        if(handler != null) {
            executeController(req, resp, handler);
        }

        String toUrl = resp.getToUrl();
        logger.debug("toUrl = {}", toUrl);
        resp.setBody(toUrl);

        //todo requestHandler에 넘겨줘서 쓰는 책임 분리
        ResponseWriter responseWriter = new ResponseWriter(dataOutputStream, resp);

        if(toUrl.contains(REDIRECT)) {
            sendRedirect(toUrl, responseWriter);
            return;
        }

        responseWriter.forward(toUrl);
    }

    private static void executeController(HttpRequest req, HttpResponse resp, ControllerConfig handler) {
            try {
                //todo 인스턴스 싱글톤으로 관리
                handler.getMethod().invoke(handler.getClazz().getConstructor().newInstance(),req, resp);
            } catch (IllegalAccessException | InvocationTargetException
                     | InstantiationException | NoSuchMethodException e) {
                if (e instanceof java.lang.reflect.InvocationTargetException) {
                    Throwable targetException = ((java.lang.reflect.InvocationTargetException) e).getTargetException();
                    logger.error(e.getClass() + " : " + targetException);
                }
                logger.error(e.getClass() + " : " + e.getMessage());
        }
    }

    private static void sendRedirect(String toUrl, ResponseWriter responseWriter) {
            String redirectUrl = toUrl.split(REDIRECT)[1];
            if(redirectUrl.contains(BLANK)) {
                redirectUrl = redirectUrl.trim();
            }
            responseWriter.sendRedirect(redirectUrl);
    }
}
