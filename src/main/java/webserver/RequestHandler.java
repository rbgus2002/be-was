package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.Callable;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private     Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            logger.debug("line = {}", line);
            String[] tokens = line.split(" ");
            if(tokens[1].startsWith("/user/create")){
                String[] info = tokens[1].split("\\?");
                String[] userInfo = info[1].split("&");
                String userId = userInfo[0].substring(userInfo[0].indexOf('=') + 1, userInfo[0].length());
                String password = userInfo[1].substring(userInfo[1].indexOf('=') + 1, userInfo[1].length());
                String name = userInfo[2].substring(userInfo[2].indexOf('=') + 1, userInfo[2].length());
                String email = userInfo[3].substring(userInfo[3].indexOf('=') + 1, userInfo[3].length());
                User user = new User(userId, password, name, email);
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("/Users/dydwo0740/Desktop/develop/be-was/src/main/resources/templates" + tokens[1]).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
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
