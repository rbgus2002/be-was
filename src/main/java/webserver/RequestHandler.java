package webserver;

import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ResponseHandler responseHandler;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.responseHandler = new ResponseHandler(connection);
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            // 요청 읽기
            HttpRequest httpRequest = new HttpRequest(reader);
            httpRequest.printLogs();
            responseHandler.response(httpRequest);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }
}
