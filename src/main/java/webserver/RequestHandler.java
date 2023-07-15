package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.Files;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpWasRequest httpWasRequest = new HttpWasRequest(in);
            final HttpWasResponse httpWasResponse = new HttpWasResponse(out);

            final String resourcePath = httpWasRequest.getResourcePath();
            getResource(resourcePath, httpWasResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void getResource(String resourcePath, HttpWasResponse httpWasResponse) {
        try {
            httpWasResponse.responseResource(resourcePath);
        } catch (IOException e) {
            httpWasResponse.response404Header();
            logger.error(e.getMessage());
        }
    }
}
