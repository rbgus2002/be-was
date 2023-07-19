package webserver.server;

import controller.Controller;
import controller.ForwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.ClientConnection;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseBody;

import java.io.DataOutputStream;

public class DispatcherServlet {

    private final RequestMapper requestMapper = RequestMapper.createRequestMapper();

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void service(HttpRequest req, HttpResponse resp, DataOutputStream dataOutputStream) {
        final String REDIRECT = "redirect";
        logger.info("DispatcherServlet service");
        Controller controller = requestMapper.getController(req.getUrl());
        String toUrl;
        if (controller == null) {
            controller = new ForwardController();
        }
        toUrl = controller.execute(req, resp);
        ResponseBody responseBody = new ResponseBody(toUrl);
        resp.setBody(responseBody);
        ClientConnection clientConnection = new ClientConnection(dataOutputStream, resp);
        if (toUrl.contains(REDIRECT)) {
            String redirectUrl = toUrl.split(":")[1];
            if(redirectUrl.contains(req.BLANK)) {
                redirectUrl = redirectUrl.trim();
            }
            clientConnection.sendRedirect(redirectUrl);
            return;
        }
        clientConnection.forward(toUrl);
    }
}
