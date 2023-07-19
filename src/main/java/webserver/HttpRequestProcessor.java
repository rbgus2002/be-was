package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponseStringifier;
import webserver.utils.ByteReader;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;
import webserver.http.message.HttpVersion;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 개별 스레드의 최상위 클래스로
 * 요청 메세지를 분석하고, 요청을 처리하고, 응답 결과를 보내주는 가장  별개의 스레드에서 요청을 처리할 수 있는 컨트롤러를 차자
 */
public class HttpRequestProcessor {
    private final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);
    private final HttpRequestParser requestParser = new HttpRequestParser();
    private final HttpResponseStringifier responseStringifier = new HttpResponseStringifier();
    private final HttpRequestHandler handler = new HttpRequestHandler();

    public void process(Socket connection) {
        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            String requestMessage = getRequestMessage(inputStream);
            if (isEmptyMessage(requestMessage)) return;
            logger.debug("requestMessage : \n{}", requestMessage);
            HttpRequest httpRequest = requestParser.parseHttpMessage(requestMessage);

            HttpResponse httpResponse = handler.handle(httpRequest);
            String responseMessage = responseStringifier.stringify(httpResponse);
            sendResponse(outputStream, responseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static boolean isEmptyMessage(String requestMessage) {
        return requestMessage.equals("");
    }

    private HttpResponse getHomeHttpResponse() throws IOException {
        HttpVersion httpVersion = HttpVersion.V1_1;
        HttpStatus httpStatus = HttpStatus.OK;
        byte[] body = Files.readAllBytes(new File("src/main/resources/templates/index.html").toPath());
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("text/html;charset=utf-8"));
        headers.put("Content-Length", List.of(String.valueOf(body.length)));
        return new HttpResponse(httpVersion, httpStatus, headers, body);
    }

    private void sendResponse(OutputStream outputStream, String responseMessage) {
        try {
            DataOutputStream dos = new DataOutputStream(outputStream);
            byte[] bytes = responseMessage.getBytes(StandardCharsets.UTF_8);
            dos.write(bytes);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getRequestMessage(InputStream inputStream) throws IOException {
        return ByteReader.readInputStream(inputStream);
    }
}
