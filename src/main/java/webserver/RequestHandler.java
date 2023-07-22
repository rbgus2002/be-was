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
            if(in == null) {
                return;
            }
            //1.리퀘스트를 파싱해서 그에 대한 정보를 분석하자! -> inputStream을 받아서 찍어보기
            HttpRequest request = HttpRequestParser.getRequest(in);
            logger.debug("Request Header : \n{}", request.getHeader());

            //2.리퀘스트의 method와 url을 분석해 컨트롤러 매핑 후 정적 파일을 보내주도록 하자!
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
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
