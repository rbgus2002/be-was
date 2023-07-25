package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpRequest;
import http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = HttpResponse.init();

            DispatcherServlet dispatcherServlet = DispatcherServlet.init();
            dispatcherServlet.doService(request, response, out);
        } catch (IOException e) {
            logger.debug("readAllBytes ERROR");
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }
}
