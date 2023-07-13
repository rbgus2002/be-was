package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
import webserver.http.HttpRequestParserImpl;
import webserver.view.View;
import webserver.view.ViewResolver;
import webserver.view.HtmlViewResolverImpl;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final HttpRequestParser httpRequestParser = new HttpRequestParserImpl();
    private final ViewResolver viewResolver = new HtmlViewResolverImpl();

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = httpRequestParser.parse(in);
            logAllHeader(httpRequest);

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();

            if(httpRequest.getUri().equals("/index.html")) {
                View view = viewResolver.resolveView(httpRequest.getUri());
                body = view.render(dos);
            }

            response200Header(dos, body.length);
            responseBody(dos, body);
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

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
