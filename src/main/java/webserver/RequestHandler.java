package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

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

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.doDispatch(httpRequest, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
