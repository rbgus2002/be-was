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
import java.io.IOException;

public class DispatcherServlet {

    private final RequestMapper requestMapper = RequestMapper.createRequestMapper();

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void service(HttpRequest req, HttpResponse resp, DataOutputStream dataOutputStream) throws IOException {
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
        if (toUrl.contains("redirect")) {
            String redirectUrl = toUrl.split(":")[1];
            clientConnection.sendRedirect(redirectUrl);
            return;
        }
        clientConnection.forward(toUrl);
    }
}
