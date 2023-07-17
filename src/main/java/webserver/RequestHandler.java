package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // HTTP Request Message 읽기
            HttpRequest httpRequest = HttpRequest.create(in);
            logger.debug(httpRequest.toString());

            // HTTP 응답 body
            byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + httpRequest.getUrl()).toPath());

            // HTTP Response Message 보내기
            sendHttpResponseMessage(out, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void sendHttpResponseMessage(OutputStream out, byte[] body) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}