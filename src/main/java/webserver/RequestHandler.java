package webserver;

import controller.FrontController;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestInputParser;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            HttpRequest httpRequest = RequestInputParser.getHttpRequest(in);

            FrontController frontController = new FrontController(httpRequest);
            HttpResponse httpResponse = frontController.response();
            String responseHeader = httpResponse.getHttpHeaderFormat();

            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(responseHeader);
            dos.write(httpResponse.getByteArrayOfBody());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
