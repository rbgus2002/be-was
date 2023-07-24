package webserver;

import db.SessionManager;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ResponseHandler responseHandler;

    public RequestHandler(Socket connectionSocket, SessionManager sessionManager) {
        this.connection = connectionSocket;
        this.responseHandler = new ResponseHandler(sessionManager);
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             OutputStream out = this.connection.getOutputStream();
             DataOutputStream dos = new DataOutputStream(out)) {

            // 요청 읽기
            HttpRequest httpRequest = new HttpRequest(reader);
            httpRequest.printLogs();
            responseHandler.response(dos, httpRequest);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }
}
