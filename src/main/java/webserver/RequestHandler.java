package webserver;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;

import controller.UserController;
import http.HttpRequest;
import http.HttpResponse;
import http.MIME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            logger.debug("{}", httpRequest);
            String path = httpRequest.getPath();
            MIME mime = httpRequest.getMime();

            HttpResponse httpResponse;

            Method method = HandlerMapping.getHandler(httpRequest);

            if (method != null) {
                httpResponse = (HttpResponse) method.invoke(new UserController(), httpRequest);
            } else {
                httpResponse = HttpResponse.ok(path, mime);
            }

            httpResponse.response(new DataOutputStream(out));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
