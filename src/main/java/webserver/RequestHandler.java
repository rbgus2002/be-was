package webserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import service.UserService;

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
            final HttpWasRequest httpWasRequest = new HttpWasRequest(in);
            final HttpWasResponse httpWasResponse = new HttpWasResponse(out);

            final WasHandler wasHandler = new WasHandler(httpWasRequest, httpWasResponse, new Controller(new UserService()));
            wasHandler.service();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
