package webserver;

import common.HttpRequest;
import common.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.HttpResponseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // HTTP 요청 메세지를 읽어서 HttpRequest 객체 생성
            HttpRequest request = HttpRequestUtils.createRequest(in);
            HttpResponse response = new HttpResponse();

            logger.debug(request.toString());

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.dispatch(request, response);

            // HTTP 응답 보내기
            HttpResponseUtils.sendResponse(out, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}