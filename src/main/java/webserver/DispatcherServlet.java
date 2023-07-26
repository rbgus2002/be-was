package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.ControllerResolver;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.HttpConstants;
import webserver.utils.HttpField;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DispatcherServlet implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final ControllerResolver controllerResolver = ControllerResolver.getInstance();
    private final Socket connection;

    public DispatcherServlet(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
             BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream())) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            processRequest(httpRequest, httpResponse);
            sendResponse(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = resolveController(httpRequest);
        controller.process(httpRequest, httpResponse);
    }

    private Controller resolveController(HttpRequest httpRequest) {
        String path = httpRequest.getHeader(HttpField.PATH);
        String method = httpRequest.getMethod();
        return controllerResolver.resolve(path, method);
    }

    private void sendResponse(HttpResponse httpResponse, OutputStream out) throws IOException {
        writeResponseHeaders(httpResponse, out);
        writeBlankLine(out);
        writeResponseBody(httpResponse, out);
        out.flush();
    }

    private void writeResponseHeaders(HttpResponse httpResponse, OutputStream out) throws IOException {
        out.write(httpResponse.getHeaderBytes());
    }

    private void writeBlankLine(OutputStream out) throws IOException {
        out.write(HttpConstants.CRLF.getBytes());
    }

    private void writeResponseBody(HttpResponse httpResponse, OutputStream out) throws IOException {
        if (!httpResponse.isBodyEmpty()) {
            out.write(httpResponse.getBodyBytes());
        }
    }
}
