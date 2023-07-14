package webserver;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.RestController;
import util.Parser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final RestController restController ;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        restController = new RestController();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = Parser.getHttpRequest(bufferedReader);

            HttpResponse httpResponse = restController.render(httpRequest);
            String responseHeader = httpResponse.getHttpHeaderFormat();
            logger.debug("Response Header : {}", responseHeader);

            DataOutputStream dos = new DataOutputStream(out);

            dos.writeBytes(responseHeader);
            dos.write(httpResponse.getByteArrayOfBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
