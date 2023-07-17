package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestMessage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import static utils.StringUtils.NEWLINE;

public class RequestHandler implements Runnable {
    public final String RESOURCE_PATH = "src/main/resources/templates";
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             InputStreamReader reader = new InputStreamReader(in);
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();

            while (!line.equals("")) {
                logger.debug("request message : {}", line);
                sb.append(line).append(NEWLINE);
                line = bufferedReader.readLine();
            }

            RequestMessage requestMessage = new RequestMessage(sb.toString());

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + requestMessage.getPath()).toPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
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
