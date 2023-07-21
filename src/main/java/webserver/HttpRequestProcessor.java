package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponseSender;
import webserver.http.message.*;
import webserver.utils.ByteReader;

import java.io.*;
import java.net.Socket;

/**
 * 개별 스레드의 최상위 클래스로
 * 요청 메세지를 분석하고, 요청을 처리하고, 응답 결과를 보내주는 가장  별개의 스레드에서 요청을 처리할 수 있는 컨트롤러를 차자
 */
public class HttpRequestProcessor {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);
    private final HttpRequestParser requestParser = new HttpRequestParser();
    private final HttpResponseSender responseSender = new HttpResponseSender();
    private final HttpRequestHandler handler = new HttpRequestHandler();

    public void process(Socket connection) {
        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {

            String requestMessage = getRequestMessage(inputStream);
            if (isEmptyMessage(requestMessage)) {
                return;
            }
            HttpRequest httpRequest = requestParser.parseHttpMessage(requestMessage);
            logger.debug("requestMessage : \n{}", requestMessage);

            HttpResponse httpResponse = handler.handle(httpRequest);

            responseSender.sendResponse(outputStream, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static boolean isEmptyMessage(String requestMessage) {
        return requestMessage.equals("");
    }

    private String getRequestMessage(InputStream inputStream) throws IOException {
            return ByteReader.readInputStream(inputStream);
    }
}
