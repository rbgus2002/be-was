package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {//tcp/ip 소켓, 서버 소켓 그냥 소켓 차이?
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {//sync,async / block,nonblock
                Thread thread = new Thread(new RequestHandler(connection));// 초기에는 유저레벨 쓰레드를 사용하다가 운영체제의 쓰레드 사용 Loom이 나오면서 운영체제 쓰레드와 독립적인 fiber 생성
                thread.start();
            }
            //put, post 차이 -> 멱등성이 있다 없다.
        }
    }
}
