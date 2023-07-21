package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.FileController;
import webserver.controller.UserSaveController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.HttpConstants;
import webserver.utils.HttpField;
import webserver.utils.HttpMethod;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DispatcherServlet implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final Socket connection;

    public DispatcherServlet(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream()); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            if (httpRequest.get(HttpField.METHOD).equals(HttpMethod.POST) && httpRequest.get(HttpField.PATH).equals("/user/create")) {
                UserSaveController userSaveController = new UserSaveController();
                userSaveController.process(httpRequest, httpResponse);
            } else {
                FileController fileController = new FileController();
                fileController.process(httpRequest, httpResponse);
            }

            sendResponse(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
