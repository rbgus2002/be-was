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
import java.nio.file.Path;
import java.nio.file.Paths;

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

            byte[] body;
            String extension = "";
            if (url.equals("/user/create")) {
                body = "".getBytes();
                response.setMethod("303");
                response.setStatusMessage("See Other");
                response.setHeader("Location", "/index.html");
                User user = new User(request.getParams());
                logger.debug("{}", user);
            } else if (url.equals("/index.html")) {
                body = Files.readAllBytes(Paths.get("src/main/resources/templates/index.html"));
                extension = "html";
            } else if (url.equals("/user/form.html")) {
                body = Files.readAllBytes(Paths.get("src/main/resources/templates/user/form.html"));
                extension = "html";
            } else if (url.equals("/")) {
                body = "hello world".getBytes();
                extension = "text";
            } else {
                Path path;
                try {
                    path = Paths.get("src/main/resources/static" + url);
                    body = Files.readAllBytes(path);
                } catch (IOException e) {
                    path = Paths.get("src/main/resources/templates" + url);
                    body = Files.readAllBytes(path);
                }
                String filename = path.getFileName().toString();
                extension = filename.substring(filename.lastIndexOf(".") + 1);
            }

            response.setBody(body, extension);
            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
