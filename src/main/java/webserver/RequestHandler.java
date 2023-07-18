package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.FrontController;
import webserver.http.HttpRequest;
import webserver.view.View;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    private static final String templatesDirectoryPath = "src/main/resources/templates";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.createRequest(in);
            String url = request.getUrl();
            logRequest(request);

            FrontController frontController = new FrontController();

            byte[] body = frontController.service(request);
            DataOutputStream dos = new DataOutputStream(out);

            View view = new View(dos, body);
            view.render();

//            response200Header(dos,body);
//            responseBody(dos,body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void logRequest(HttpRequest request) {
        logger.debug("request line: {} {} {}", request.getMethod(), request.getUrl(), request.getVersion());

        Map<String, String> headers = request.getHeaders();
        for (String header : headers.keySet()) {
            logger.debug("header : {}: {}", header, headers.get(header));
        }
    }

    private void response200Header(DataOutputStream dos, byte[] body) throws Exception {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws Exception {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
