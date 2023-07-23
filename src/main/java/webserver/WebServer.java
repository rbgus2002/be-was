package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    public static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    public static final int NUM_OF_THREAD = 10;

    private final ExecutorService executorService;
    private final HttpRequestProcessor httpRequestProcessor;

    private final int port;

    public WebServer(int port) {
        executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
        httpRequestProcessor = new HttpRequestProcessor();
        this.port = port;
    }

    public void run() throws Exception {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Socket httpConnection = connection;
                executorService.execute(() -> {
                    httpRequestProcessor.process(httpConnection);
                });
            }
        } catch (BindException e) {
            logger.error("{} 포트는 사용중인 포트입니다.", port);
        } finally {
            executorService.shutdown();
        }
    }
}
