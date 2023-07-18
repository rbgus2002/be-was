package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpConstant;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.FileUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Paths;

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
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            readFileToResponseBody(httpRequest.getURI(), httpResponse);

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

    private void readFileToResponseBody(String URI, HttpResponse httpResponse) throws IOException {
        if (FileUtils.isFileExist(URI)) {
            httpResponse.setStatus(HttpStatus.OK);
            readFile(httpResponse, URI);
        } else if (URI.equals("/")) {
            httpResponse.setStatus(HttpStatus.OK);
            readFile(httpResponse, "/index.html");
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND);
            readFile(httpResponse, "/404.html");
        }

        httpResponse.setHeader(HttpConstant.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.setHeader(HttpConstant.CONTENT_LENGTH, String.valueOf(httpResponse.getBodyLength()));
    }

    private void readFile(HttpResponse httpResponse, String URI) throws IOException {
        if (FileUtils.isTemplateFile(URI)) {
            httpResponse.setBody(FileUtils.readBytesFromFile(Paths.get(FileUtils.TEMPLATES_DIRECTORY + URI)));
            return;
        }
        httpResponse.setBody(FileUtils.readBytesFromFile(Paths.get(FileUtils.STATIC_DIRECTORY + URI)));
    }
}
