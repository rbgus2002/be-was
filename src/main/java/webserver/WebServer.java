package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private WebServer() {
    }

    public static void on(int port) {
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            ExecutorService executor = Executors.newFixedThreadPool(10);
            while ((connection = listenSocket.accept()) != null) {
                executor.submit(new RequestHandler(connection));
            }
            executor.shutdown();
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
