package webserver;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

import db.SessionDatabase;
import handler.HandlerMapping;
import controller.UserController;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();
            logger.debug("{}", httpRequest);

            // TODO: 여기서부터 httpResponse.response 이전까지의 로직들을 다른 객체가 처리하도록 하면 좋을거같다. Front Controller? Dispatcher?
            Method method = HandlerMapping.getHandler(httpRequest);

            if (method != null) {
                String redirectPath = (String) method.invoke(new UserController(), httpRequest, httpResponse);
                String responsePath = Parser.parsePathFromRedirect(redirectPath);
                httpResponse.redirect(responsePath);
            } else {
                String requestPath = httpRequest.getPath();
                httpResponse.ok(requestPath);
            }

            httpResponse.response(new DataOutputStream(out));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
