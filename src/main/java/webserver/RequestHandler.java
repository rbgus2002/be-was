package webserver;

import annotation.RequestMappingHandler;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            // 요청 읽기
            HttpRequest httpRequest = new HttpRequest(reader);
            printLogs(httpRequest);
            HttpResponse httpResponse = handleHttpRequest(httpRequest);

            ResponseHandler responseHandler = new ResponseHandler(connection);
            responseHandler.response(httpResponse);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }

    private void printLogs(HttpRequest httpRequest) {
        StringBuilder requestBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : httpRequest.getHeaders().entrySet()) {
            requestBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
        }
        logger.debug("Method : {}, URI : {}, Version : {}", httpRequest.method(), httpRequest.uri(), httpRequest.version());
        logger.debug("Headers : {}", requestBuilder);
        logger.debug("Mime : {}, Body : {}", httpRequest.mime(), httpRequest.getBody());
    }

    private HttpResponse handleHttpRequest(HttpRequest httpRequest) {
        String path = httpRequest.uri().getPath();
        String extension = StringUtils.getExtension(path);
        HttpResponse httpResponse;
        if (!Objects.equals(extension, path)) {
            // 정적 파일 응답
            httpResponse = HttpResponse.ok(path, httpRequest.mime());
        } else {
            // 잘못된 http request이면 /error.html response 생성
            try {
                httpResponse = RequestMappingHandler.invokeMethod(httpRequest);
            } catch (Throwable e) {
                logger.error("메소드를 실행하는데 오류가 발생했습니다.\n{}", (Object) e.getStackTrace());
                httpResponse = HttpResponse.notFound();
            }
        }
        return httpResponse;
    }
}
