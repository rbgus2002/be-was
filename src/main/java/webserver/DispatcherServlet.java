package webserver;

import controller.Controller;
import controller.ForwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DispatcherServlet  {

    private final RequestMapping requestMapping = new RequestMapping();

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    protected void service(HttpRequest req, HttpResponse resp) throws IOException {
        logger.info("DispatcherServlet service");
        Controller controller = requestMapping.getController(req);
        String toUrl;
        if(controller == null) {
            controller = new ForwardController(req.getUrl());
        }
        toUrl = controller.execute(req, resp);

        if(toUrl.contains("redirect")) {
            String redirectUrl = toUrl.split(":")[1];
            resp.sendRedirect(redirectUrl);
            return;
        }

        resp.forward(toUrl);
    }
}
