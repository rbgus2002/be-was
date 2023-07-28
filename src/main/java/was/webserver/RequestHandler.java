package was.webserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import was.controller.ClassManager;
import was.webserver.response.HttpWasResponse;
import was.webserver.request.HttpWasRequest;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ClassManager classManager;

    public RequestHandler(Socket connectionSocket, ClassManager classManager) {
        this.connection = connectionSocket;
        this.classManager = classManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpWasRequest httpWasRequest = new HttpWasRequest(in);
            final HttpWasResponse httpWasResponse = new HttpWasResponse(out);

            final WasHandler wasHandler = new WasHandler(httpWasRequest, httpWasResponse, classManager);
            wasHandler.service();
            httpWasResponse.doResponse();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
