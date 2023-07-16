package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.request.HttpRequestParserImpl;
import webserver.http.response.HttpResponse;
import webserver.myframework.servlet.DispatcherServlet;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final DispatcherServlet dispatcherServlet;
    private final Socket connection;
    private final HttpRequestParser httpRequestParser = new HttpRequestParserImpl();


    public RequestHandler(Socket connectionSocket,
                          DispatcherServlet dispatcherServlet) {
        this.connection = connectionSocket;
        this.dispatcherServlet = dispatcherServlet;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = httpRequestParser.parse(inputStream);
            HttpResponse httpResponse = HttpResponse.getInstance();

            logAllHeader(httpRequest);
            dispatcherServlet.handleRequest(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(outputStream);

            response200Header(dos, httpResponse);
            responseBody(dos, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void logAllHeader(HttpRequest httpRequest) {
        Set<String> headerNames = httpRequest.getHeaderNames();
        for (String headerName : headerNames) {
            logger.debug("Header {}: {}", headerName, httpRequest.getHeader(headerName));
        }
    }

    private void response200Header(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + httpResponse.getBody().length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] body = httpResponse.getBody();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
