package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.of(in);
            request.setCookie();
            HttpResponse response = HttpResponse.createEmpty();
            response.setHttpVersion(request.getVersion());
            DataOutputStream dos = new DataOutputStream(out);
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.dispatch(request, response, dos);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
