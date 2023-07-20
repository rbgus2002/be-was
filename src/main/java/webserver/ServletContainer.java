package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletContainer implements Runnable {

    private final String STATIC_PATH = "./src/main/resources/static";
    private final String DYNAMIC_PATH = "./src/main/resources/templates";

    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);

    private Socket connection;

    public ServletContainer(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(request);

            DispatcherServlet dispatcherServlet = new DispatcherServlet(request, response);
            dispatcherServlet.doService(request, response);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(DYNAMIC_PATH + request.getRequestURI()).toPath());
            dos.write(response.write());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
