package webserver;

import controller.FrontController;
import controller.RestController;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RestController restController;

    public RequestHandler(Socket connectionSocket, RestController restController) {
        this.connection = connectionSocket;
        this.restController = restController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = Parser.getHttpRequest(in);

            FrontController frontController = new FrontController(httpRequest);
            HttpResponse httpResponse = frontController.response();
            String responseHeader = httpResponse.getHttpHeaderFormat();
//            logger.debug("Response Header : {}", responseHeader);

            DataOutputStream dos = new DataOutputStream(out);

            dos.writeBytes(responseHeader);
            dos.write(httpResponse.getByteArrayOfBody());
        } catch ( Exception e) {
            logger.error(e.getMessage());
        }
    }

}
