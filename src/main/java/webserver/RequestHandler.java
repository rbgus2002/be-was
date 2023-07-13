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

            String path = httpRequest.uri().getPath();
            String extension = FileUtils.getExtension(path);
            HttpResponse httpResponse;
            if (extension.equals("html")) {
                httpResponse = HttpResponse.ok(path);
            } else {
                httpResponse = RequestMappingHandler.invokeMethod(httpRequest);
            }
            httpResponse.response(connection);
        } catch (Throwable e) {
            logger.error(e.getMessage());
            HttpResponse.redirect("/error.html").response(connection);
        }
    }

    private void printLogs(List<String> strings) {
        for (String str : strings) {
            logger.debug(str);
        }
    }
}
