package webserver;

import annotation.RequestMappingHandler;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static util.Utils.convertBufferedReaderToList;

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
            HttpRequest request = new HttpRequest(strings);

            printLogs(strings);

            HttpResponse response = RequestMappingHandler.invokeMethod(request);
            response.response(connection);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void printLogs(List<String> strings) {
        for (String str : strings) {
            logger.debug(str);
        }
    }
}
