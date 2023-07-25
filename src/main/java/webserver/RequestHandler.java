package webserver;

import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             DataOutputStream dos = new DataOutputStream(out);
             BufferedReader br = new BufferedReader(new InputStreamReader(in));
        ) {
            HttpRequest request = HttpRequestParser.parseRequest(br);
            HttpResponse response = new HttpResponse();
            FrontController controller = FrontController.getInstance();
            controller.service(dos, request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
