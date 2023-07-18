package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httphandler.HttpHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(dos);
            String url = request.getUrl();
            HttpHandler handler = HttpHandler.of(url);
            handler.service(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
