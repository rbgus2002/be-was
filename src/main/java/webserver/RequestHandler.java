package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import router.Router;
import webserver.http.model.Request;
import webserver.http.model.Request.Method;
import webserver.http.model.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpUtil.*;
import static webserver.http.HttpParser.*;
import static service.SessionService.isSessionValid;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try(connection) {
            // Request
            InputStream in = connection.getInputStream();
            Request request = new Request(in);

            // Response
            Response response = Router.generateResponse(request);

            // Send Response
            OutputStream out = connection.getOutputStream();
            response.sendResponse(out);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
