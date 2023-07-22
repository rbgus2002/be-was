package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.BadRequestException;
import webserver.handler.HttpHandler;
import webserver.request.HttpRequest;
import webserver.response.HttpResponseMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static utils.StringUtils.NEW_LINE;
import static webserver.request.HttpRequestParser.parseRequest;

public class Dispatcher implements Runnable {
    public final String RESOURCE_PATH = "src/main/resources/templates";
    private final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private final Socket connection;

    public Dispatcher(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
//
        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            // 파싱해서 요청 메시지를 가져온다.
            HttpRequest httpRequest = parseRequest(in);
            HttpResponseMessage responseMessage = new HttpResponseMessage();

            // 요청 메시지에 따라 수행한다.
            HttpHandler httpHandler = new HttpHandler(httpRequest, responseMessage);
            httpHandler.handling();

            // 결과를 응답한다.
            response(out, httpHandler.getResponseMessage());

        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getLocalizedMessage());
        } catch (BadRequestException e) {
            logger.error("404 에러 잘못된 요청입니다.123");
        }
    }

    private void response(OutputStream out, HttpResponseMessage httpResponseMessage) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        logger.debug("response header message : {}", httpResponseMessage.getResponseHeader());
        dos.writeBytes(httpResponseMessage.getResponseHeader());
        dos.writeBytes(NEW_LINE);
        dos.write(httpResponseMessage.getResponseBody());
        dos.flush();
    }
}
