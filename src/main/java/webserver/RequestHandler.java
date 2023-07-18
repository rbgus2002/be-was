package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpConstant;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestHandler implements Runnable {
    private static final String TEMPLATES_DIRECTORY = "src/main/resources/templates";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            readFileToResponseBody(httpResponse, httpRequest.getURI());

            sendResponse(httpResponse, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendResponse(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        dos.write(httpResponse.getHeaderBytes());
        dos.write(HttpConstant.CRLF.getBytes());
        if (!httpResponse.isBodyEmpty()) {
            dos.write(httpResponse.getBodyBytes());
        }
        dos.flush();
    }

    private void readFileToResponseBody(HttpResponse httpResponse, String URI) throws IOException {
        Path path = Paths.get(TEMPLATES_DIRECTORY + URI);

        if (Files.exists(path) && !Files.isDirectory(path)) {
            httpResponse.setBody(readBytesFromFile(path));
            httpResponse.setStatus(HttpStatus.OK);
        } else if (URI.equals("/")) {
            httpResponse.setBody(readBytesFromFile(Paths.get(TEMPLATES_DIRECTORY + "/index.html")));
            httpResponse.setStatus(HttpStatus.OK);
        } else {
            httpResponse.setBody(readBytesFromFile(Paths.get(TEMPLATES_DIRECTORY + "/404.html")));
            httpResponse.setStatus(HttpStatus.NOT_FOUND);
        }

        httpResponse.setHeader(HttpConstant.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.setHeader(HttpConstant.CONTENT_LENGTH, String.valueOf(httpResponse.getBodyLength()));
    }

    private byte[] readBytesFromFile(Path path) throws IOException {
        InputStream inputStream = Files.newInputStream(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
