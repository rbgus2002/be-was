package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StaticFIleUtils;
import webserver.exception.BadRequestException;
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
        HttpResponse response = new HttpResponse();
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            if(in.readAllBytes() == null) {
                return;
            }
            HttpRequest request = HttpRequestParser.getRequest(in);
            logger.debug("Request Header : \n{}", request.getHeader());

            process(request, response);
            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            //인터널 서버 에러
            logger.error(e.getMessage());
        }
    }

    private void process(HttpRequest request, HttpResponse response) throws IOException {
        try {
            FrontController.service(request, response);
            response.setStatus(HttpResponseStatus.STATUS_200);
        } catch (NotFoundException e) {
            response.setStatus(HttpResponseStatus.STATUS_404);
            response.setBodyByText("404 NOT FOUND");
        } catch (BadRequestException e) {
            response.setStatus(HttpResponseStatus.STATUS_400);
            response.setBodyByText("400 BAD REQUEST");
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
