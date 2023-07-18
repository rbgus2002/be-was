package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpConstant;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            FileUtils.processFileResponse(httpRequest.getURI(), httpResponse);

            sendResponse(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(HttpResponse httpResponse, OutputStream out) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);

        bufferedOutputStream.write(httpResponse.getHeaderBytes());
        bufferedOutputStream.write(HttpConstant.CRLF.getBytes());
        if (!httpResponse.isBodyEmpty()) {
            bufferedOutputStream.write(httpResponse.getBodyBytes());
        }
        bufferedOutputStream.flush();
    }
}
