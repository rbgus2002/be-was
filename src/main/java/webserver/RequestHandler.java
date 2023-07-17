package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.request.HttpRequestParserImpl;
import webserver.http.request.exception.IllegalRequestParameterException;
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
            sendResponse(dos, httpResponse);
        } catch (IOException | IllegalRequestParameterException e) {
            logger.error(e.getMessage());
        }
    }

    private static void logAllHeader(HttpRequest httpRequest) {
        Set<String> headerNames = httpRequest.getHeaderNames();
        for (String headerName : headerNames) {
            logger.debug("Header {}: {}", headerName, httpRequest.getHeader(headerName));
        }
    }

    //TODO: text/html 외의 다른 Content-Type도 지원되도록 해야함
    private void sendResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            writeStartLine(dos, httpResponse);
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + httpResponse.getBody().length + "\r\n");
            writeHeaders(dos, httpResponse);
            dos.writeBytes("\r\n");
            writeBody(dos, httpResponse);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void writeBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        byte[] body = httpResponse.getBody();
        dos.write(body, 0, body.length);
        dos.writeBytes("\r\n");
    }

    private static void writeHeaders(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        HttpHeaders headers = httpResponse.getHeaders();
        for (String headerName : headers.getHeaderNames()) {
            dos.writeBytes(headerName + ": " + headers.getHeaderValues(headerName));
        }
    }

    private static void writeStartLine(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        String stringBuilder = "HTTP/1.1 " +
                               httpResponse.getStatus().getStatusNumber() + " " +
                               httpResponse + " " +
                               "\r\n";
        dos.writeBytes(stringBuilder);
    }
}
