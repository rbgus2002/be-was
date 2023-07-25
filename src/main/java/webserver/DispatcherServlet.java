package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerResolver;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.HttpConstants;
import webserver.utils.HttpField;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DispatcherServlet implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final ControllerResolver controllerResolver = ControllerResolver.getInstance();
    private final Socket connection;

    public DispatcherServlet(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream()); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            processRequest(httpRequest, httpResponse);
            sendResponse(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = resolveController(httpRequest);
        controller.process(httpRequest, httpResponse);
    }

    private Controller resolveController(HttpRequest httpRequest) {
        String path = httpRequest.get(HttpField.PATH);
        String method = httpRequest.get(HttpField.METHOD);

        return controllerResolver.resolve(path, method);
    }

    private void sendResponse(HttpResponse httpResponse, OutputStream out) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);

        bufferedOutputStream.write(httpResponse.getHeaderBytes());
        bufferedOutputStream.write(HttpConstants.CRLF.getBytes());
        if (!httpResponse.isBodyEmpty()) {
            bufferedOutputStream.write(httpResponse.getBodyBytes());
        }

        bufferedOutputStream.flush();
    }
}
