package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.RequestHandler;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    public static void start(final int port) {
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        Thread thread = new Thread(() -> {
            try (ServerSocket listenSocket = new ServerSocket(port)) {
                logger.info("Web Application Server started {} port.", port);

                processConnection(executorService, listenSocket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();
    }

    private static void processConnection(
            final ExecutorService executorService,
            final ServerSocket listenSocket
    ) throws IOException {
        Socket connection;

        while ((connection = listenSocket.accept()) != null) {
            logger.debug(
                    "New Client Connect! Connected IP : {}, Port : {}",
                    connection.getInetAddress(),
                    connection.getPort()
            );

            executorService.execute(new RequestHandler(connection));
        }
    }
}
