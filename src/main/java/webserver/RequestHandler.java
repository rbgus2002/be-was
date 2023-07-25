package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StaticFIleUtils;
import webserver.exception.BadRequestException;
import webserver.exception.ConflictException;
import webserver.exception.NotFoundException;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestParser;

public class RequestHandler implements Runnable {//함수형 인터페이스
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestParser.getRequest(in);
            logger.debug("Request Header : \n{}", request.getHeader());
            logger.debug("Request Body : \n{}", request.getBody());
            HttpResponse response = new HttpResponse();

            FrontController.service(request, response);
            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            //인터널 서버 에러
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes(response.getHttpResponseHeader());
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse response) {
        try {
            byte[] body = response.getHttpResponseBody();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
