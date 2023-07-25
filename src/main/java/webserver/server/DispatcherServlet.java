package webserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.ResponseWriter;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.body.ResponseBody;

import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;

import static webserver.http.response.ResponseMessageHeader.BLANK;

public class DispatcherServlet {

    private final AnnotationProcessor annotationProcessor;

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public static final String REDIRECT = "redirect:";

    public DispatcherServlet() {
        this.annotationProcessor = AnnotationProcessor.createAnnotationProcessor();
    }

    public void service(HttpRequest req, HttpResponse resp, DataOutputStream dataOutputStream) {
        logger.info("DispatcherServlet service");
        ControllerConfig handler = annotationProcessor.getHandler(req.getUrl());

        executeController(req, resp, handler);

        String toUrl = resp.getToUrl();
        logger.debug("toUrl = {}", toUrl);
        resp.setBody(new ResponseBody(toUrl));

        ResponseWriter responseWriter = new ResponseWriter(dataOutputStream, resp);

        if(toUrl.contains(REDIRECT)) {
            sendRedirect(toUrl, responseWriter);
            return;
        }
        responseWriter.forward(toUrl);
    }

    private static void executeController(HttpRequest req, HttpResponse resp, ControllerConfig handler) {
        resp.setToUrl(req.getUrl());

        if (handler != null) {
            try {
                handler.getMethod().invoke(handler.getClazz().getConstructor().newInstance(),req, resp);
            } catch (IllegalAccessException | InvocationTargetException
                     | InstantiationException | NoSuchMethodException e) {
                logger.error(e.getMessage());
            }
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
