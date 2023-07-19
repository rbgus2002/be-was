package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StaticFIleUtils;
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
            //1.리퀘스트를 파싱해서 그에 대한 정보를 분석하자! -> inputStream을 받아서 찍어보기
            HttpRequest request = HttpRequestParser.getInstance().getRequest(in);
            logger.debug("Request Header : \n{}", request.getHeader());

            //2.리퀘스트의 method와 url을 분석해 컨트롤러 매핑 후 정적 파일을 보내주도록 하자!
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = StaticFIleUtils.isExistedStaticFileRequest(request.getUrl()) ?
                    StaticFIleUtils.getStaticByte(request.getUrl()) :
                    "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
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
