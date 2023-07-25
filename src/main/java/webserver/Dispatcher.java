package webserver;

import webserver.exception.BadRequestException;
import webserver.handler.HttpHandler;
import webserver.request.HttpRequestMessage;
import webserver.response.HttpResponseMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static utils.StringUtils.NEW_LINE;
import static webserver.WebServer.logger;
import static webserver.request.HttpRequestParser.parseRequest;

public class Dispatcher implements Runnable {
    private final Socket connection;

    public Dispatcher(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            // 파싱해서 요청 메시지를 가져온다.
            HttpResponseMessage httpResponseMessage = new HttpResponseMessage();
            HttpRequestMessage httpRequestMessage = parseRequest(in);

            // 요청 메시지에 따라 수행한다.
            HttpHandler httpHandler = new HttpHandler(httpRequestMessage, httpResponseMessage);
            httpHandler.handling();

            response(out, httpResponseMessage);

        } catch (IOException | BadRequestException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private void response(OutputStream out, HttpResponseMessage httpResponseMessage) throws IOException {
        // todo 응답 로직도 view 클래스를 만들어서 분리하자.
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(httpResponseMessage.getResponseHeader());
        dos.writeBytes(NEW_LINE);
        if (httpResponseMessage.getResponseBody() != null) {
            dos.write(httpResponseMessage.getResponseBody());
        }
        dos.flush();
    }
}
