package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;


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

            HttpRequest httpRequest = HttpRequest.of(in);

            DataOutputStream dos = new DataOutputStream(out);
            Path filePath = Paths.get("/Users/lbc/Desktop/softeer_mission/be-was/src/main/resources/templates/index.html");
            byte[] body = Files.readAllBytes(filePath);

            HttpResponse httpResponse = new HttpResponse(HttpStatus.OK, ContentType.HTML, body.length, body);
            httpResponse.sendResponse(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
