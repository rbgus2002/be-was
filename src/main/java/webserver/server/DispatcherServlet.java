package webserver.server;

import controller.Controller;
import controller.ForwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.ResponseWriter;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.body.ResponseBody;

import java.io.DataOutputStream;

import static webserver.http.response.ResponseMessageHeader.BLANK;

public class DispatcherServlet {

    private final RequestMapper requestMapper = RequestMapper.createRequestMapper();

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public static final String REDIRECT = "redirect:";

    protected void service(HttpRequest req, HttpResponse resp, DataOutputStream dataOutputStream) {

        logger.info("DispatcherServlet service");
        Controller controller = requestMapper.getController(req.getUrl());
        if (controller == null) {
            controller = new ForwardController();
        }
        HttpResponse response = controller.execute(req, resp);
        String toUrl = response.getToUrl();
        response.setBody(new ResponseBody(toUrl));
        ResponseWriter responseWriter = new ResponseWriter(dataOutputStream, response);
        if (toUrl.contains(REDIRECT)) {
            String redirectUrl = toUrl.split(REDIRECT)[1];
            if(redirectUrl.contains(BLANK)) {
                redirectUrl = redirectUrl.trim();
            }
            responseWriter.sendRedirect(redirectUrl);
            return;
        }
        responseWriter.forward(toUrl);
    }
}
