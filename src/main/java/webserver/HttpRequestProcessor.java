package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ByteReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 개별 스레드의 최상위 클래스로
 * 요청 메세지를 분석하고, 요청을 처리하고, 응답 결과를 보내주는 가장  별개의 스레드에서 요청을 처리할 수 있는 컨트롤러를 차자
 */
public class HttpRequestProcessor {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);

    public void process(Socket connection) {
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {
            String requestMessage = getRequestMessage(inputStream);
            logger.debug("requestMessage : {}", requestMessage);
            // TODO
            // 1. 메세지 -> HTTP Request
            // 2. 요청을 처리해줄 컨트롤러 찾기
            // 3. 컨트롤러에 작업 위임.
            // 4. 작업 결과를 받아 HTTP Response 생성
            sendResponseMessage(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponseMessage(OutputStream outputStream) {
            DataOutputStream dos = new DataOutputStream(outputStream);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
    }

    private String getRequestMessage(InputStream inputStream) throws IOException {
        return ByteReader.readInputStream(inputStream);
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
