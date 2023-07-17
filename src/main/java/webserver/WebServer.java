package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    public static final int DEFAULT_PORT = 8080;
    public static final int NUM_OF_THREAD = 10;

    private final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private final int port;

    public WebServer() {
        port = DEFAULT_PORT;
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection));
            }
        }
        executorService.shutdown();
    }
}
