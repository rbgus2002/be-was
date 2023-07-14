package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

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

            HttpRequest httpRequest = new HttpRequest(in);
            String url = httpRequest.getUrl();

            byte[] body;
            switch (url) {
                case "/user/create":
                    body = Files.readAllBytes(Paths.get("src/main/resources/templates/user/form.html"));
                    User user = new User(httpRequest.getParams());
                    logger.debug(user.toString());
                    break;
                default:
                    body = Files.readAllBytes(Paths.get("src/main/resources/templates" + url));
            }

            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(dos, body, httpRequest);
            httpResponse.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
