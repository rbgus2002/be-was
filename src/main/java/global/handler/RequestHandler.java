package global.handler;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import global.util.HttpUtil;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = Objects.requireNonNull(connectionSocket);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpUtil httpUtil = new HttpUtil(in);
            byte[] response = httpUtil.getResponse();
            DataOutputStream dos = new DataOutputStream(out);
            dos.write(response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
