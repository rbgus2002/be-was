package webserver;

import annotation.RequestMappingHandler;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import static util.FileUtils.convertBufferedReaderToList;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            // 요청 읽기
            List<String> strings = convertBufferedReaderToList(reader);
            HttpRequest httpRequest = new HttpRequest(strings);

            printLogs(strings);

            response(httpRequest);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }

    private void printLogs(List<String> strings) {
        for (String str : strings) {
            logger.debug(str);
        }
    }

    private void response(HttpRequest httpRequest) {
        String path = httpRequest.uri().getPath();
        String extension = FileUtils.getExtension(path);
        HttpResponse httpResponse;
        if (!Objects.equals(extension, path)) {
            httpResponse = HttpResponse.ok(path, httpRequest.mime());
        } else {
            // 잘못된 http request이면 /error.html response 생성
            try {
                httpResponse = RequestMappingHandler.invokeMethod(httpRequest);
            } catch (Throwable e) {
                httpResponse = HttpResponse.redirect("/error.html");
            }
        }
        httpResponse.response(connection);
    }
}
