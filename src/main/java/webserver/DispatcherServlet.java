package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.UserSaveController;
import webserver.utils.HttpConstants;
import webserver.utils.HttpField;
import webserver.utils.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.FileUtils;

import java.io.*;
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

            if (httpRequest.get(HttpField.METHOD).equals(HttpMethod.GET) && httpRequest.getPath().equals("/user/create")) {
                UserSaveController userSaveController = new UserSaveController();
                userSaveController.process(httpRequest, httpResponse);
            } else {
                FileUtils.processFileResponse(httpRequest.getURI(), httpResponse);
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
